package hr.fer.elektrijada.dal.sql.faculty;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import hr.fer.elektrijada.dal.sql.DbHelper;
import hr.fer.elektrijada.dal.sql.user.UserContract;
import hr.fer.elektrijada.dal.sql.user.UserFromDb;
import hr.fer.elektrijada.util.Logger;

/**
 * Created by Ivica Brebrek
 */
public class SqlFacultyRepository {
    private SQLiteOpenHelper dbHelper;
    private final Context context;

    public SqlFacultyRepository(Context context) {
        dbHelper = new DbHelper(context);
        this.context = context;
    }

    public void close() {
        if (dbHelper != null) {
            dbHelper.close();
        }
    }

    public void fixId(FacultyFromDb faculty) {
        SQLiteDatabase db = null;
        try {
            db = dbHelper.getReadableDatabase();

            Cursor cursor = db.rawQuery("SELECT "+ FacultyContract.FacultyEntry._ID+" FROM " + FacultyContract.FacultyEntry.TABLE_NAME
                    + " WHERE " + FacultyContract.FacultyEntry.COLUMN_NAME_NAME +"='"+faculty.getName()+"'", null);

            if (cursor != null) {
                cursor.moveToFirst();
                faculty.setId(cursor.getInt(0));
                cursor.close();
            }

        } catch (Exception exc) {
            //TO DO: Call Logger.ShowError;
        } finally {
            if (db != null)
                db.close();
        }
    }

    public FacultyFromDb getFaculty(int id) {
        FacultyFromDb facultyFromDb = null;
        SQLiteDatabase db = null;
        try {
            db = dbHelper.getReadableDatabase();

            String[] projection = {
                    FacultyContract.FacultyEntry._ID,
                    FacultyContract.FacultyEntry.COLUMN_NAME_NAME,
                    FacultyContract.FacultyEntry.COLUMN_NAME_NICK
            };

            Cursor cursor = db.query(
                    FacultyContract.FacultyEntry.TABLE_NAME,
                    projection,
                    FacultyContract.FacultyEntry._ID + "=?",
                    new String[]{String.valueOf(id)},
                    null,
                    null,
                    null
            );

            if (cursor != null && cursor.moveToFirst()) {
                facultyFromDb = new FacultyFromDb(
                        cursor.getInt(FacultyContract.getColumnPos(FacultyContract.FacultyEntry._ID)),
                        cursor.getString(FacultyContract.getColumnPos(FacultyContract.FacultyEntry.COLUMN_NAME_NAME)),
                        cursor.getString(FacultyContract.getColumnPos(FacultyContract.FacultyEntry.COLUMN_NAME_NICK))
                );
                cursor.close();
            }
        } catch (Exception exc) {
            //TO DO: Call Logger.ShowError;
        } finally {
            if (db != null)
                db.close();
        }
        return facultyFromDb;
    }


    public List<FacultyFromDb> getFaculties() {
        List<FacultyFromDb> allFaculties = new ArrayList<>();
        SQLiteDatabase db = null;
        try {
            db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM " + FacultyContract.FacultyEntry.TABLE_NAME +
                    "  ORDER BY " + FacultyContract.FacultyEntry.COLUMN_NAME_NAME, null);
            if (cursor.moveToFirst()) {
                do {
                    FacultyFromDb faculty = new FacultyFromDb(
                            cursor.getInt(FacultyContract.getColumnPos(FacultyContract.FacultyEntry._ID)),
                            cursor.getString(FacultyContract.getColumnPos(FacultyContract.FacultyEntry.COLUMN_NAME_NAME)),
                            cursor.getString(FacultyContract.getColumnPos(FacultyContract.FacultyEntry.COLUMN_NAME_NICK))
                    );
                    allFaculties.add(faculty);
                } while (cursor.moveToNext());
            }
            cursor.close();
        } catch (Exception exc) {
            Logger.LogException(exc);
        } finally {
            if (db != null)
                db.close();
        }
        Collections.sort(allFaculties, new Comparator<FacultyFromDb>() {
            @Override
            public int compare(FacultyFromDb f1, FacultyFromDb f2) {
                return f2.getName().compareTo(f1.getName());
            }
        });
        return allFaculties;
    }

    public void createFaculty(FacultyFromDb faculty) {
        SQLiteDatabase db = null;
        try {
            db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(FacultyContract.FacultyEntry.COLUMN_NAME_NAME, faculty.getName());
            values.put(FacultyContract.FacultyEntry.COLUMN_NAME_NICK, faculty.getNick());

            db.insert(FacultyContract.FacultyEntry.TABLE_NAME, null, values);
        } catch (Exception exc) {
            //TO DO: Call Logger.ShowError;
        } finally {
            if (db != null)
                db.close();
        }
    }
}

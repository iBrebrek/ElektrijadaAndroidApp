package hr.fer.elektrijada.dal.sql.faculty;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import hr.fer.elektrijada.dal.sql.DbHelper;

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
}

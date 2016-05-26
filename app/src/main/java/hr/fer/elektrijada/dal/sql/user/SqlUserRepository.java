package hr.fer.elektrijada.dal.sql.user;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import hr.fer.elektrijada.dal.sql.DbHelper;
import hr.fer.elektrijada.util.Logger;

/**
 * Created by Ivica Brebrek
 */
public class SqlUserRepository {
    private SQLiteOpenHelper dbHelper;
    private final Context context;

    public SqlUserRepository(Context context) {
        dbHelper = new DbHelper(context);
        this.context = context;
    }

    public void close() {
        if (dbHelper != null) {
            dbHelper.close();
        }
    }

    public List<UserFromDb> getAllUsers() {
        List<UserFromDb> list = new ArrayList<>();
        SQLiteDatabase db = null;
        SqlUserRepository users = new SqlUserRepository(context);
        try {
            db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM " + UserContract.UserEntry.TABLE_NAME, null);
            if (cursor.moveToFirst()) {
                do {
                    list.add(new UserFromDb(
                            cursor.getInt(UserContract.getColumnPos(UserContract.UserEntry._ID)),
                            cursor.getString(UserContract.getColumnPos(UserContract.UserEntry.COLUMN_NAME_NAME)),
                            cursor.getString(UserContract.getColumnPos(UserContract.UserEntry.COLUMN_NAME_SURNAME)),
                            cursor.getString(UserContract.getColumnPos(UserContract.UserEntry.COLUMN_NAME_UNIQUE_ID))
                    ));
                } while (cursor.moveToNext());
            }
            cursor.close();
        } catch (Exception exc) {
            Logger.LogException(exc);
        } finally {
            users.close();
            if (db != null)
                db.close();
        }
        return list;
    }

    public void fixId(UserFromDb user) {
        SQLiteDatabase db = null;
        try {
            db = dbHelper.getReadableDatabase();

            Cursor cursor = db.rawQuery("SELECT "+ UserContract.UserEntry._ID+" FROM " + UserContract.UserEntry.TABLE_NAME
                    + " WHERE " + UserContract.UserEntry.COLUMN_NAME_UNIQUE_ID +"='"+user.getUniqueId()+"'", null);

            if (cursor != null) {
                cursor.moveToFirst();
                user.setId(cursor.getInt(0));
                cursor.close();
            }

        } catch (Exception exc) {
            //TO DO: Call Logger.ShowError;
        } finally {
            if (db != null)
                db.close();
        }
    }

    public UserFromDb getUser(long id) {
        UserFromDb user = null;
        SQLiteDatabase db = null;
        try {
            db = dbHelper.getReadableDatabase();

            Cursor cursor = db.rawQuery("SELECT * FROM " + UserContract.UserEntry.TABLE_NAME
                    + " WHERE " + UserContract.UserEntry._ID +"="+id, null);

            if (cursor != null) {
                cursor.moveToFirst();
                user = new UserFromDb(
                        cursor.getInt(UserContract.getColumnPos(UserContract.UserEntry._ID)),
                        cursor.getString(UserContract.getColumnPos(UserContract.UserEntry.COLUMN_NAME_NAME)),
                        cursor.getString(UserContract.getColumnPos(UserContract.UserEntry.COLUMN_NAME_SURNAME)),
                        cursor.getString(UserContract.getColumnPos(UserContract.UserEntry.COLUMN_NAME_UNIQUE_ID))
                );
                cursor.close();
            }

        } catch (Exception exc) {
            //TO DO: Call Logger.ShowError;
        } finally {
            if (db != null)
                db.close();
        }
        return user;
    }

    //returns created user's id (this new user is in db under that id)
    public long createUser(UserFromDb user) {
        SQLiteDatabase db = null;
        try {
            db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(UserContract.UserEntry.COLUMN_NAME_NAME, user.getName());
            values.put(UserContract.UserEntry.COLUMN_NAME_SURNAME, user.getSureName());
            values.put(UserContract.UserEntry.COLUMN_NAME_UNIQUE_ID, user.getUniqueId());

            return db.insert(UserContract.UserEntry.TABLE_NAME, null, values);
        } catch (Exception exc) {
            //TO DO: Call Logger.ShowError;
            return -2;
        } finally {
            if (db != null)
                db.close();
        }
    }
}

package hr.fer.elektrijada.dal.sql.stage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import hr.fer.elektrijada.dal.sql.DbHelper;
import hr.fer.elektrijada.dal.sql.category.CategoryContract;
import hr.fer.elektrijada.dal.sql.user.UserContract;
import hr.fer.elektrijada.dal.sql.user.UserFromDb;
import hr.fer.elektrijada.util.Logger;

/**
 * Created by Ivica Brebrek
 */
public class SqlStageRepository {
    private SQLiteOpenHelper dbHelper;
    private final Context context;


    public SqlStageRepository(Context context) {
        dbHelper = new DbHelper(context);
        this.context = context;
    }

    public void close() {
        if (dbHelper != null) {
            dbHelper.close();
        }
    }

    public void fixId(StageFromDb stage) {
        SQLiteDatabase db = null;
        try {
            db = dbHelper.getReadableDatabase();

            Cursor cursor = db.rawQuery("SELECT "+ StageContract.StageEntry._ID+" FROM " + StageContract.StageEntry.TABLE_NAME
                    + " WHERE " + StageContract.StageEntry.COLUMN_NAME_NAME +"='"+stage.getName()+"'", null);

            if (cursor != null) {
                cursor.moveToFirst();
                stage.setId(cursor.getInt(0));
                cursor.close();
            }

        } catch (Exception exc) {
            //TO DO: Call Logger.ShowError;
        } finally {
            if (db != null)
                db.close();
        }
    }

    public StageFromDb getStage (int id) {
        StageFromDb stage = null;
        SQLiteDatabase db = null;
        try {
            db = dbHelper.getReadableDatabase();

            Cursor cursor = db.rawQuery(
                    "SELECT * "
                            + " FROM " + StageContract.StageEntry.TABLE_NAME
                            + " WHERE " + StageContract.StageEntry._ID +" = " + id,
                    null);

            if (cursor.moveToFirst()) {
                stage = new StageFromDb(
                        cursor.getInt(StageContract.getColumnPos(CategoryContract.CategoryEntry._ID)),
                        cursor.getString(StageContract.getColumnPos(CategoryContract.CategoryEntry.COLUMN_NAME_NAME))
                );
            }
            cursor.close();
        } catch (Exception exc) {

        } finally {
            if (db != null)
                db.close();
        }
        return stage;
    }

    public ArrayList<StageFromDb> getAllStages(){
        ArrayList<StageFromDb> listOfAllStages = new ArrayList<>();
        SQLiteDatabase db = null;
        try {
            db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM " + StageContract.StageEntry.TABLE_NAME +
                    " ORDER BY " + StageContract.StageEntry._ID + " ASC", null);
            if (cursor.moveToFirst()) {
                do {
                    listOfAllStages.add(new StageFromDb(
                            cursor.getInt(StageContract.getColumnPos(StageContract.StageEntry._ID)),
                            cursor.getString(StageContract.getColumnPos(StageContract.StageEntry.COLUMN_NAME_NAME))
                    ));
                } while (cursor.moveToNext());
            }
            cursor.close();
        } catch (Exception exc) {
            Logger.LogException(exc);
        } finally {
            if (db != null)
                db.close();
        }
        return listOfAllStages;
    }

    public void createStage(StageFromDb stage) {
        SQLiteDatabase db = null;
        try {
            db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(StageContract.StageEntry.COLUMN_NAME_NAME, stage.getName());

            db.insert(StageContract.StageEntry.TABLE_NAME, null, values);
        } catch (Exception exc) {
            //TO DO: Call Logger.ShowError;
        } finally {
            if (db != null)
                db.close();
        }
    }
}

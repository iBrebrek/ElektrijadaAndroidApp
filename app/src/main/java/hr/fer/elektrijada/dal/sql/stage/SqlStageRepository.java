package hr.fer.elektrijada.dal.sql.stage;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import hr.fer.elektrijada.dal.sql.DbHelper;
import hr.fer.elektrijada.dal.sql.category.CategoryContract;
import hr.fer.elektrijada.util.Logger;

/**
 * Created by Ivica Brebrek
 */
public class SqlStageRepository {
    private SQLiteOpenHelper dbHelper;

    public SqlStageRepository(Context context) {
        dbHelper = new DbHelper(context);
    }

    public void close() {
        if (dbHelper != null) {
            dbHelper.close();
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

    public static class StageFromDb {
        private int id;
        private String name;

        public StageFromDb(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return name;
        }

        @Override
        public boolean equals(Object o) {
            if (o instanceof StageFromDb && ((StageFromDb) o).id == id) {
                return true;
            }
            return false;
        }
    }
}

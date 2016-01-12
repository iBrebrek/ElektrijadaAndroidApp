package hr.fer.elektrijada.dal.sql.category;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import hr.fer.elektrijada.dal.sql.DbHelper;
import hr.fer.elektrijada.util.Logger;

/**
 * Created by Ivica Brebrek
 */
public class SqlCategoryRepository {
    private SQLiteOpenHelper dbHelper;

    public SqlCategoryRepository(Context context) {
        dbHelper = new DbHelper(context);
    }

    public void close() {
        if (dbHelper != null) {
            dbHelper.close();
        }
    }

    //napisati ostale metode ako zatrebaju...

    public String getCategoryName(int id) {
        String name = null;
        SQLiteDatabase db = null;
        try {
            db = dbHelper.getReadableDatabase();

            Cursor cursor = db.rawQuery(
                    "SELECT " + CategoryContract.CategoryEntry.COLUMN_NAME_NAME
                            + " FROM " + CategoryContract.CategoryEntry.TABLE_NAME
                            + " WHERE " + CategoryContract.CategoryEntry._ID +" = " + id,
                    null);

            if (cursor != null) {
                cursor.moveToFirst();
                name = cursor.getString(0);
                cursor.close();
            }
        } catch (Exception exc) {

        } finally {
            if (db != null)
                db.close();
        }
        return name;
    }

    public ArrayList<CategoryFromDb> getAllCategories(){
        ArrayList<CategoryFromDb> listOfAllCategories = new ArrayList<>();
        SQLiteDatabase db = null;
        try {
            db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM " + CategoryContract.CategoryEntry.TABLE_NAME +
                    " ORDER BY " + CategoryContract.CategoryEntry.COLUMN_NAME_NAME + " ASC", null);
            if (cursor.moveToFirst()) {
                do {
                    listOfAllCategories.add(new CategoryFromDb(
                            cursor.getInt(CategoryContract.getColumnPos(CategoryContract.CategoryEntry._ID)),
                            cursor.getString(CategoryContract.getColumnPos(CategoryContract.CategoryEntry.COLUMN_NAME_NAME)),
                            cursor.getString(CategoryContract.getColumnPos(CategoryContract.CategoryEntry.COLUMN_NAME_NICK)),
                            cursor.getInt(CategoryContract.getColumnPos(CategoryContract.CategoryEntry.COLUMN_NAME_IS_SPORT)) > 0
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
        return listOfAllCategories;
    }

    public static class CategoryFromDb {
        private int id;
        private String name;
        private String nick;
        private boolean isSport;

        public CategoryFromDb(int id, String name, String nick, boolean isSport) {
            this.id = id;
            this.name = name;
            this.nick = nick;
            this.isSport = isSport;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getNick() {
            return nick;
        }

        public boolean isSport() {
            return isSport;
        }

        @Override
        public String toString() {
            return name + (nick==null?"":" ("+nick+")");
        }
    }
}

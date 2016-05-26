package hr.fer.elektrijada.dal.sql.category;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import hr.fer.elektrijada.dal.sql.DbHelper;
import hr.fer.elektrijada.dal.sql.competitor.CompetitorContract;
import hr.fer.elektrijada.dal.sql.competitor.CompetitorFromDb;
import hr.fer.elektrijada.dal.sql.news.NewsContract;
import hr.fer.elektrijada.dal.sql.stage.StageContract;
import hr.fer.elektrijada.dal.sql.stage.StageFromDb;
import hr.fer.elektrijada.model.news.NewsEntry;
import hr.fer.elektrijada.util.DateParserUtil;
import hr.fer.elektrijada.util.Logger;

/**
 * Created by Ivica Brebrek
 */
public class SqlCategoryRepository {
    private SQLiteOpenHelper dbHelper;
    private final Context context;

    public SqlCategoryRepository(Context context) {
        dbHelper = new DbHelper(context);
        this.context = context;
    }

    public void close() {
        if (dbHelper != null) {
            dbHelper.close();
        }
    }

    public void fixId(CategoryFromDb category) {
        SQLiteDatabase db = null;
        try {
            db = dbHelper.getReadableDatabase();

            Cursor cursor = db.rawQuery("SELECT " + CategoryContract.CategoryEntry._ID + " FROM " + CategoryContract.CategoryEntry.TABLE_NAME
                    + " WHERE " + CategoryContract.CategoryEntry.COLUMN_NAME_NAME + "='" + category.getName() + "'", null);

            if (cursor != null) {
                cursor.moveToFirst();
                category.setId(cursor.getInt(0));
                cursor.close();
            }

        } catch (Exception exc) {
            //TO DO: Call Logger.ShowError;
        } finally {
            if (db != null)
                db.close();
        }
    }

    public CategoryFromDb getCategory(int id) {
        CategoryFromDb category = null;
        SQLiteDatabase db = null;
        try {
            db = dbHelper.getReadableDatabase();

            Cursor cursor = db.rawQuery(
                    "SELECT * "
                            + " FROM " + CategoryContract.CategoryEntry.TABLE_NAME
                            + " WHERE " + CategoryContract.CategoryEntry._ID +" = " + id,
                    null);

            if (cursor.moveToFirst()) {
                category = new CategoryFromDb(
                        cursor.getInt(CategoryContract.getColumnPos(CategoryContract.CategoryEntry._ID)),
                        cursor.getString(CategoryContract.getColumnPos(CategoryContract.CategoryEntry.COLUMN_NAME_NAME)),
                        cursor.getString(CategoryContract.getColumnPos(CategoryContract.CategoryEntry.COLUMN_NAME_NICK)),
                        cursor.getInt(CategoryContract.getColumnPos(CategoryContract.CategoryEntry.COLUMN_NAME_IS_SPORT)) > 0,
                        cursor.getInt(CategoryContract.getColumnPos(CategoryContract.CategoryEntry.COLUMN_NAME_IS_DUEL)) > 0
                );
            }
            cursor.close();
        } catch (Exception exc) {

        } finally {
            if (db != null)
                db.close();
        }
        return category;
    }

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
                            cursor.getInt(CategoryContract.getColumnPos(CategoryContract.CategoryEntry.COLUMN_NAME_IS_SPORT)) > 0,
                            cursor.getInt(CategoryContract.getColumnPos(CategoryContract.CategoryEntry.COLUMN_NAME_IS_DUEL)) > 0
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

    public void updateCategory(CategoryFromDb category) {
        SQLiteDatabase db = null;
        try {
            db = dbHelper.getReadableDatabase();
            ContentValues values = new ContentValues();
            values.put(CategoryContract.CategoryEntry.COLUMN_NAME_NAME, category.getName());
            values.put(CategoryContract.CategoryEntry.COLUMN_NAME_NICK, category.getNick());
            values.put(CategoryContract.CategoryEntry.COLUMN_NAME_IS_DUEL, category.isDuel());
            values.put(CategoryContract.CategoryEntry.COLUMN_NAME_IS_SPORT, category.isSport());
            db.update(CategoryContract.CategoryEntry.TABLE_NAME, values, CategoryContract.CategoryEntry._ID + "=?", new String[]{String.valueOf(category.getId())});
        } catch (Exception exc) {
            //TO DO: Call Logger.ShowError;
        } finally {
            if (db != null)
                db.close();
        }
    }

    public void createCategory(CategoryFromDb category) {
        SQLiteDatabase db = null;
        try {
            db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(CategoryContract.CategoryEntry.COLUMN_NAME_NAME, category.getName());
            values.put(CategoryContract.CategoryEntry.COLUMN_NAME_NICK, category.getNick());
            values.put(CategoryContract.CategoryEntry.COLUMN_NAME_IS_DUEL, category.isDuel());
            values.put(CategoryContract.CategoryEntry.COLUMN_NAME_IS_SPORT, category.isSport());

            db.insert(CategoryContract.CategoryEntry.TABLE_NAME, null, values);
        } catch (Exception exc) {
            //TO DO: Call Logger.ShowError;
        } finally {
            if (db != null)
                db.close();
        }
    }
}

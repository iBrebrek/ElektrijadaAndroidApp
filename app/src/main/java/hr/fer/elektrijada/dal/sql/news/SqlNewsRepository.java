package hr.fer.elektrijada.dal.sql.news;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import hr.fer.elektrijada.dal.sql.DbHelper;
import hr.fer.elektrijada.model.news.NewsEntry;
import hr.fer.elektrijada.model.news.NewsRepository;
import hr.fer.elektrijada.util.DateParserUtil;
import hr.fer.elektrijada.util.Logger;

/**
 * nikad nemoj zaboravit ugasiti dbHelper,
 * npr, pravilno dodavanje nove vijesti:
 * SqlNewsRepository repo = new SqlNewsRepository(getApplicationContext());
 * repo.createNewsEntry(novaVijest);
 * repo.close();
 */
public class SqlNewsRepository implements NewsRepository {
    SQLiteOpenHelper dbHelper;

    //TO DO: Provjeriti u Android dokumentaciji smije li biti više helpera ili mora biti singleton i što sa zatvaranjem
    /*
    Citajuci android dokumentaciju mi se cini da nije problem ako ih je vise jer se baza uopce ne otvara/kreira
    tak dugo dok se ne pozvoze getWritableDatabase ili getReadableDatabase, no pise da je dobra praksa getWritable/ReadableDatabase
    pozivati u drugim dretvana jer se znaju dugo izvrsavati.
    nekoliko citata iz dokumentacije o SQLiteOpenHelper:
    za konstrutkor: "The database is not actually created or opened until one of getWritableDatabase() or getReadableDatabase() is called."
    metode getW/RDatabase: "this method may take a long time to return, so you should not call it from the application main thread, including from ContentProvider.onCreate()."
    metoda getWritableDatabase: "Once opened successfully, the database is cached, so you can call this method every time you need to write to the database.(Make sure to call close() when you no longer need the database.) "

    Sve u svemu, mislim da je nas nacin dobar.
    */

    public SqlNewsRepository(Context context) {
        dbHelper = new DbHelper(context);
    }

    public void close() {
        if (dbHelper != null) {
            dbHelper.close();
        }
    }

    public boolean createNewsEntry(NewsEntry news) {
        SQLiteDatabase db = null;
        try {
            db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(NewsContract.NewsEntry.COLUMN_NAME_TITLE, news.getTitle());
            values.put(NewsContract.NewsEntry.COLUMN_NAME_DESCRIPTION, news.getText());
            values.put(NewsContract.NewsEntry.COLUMN_NAME_USER_ID, news.getAuthorId());
            values.put(NewsContract.NewsEntry.COLUMN_NAME_TIME, DateParserUtil.dateToString(news.getTimeOfCreation()));

            long rowId = db.insert(NewsContract.NewsEntry.TABLE_NAME, null, values);
            return rowId != -1;
        } catch (Exception exc) {
            //TO DO: Call Logger.ShowError;
            return false;
        } finally {
            if (db != null)
                db.close();
        }
    }

    //mozda nepotrebno, al ne smeta
    public int getNewsCount() {
        int count = 0;
        SQLiteDatabase db = null;
        try {
            db = dbHelper.getReadableDatabase();
            SQLiteStatement statement = db.compileStatement("SELECT COUNT(*) FROM " + NewsContract.NewsEntry.TABLE_NAME);
            count = (int) statement.simpleQueryForLong();
        } catch (Exception exc) {
            //TO DO: Call Logger.ShowError;
        } finally {
            if (db != null)
                db.close();
        }
        return count;
    }

    /**
     * sve vijesti u bazi sortirane od najmlade do najstarije
     */
    public List<NewsEntry> getNews() {
        List<NewsEntry> listOfNews = new ArrayList<>();
        SQLiteDatabase db = null;
        try {
            db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM " + NewsContract.NewsEntry.TABLE_NAME, null);
            if (cursor.moveToFirst()) {
                do {
                    NewsEntry news = newsEntryFromCursor(cursor);
                    listOfNews.add(news);
                } while (cursor.moveToNext());
            }
            cursor.close();
        } catch (Exception exc) {
            Logger.LogException(exc);
        } finally {
            if (db != null)
                db.close();
        }
        Collections.sort(listOfNews, new Comparator<NewsEntry>() {
            @Override
            public int compare(NewsEntry n1, NewsEntry n2) {
                return n2.getTimeOfCreation().compareTo(n1.getTimeOfCreation());
            }
        });
        return listOfNews;
    }

    @NonNull
    private NewsEntry newsEntryFromCursor(Cursor cursor) {
        return new NewsEntry(
                cursor.getInt(NewsContract.getColumnPos(NewsContract.NewsEntry._ID)),
                cursor.getString(NewsContract.getColumnPos(NewsContract.NewsEntry.COLUMN_NAME_TITLE)),
                cursor.getString(NewsContract.getColumnPos(NewsContract.NewsEntry.COLUMN_NAME_DESCRIPTION)),
                DateParserUtil.stringToDate(cursor.getString(NewsContract.getColumnPos(NewsContract.NewsEntry.COLUMN_NAME_TIME))),
                cursor.getInt(NewsContract.getColumnPos(NewsContract.NewsEntry.COLUMN_NAME_USER_ID))
        );
    }

    @Override
    public NewsEntry getNews(int id) {
        NewsEntry news = null;
        SQLiteDatabase db = null;
        try {
            db = dbHelper.getReadableDatabase();

            //pazi na redoslijed stupaca u Contract
            String[] projection = {
                    NewsContract.NewsEntry._ID,
                    NewsContract.NewsEntry.COLUMN_NAME_TIME,
                    NewsContract.NewsEntry.COLUMN_NAME_TITLE,
                    NewsContract.NewsEntry.COLUMN_NAME_DESCRIPTION,
                    NewsContract.NewsEntry.COLUMN_NAME_USER_ID
            };

            Cursor cursor = db.query(
                    NewsContract.NewsEntry.TABLE_NAME,
                    projection,
                    NewsContract.NewsEntry._ID + "=?",
                    new String[]{String.valueOf(id)},
                    null,
                    null,
                    null
            );

            if (cursor != null) {
                cursor.moveToFirst();
                news = newsEntryFromCursor(cursor);
                cursor.close();
            }

        } catch (Exception exc) {
            //TO DO: Call Logger.ShowError;
        } finally {
            if (db != null)
                db.close();
        }
        return news;
    }

    public void deleteNews(NewsEntry news) {
        SQLiteDatabase db = null;
        try {
            db = dbHelper.getWritableDatabase();
            db.delete(NewsContract.NewsEntry.TABLE_NAME, NewsContract.NewsEntry._ID + "=?", new String[]{String.valueOf(news.getId())});
        } catch (Exception exc) {
            //TO DO: Call Logger.ShowError;
        } finally {
            if (db != null)
                db.close();
        }
    }


    public void updateNews(NewsEntry news) {
        SQLiteDatabase db = null;
        try {
            db = dbHelper.getReadableDatabase();
            ContentValues values = new ContentValues();
            values.put(NewsContract.NewsEntry.COLUMN_NAME_TITLE, news.getTitle());
            values.put(NewsContract.NewsEntry.COLUMN_NAME_DESCRIPTION, news.getText());
            values.put(NewsContract.NewsEntry.COLUMN_NAME_USER_ID, news.getAuthorId());
            values.put(NewsContract.NewsEntry.COLUMN_NAME_TIME, DateParserUtil.dateToString(news.getTimeOfCreation()));
            db.update(NewsContract.NewsEntry.TABLE_NAME, values, NewsContract.NewsEntry._ID + "=?", new String[]{String.valueOf(news.getId())});
        } catch (Exception exc) {
            //TO DO: Call Logger.ShowError;
        } finally {
            if (db != null)
                db.close();
        }
    }
}

package hr.fer.elektrijada.dal.sql.news;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;
import java.util.List;

import hr.fer.elektrijada.model.news.NewsEntry;
import hr.fer.elektrijada.model.news.NewsRepository;
import hr.fer.elektrijada.util.DateParserUtil;

/**
 * Created by Boris Milašinović on 10.11.2015..
 */
public class SqlNewsRepository implements NewsRepository {
    SQLiteOpenHelper dbHelper;
    public SqlNewsRepository(Context context){
        dbHelper = new NewsDbHelper(context);
    }
    public boolean createNewsEntry(NewsEntry news){
        SQLiteDatabase db = null;
        try{
            db =  dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(NewsContract.NewsEntry.COLUMN_NAME_TITLE, news.getTitle());
            values.put(NewsContract.NewsEntry.COLUMN_NAME_DESCRIPTION, news.getText());
            values.put(NewsContract.NewsEntry.COLUMN_NAME_USER_ID, news.getAuthorId());
            values.put(NewsContract.NewsEntry.COLUMN_NAME_TIME, news.getTimeToString());

            long rowId = db.insert(NewsContract.NewsEntry.TABLE_NAME, null, values); //TODO: popraviti insert, ovo vraca 1, sto znaci da se dogodio error prilikom inserta
            return rowId != -1;
        }
        catch(Exception exc){
            //TO DO: Call Logger.ShowError;
            return false;
        }
        finally {
            if (db != null)
                db.close();
        }
    }

    public int getNewsCount(){
        int count = 0;
        SQLiteDatabase db = null;
        try {
            db = dbHelper.getReadableDatabase();
            SQLiteStatement statement = db.compileStatement("SELECT COUNT(*) FROM " + NewsContract.NewsEntry.TABLE_NAME);
            count = (int) statement.simpleQueryForLong();
        }
        catch(Exception exc){
            //TO DO: Call Logger.ShowError;
        }
        finally {
            if (db != null)
                db.close();
        }
        return count;
    }

    public List<NewsEntry> getNews(){
        List<NewsEntry> listOfNews = new ArrayList<>();
        SQLiteDatabase db = null;
        try {
            db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM " + NewsContract.NewsEntry.TABLE_NAME, null);
            if (cursor.moveToFirst()) {
                do {
                    NewsEntry news = new NewsEntry(
                            cursor.getInt(NewsContract.getColumnPos(NewsContract.NewsEntry.COLUMN_NAME_USER_ID)),
                            cursor.getString(NewsContract.getColumnPos(NewsContract.NewsEntry.COLUMN_NAME_TITLE)),
                            cursor.getString(NewsContract.getColumnPos(NewsContract.NewsEntry.COLUMN_NAME_DESCRIPTION)),
                            DateParserUtil.stringToDate( cursor.getString(NewsContract.getColumnPos(NewsContract.NewsEntry.COLUMN_NAME_TIME))),
                            cursor.getInt(NewsContract.getColumnPos(NewsContract.NewsEntry._ID))
                    );
                    listOfNews.add(news);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        catch(Exception exc){
            //TO DO: Call Logger.ShowError;
        }
        finally {
            if (db != null)
                db.close();
        }
        return listOfNews;
    }

    /*
     public NewsEntry getNews(int id){
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(  //TODO: popraviti... javlja exception da ne postoji tablica
                TABLE_NEWS,
                new String[] { KEY_ID, KEY_TITLE, KEY_TEXT, KEY_TIME, KEY_USER_ID},
                KEY_ID +"=?",
                new String[] { String.valueOf(id)},
                null,
                null,
                null,
                null
        );

        if(cursor != null){
            cursor.moveToFirst();
        }


        NewsEntry news = new NewsEntry(
                Integer.parseInt(cursor.getString(4)),  //autor Id
                cursor.getString(1),                    //naslov
                cursor.getString(2),                    //tekst
                stringToDate(cursor.getString(3)),      //vrijeme
                Integer.parseInt(cursor.getString(0))   //id vijesti
        );
        db.close();
        cursor.close();
        return news;
    }



    public void deleteNews(NewsEntry news){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NEWS, KEY_ID + "=?", new String[] { String.valueOf(news.getId())});
        db.close();
    }



    public void updateNews(NewsEntry news){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, news.getTitle());
        values.put(KEY_TEXT, news.getText());
        values.put(KEY_TIME, getTimeFormat().format(news.getTimeOfCreation()));
        values.put(KEY_USER_ID, news.getAuthorId());
        db.update(TABLE_NEWS, values, KEY_ID + "=?", new String[] { String.valueOf(news.getId())});
        db.close();
    }
     */
}

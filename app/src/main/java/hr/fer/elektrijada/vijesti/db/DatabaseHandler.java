package hr.fer.elektrijada.vijesti.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import hr.fer.elektrijada.vijesti.News;

/**
 * Created by Ivica Brebrek
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "newsDb",
                                TABLE_NEWS = "news",
                                KEY_ID = "id",
                                KEY_TITLE = "title",
                                KEY_TEXT = "text",
                                KEY_TIME = "time",
                                KEY_USER_ID = "userId";

    public DatabaseHandler (Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NEWS + "(" +
                        KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_TITLE + " TEXT, " + KEY_TEXT + " TEXT, " + KEY_TIME + " TEXT, " + KEY_USER_ID + " INTEGER)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NEWS);
        onCreate(db);
    }

    public void createNews(News news){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, news.getTitle());
        values.put(KEY_TEXT, news.getText());
        //NOTE: vrijeme zapisujem kao TEXT, uvijek formatiraj iz objekta u bazu i obrnuto
        values.put(KEY_TIME, getTimeFormat().format(news.getTimeOfCreation()));
        values.put(KEY_USER_ID, news.getAuthorId());

        db.insert(TABLE_NEWS, null, values); //TODO: popraviti insert, ovo vraca 1, sto znaci da se dogodio error prilikom inserta
        db.close();

    }

    public News getNews(int id){
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


        News news = new News(
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

    private SimpleDateFormat getTimeFormat(){
        return new SimpleDateFormat("HH:mm dd.MM.yyyy.", Locale.getDefault());
    }

    private Date stringToDate(String string){
        //jer mi neda da ne obradim gresku iako znam da ce uvijek biti format iz getTimeFormat....
        Date date;
        try {
            date = getTimeFormat().parse(string);
        }catch (ParseException exc){
            date = new Date(0);
        }
        return date;
    }

    public void deleteNews(News news){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NEWS, KEY_ID + "=?", new String[] { String.valueOf(news.getId())});
        db.close();
    }

    public int getNewsCount(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NEWS, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        return count;
    }

    public void updateNews(News news){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, news.getTitle());
        values.put(KEY_TEXT, news.getText());
        values.put(KEY_TIME, getTimeFormat().format(news.getTimeOfCreation()));
        values.put(KEY_USER_ID, news.getAuthorId());
        db.update(TABLE_NEWS, values, KEY_ID + "=?", new String[] { String.valueOf(news.getId())});
        db.close();
    }

    public ArrayList<News> getAllNews(){
        ArrayList<News> listOfNews = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+ TABLE_NEWS, null);
        if(cursor.moveToFirst()){
            do{
                News news = new News(
                        Integer.parseInt(cursor.getString(4)),
                        cursor.getString(1),
                        cursor.getString(2),
                        stringToDate(cursor.getString(3)),
                        Integer.parseInt(cursor.getString(0))
                );
                listOfNews.add(news);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return listOfNews;
    }
}

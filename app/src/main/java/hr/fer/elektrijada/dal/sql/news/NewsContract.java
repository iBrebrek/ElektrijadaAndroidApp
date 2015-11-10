package hr.fer.elektrijada.dal.sql.news;

import android.provider.BaseColumns;

import hr.fer.elektrijada.model.news.NewsEntry;

/**
 * Created by Boris Milašinović on 10.11.2015..
 */
public final class NewsContract {
    private NewsContract(){

    }

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + NewsEntry.TABLE_NAME + " (" +
                    NewsEntry._ID + " INTEGER PRIMARY KEY," +
                    NewsEntry.COLUMN_NAME_TIME + TEXT_TYPE + COMMA_SEP +
                    NewsEntry.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP +
                    NewsEntry.COLUMN_NAME_DESCRIPTION + TEXT_TYPE + COMMA_SEP +
                    NewsEntry.COLUMN_NAME_USER_ID + TEXT_TYPE + COMMA_SEP +
            " )";

    static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + NewsEntry.TABLE_NAME;

    //Boris: umjesto metode mogle su se definirati i int konstante... Kasnije mi je tek palo na pamet
    public static  int getColumnPos(String columnName){
        switch(columnName){
            case NewsEntry._ID:
                return 0;
            case NewsEntry.COLUMN_NAME_TIME:
                return 1;
            case NewsEntry.COLUMN_NAME_TITLE:
                return 2;
            case NewsEntry.COLUMN_NAME_DESCRIPTION:
                return 3;
            case NewsEntry.COLUMN_NAME_USER_ID:
                return 4;
            default:
                throw new RuntimeException(String.format("Invalid column:%s for table %s",
                        columnName, NewsEntry.TABLE_NAME));
        }
    }

    /* Inner class that defines the table contents */
    public static abstract class NewsEntry implements BaseColumns {
        public static final String TABLE_NAME = "news";
        public static final String COLUMN_NAME_TIME = "time";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static final String COLUMN_NAME_USER_ID = "userid";
    }
}

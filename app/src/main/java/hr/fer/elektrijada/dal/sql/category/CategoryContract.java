package hr.fer.elektrijada.dal.sql.category;

import android.provider.BaseColumns;

/**
 * Created by Sebastian Glad on 14.11.2015..
 */
public class CategoryContract {
    private CategoryContract(){

    }

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";

    //SQL za stvaranje i brisanje tablice category
    public static final String SQL_CREATE_CATEGORY =
            "CREATE TABLE " + CategoryEntry.TABLE_NAME + " (" +
                    CategoryEntry._ID + " INTEGER PRIMARY KEY," +
                    CategoryEntry.COLUMN_NAME_NAME + TEXT_TYPE + COMMA_SEP +
                    CategoryEntry.COLUMN_NAME_NICK + TEXT_TYPE + COMMA_SEP +
                    CategoryEntry.COLUMN_NAME_IS_SPORT + " BOOLEAN, " +
                    CategoryEntry.COLUMN_NAME_IS_DUEL + " BOOLEAN" +
                    " )";

    public static final String SQL_DELETE_CATEGORY =
            "DROP TABLE IF EXISTS " + CategoryEntry.TABLE_NAME;

    //Boris: umjesto metode mogle su se definirati i int konstante... Kasnije mi je tek palo na pamet
    public static  int getColumnPos(String columnName){
        switch(columnName){
            case CategoryEntry._ID:
                return 0;
            case CategoryEntry.COLUMN_NAME_NAME:
                return 1;
            case CategoryEntry.COLUMN_NAME_NICK:
                return 2;
            case CategoryEntry.COLUMN_NAME_IS_SPORT:
                return 3;
            case CategoryEntry.COLUMN_NAME_IS_DUEL:
                return 4;
            default:
                throw new RuntimeException(String.format("Invalid column:%s for table %s",
                        columnName, CategoryEntry.TABLE_NAME));
        }
    }

    /* Inner class that defines the table contents */
    public static abstract class CategoryEntry implements BaseColumns {
        public static final String TABLE_NAME = "category";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_NICK = "nick";
        public static final String COLUMN_NAME_IS_SPORT = "issport";
        public static final String COLUMN_NAME_IS_DUEL = "isduel";
    }
}

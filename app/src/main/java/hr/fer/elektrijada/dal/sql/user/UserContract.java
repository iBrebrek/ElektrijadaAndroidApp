package hr.fer.elektrijada.dal.sql.user;

import android.provider.BaseColumns;

/**
 * Created by Sebastian Glad on 14.11.2015..
 */
public class UserContract {


    private UserContract(){

    }

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";

    //SQL za stvaranje i brisanje tablice user
    public static final String SQL_CREATE_USER =
            "CREATE TABLE " + UserEntry.TABLE_NAME + " (" +
                    UserEntry._ID + " INTEGER PRIMARY KEY," +
                    UserEntry.COLUMN_NAME_NAME + TEXT_TYPE + COMMA_SEP +
                    UserEntry.COLUMN_NAME_SURNAME + TEXT_TYPE + COMMA_SEP +
                    UserEntry.COLUMN_NAME_LOGINTYPE + TEXT_TYPE +
                    " )";

    public static final String SQL_DELETE_USER =
            "DROP TABLE IF EXISTS " + UserEntry.TABLE_NAME;

    //Boris: umjesto metode mogle su se definirati i int konstante... Kasnije mi je tek palo na pamet
    public static  int getColumnPos(String columnName){
        switch(columnName){
            case UserEntry._ID:
                return 0;
            case UserEntry.COLUMN_NAME_NAME:
                return 1;
            case UserEntry.COLUMN_NAME_SURNAME:
                return 2;
            case UserEntry.COLUMN_NAME_LOGINTYPE:
                return 3;
            default:
                throw new RuntimeException(String.format("Invalid column:%s for table %s",
                        columnName, UserEntry.TABLE_NAME));
        }
    }

    /* Inner class that defines the table contents */
    public static abstract class UserEntry implements BaseColumns {
        public static final String TABLE_NAME = "user";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_SURNAME = "surname";
        public static final String COLUMN_NAME_LOGINTYPE = "logintype";

    }
}

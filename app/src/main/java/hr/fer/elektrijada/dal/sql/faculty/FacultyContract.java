package hr.fer.elektrijada.dal.sql.faculty;

import android.provider.BaseColumns;

/**
 * Created by Sebastian Glad on 13.11.2015..
 */
public class FacultyContract {

    private FacultyContract(){

    }

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";

    //SQL za stvaranje i brisanje tablice faculty
    public static final String SQL_CREATE_FACULTY =
            "CREATE TABLE " + FacultyEntry.TABLE_NAME + " (" +
                    FacultyEntry._ID + " INTEGER PRIMARY KEY," +
                    FacultyEntry.COLUMN_NAME_NAME + TEXT_TYPE + COMMA_SEP +
                    FacultyEntry.COLUMN_NAME_NICK + TEXT_TYPE +
                    " )";

    public static final String SQL_DELETE_FACULTY =
            "DROP TABLE IF EXISTS " + FacultyEntry.TABLE_NAME;

    //Boris: umjesto metode mogle su se definirati i int konstante... Kasnije mi je tek palo na pamet
    public static  int getColumnPos(String columnName){
        switch(columnName){
            case FacultyEntry._ID:
                return 0;
            case FacultyEntry.COLUMN_NAME_NAME:
                return 1;
            case FacultyEntry.COLUMN_NAME_NICK:
                return 2;
            default:
                throw new RuntimeException(String.format("Invalid column:%s for table %s",
                        columnName, FacultyEntry.TABLE_NAME));
        }
    }

    /* Inner class that defines the table contents */
    public static abstract class FacultyEntry implements BaseColumns {
        public static final String TABLE_NAME = "faculty";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_NICK = "nick";

    }
}

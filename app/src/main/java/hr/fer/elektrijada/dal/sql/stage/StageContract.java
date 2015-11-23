package hr.fer.elektrijada.dal.sql.stage;

import android.provider.BaseColumns;

/**
 * Created by Sebastian Glad on 14.11.2015..
 */
public class StageContract {

    private StageContract(){

    }

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";

    //SQL za stvaranje i brisanje tablice stage
    public static final String SQL_CREATE_STAGE =
            "CREATE TABLE " + StageEntry.TABLE_NAME + " (" +
                    StageEntry._ID + " INTEGER PRIMARY KEY," +
                    StageEntry.COLUMN_NAME_NAME + TEXT_TYPE +
                    " )";

    public static final String SQL_DELETE_STAGE =
            "DROP TABLE IF EXISTS " + StageEntry.TABLE_NAME;

    //Boris: umjesto metode mogle su se definirati i int konstante... Kasnije mi je tek palo na pamet
    public static  int getColumnPos(String columnName){
        switch(columnName){
            case StageEntry._ID:
                return 0;
            case StageEntry.COLUMN_NAME_NAME:
                return 1;
            default:
                throw new RuntimeException(String.format("Invalid column:%s for table %s",
                        columnName, StageEntry.TABLE_NAME));
        }
    }

    /* Inner class that defines the table contents */
    public static abstract class StageEntry implements BaseColumns {
        public static final String TABLE_NAME = "stage";
        public static final String COLUMN_NAME_NAME = "name";

    }
}

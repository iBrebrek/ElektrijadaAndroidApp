package hr.fer.elektrijada.dal.sql.competition;

import android.provider.BaseColumns;

/**
 * Created by Sebastian Glad on 14.11.2015..
 */
public class CompetitionContract {

    private CompetitionContract(){

    }

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";

    //SQL za stvaranje i brisanje tablice competition
    public static final String SQL_CREATE_COMPETITION =
            "CREATE TABLE " + CompetitionEntry.TABLE_NAME + " (" +
                    CompetitionEntry._ID + " INTEGER PRIMARY KEY," +
                    CompetitionEntry.COLUMN_NAME_TIME_FROM + " DATETIME" + COMMA_SEP +
                    CompetitionEntry.COLUMN_NAME_TIME_TO + " DATETIME" + COMMA_SEP +
                    CompetitionEntry.COLUMN_NAME_CATEGORY_ID + " INTEGER" + COMMA_SEP +
                    CompetitionEntry.COLUMN_NAME_LOCATION + TEXT_TYPE + COMMA_SEP +
                    CompetitionEntry.COLUMN_NAME_IS_ASSUMPTION + " BOOLEAN" +
                    " )";

    public static final String SQL_DELETE_COMPETITION =
            "DROP TABLE IF EXISTS " + CompetitionEntry.TABLE_NAME;

    //Boris: umjesto metode mogle su se definirati i int konstante... Kasnije mi je tek palo na pamet
    public static  int getColumnPos(String columnName){
        switch(columnName){
            case CompetitionEntry._ID:
                return 0;
            case CompetitionEntry.COLUMN_NAME_TIME_FROM:
                return 1;
            case CompetitionEntry.COLUMN_NAME_TIME_TO:
                return 2;
            case CompetitionEntry.COLUMN_NAME_CATEGORY_ID:
                return 3;
            case CompetitionEntry.COLUMN_NAME_LOCATION:
                return 4;
            case CompetitionEntry.COLUMN_NAME_IS_ASSUMPTION:
                return 5;
            default:
                throw new RuntimeException(String.format("Invalid column:%s for table %s",
                        columnName, CompetitionEntry.TABLE_NAME));
        }
    }

    /* Inner class that defines the table contents */
    public static abstract class CompetitionEntry implements BaseColumns {
        public static final String TABLE_NAME = "competition";
        public static final String COLUMN_NAME_TIME_FROM = "timefrom";
        public static final String COLUMN_NAME_TIME_TO = "timeto";
        public static final String COLUMN_NAME_CATEGORY_ID = "categoryid";
        public static final String COLUMN_NAME_LOCATION = "location";
        public static final String COLUMN_NAME_IS_ASSUMPTION = "isassumption";
    }
}

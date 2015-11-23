package hr.fer.elektrijada.dal.sql.competitor;

import android.provider.BaseColumns;

/**
 * Created by Sebastian Glad on 14.11.2015..
 */
public class CompetitorContract {
    private CompetitorContract(){

    }

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";

    //SQL za stvaranje i brisanje tablice competitor
    public static final String SQL_CREATE_COMPETITOR =
            "CREATE TABLE " + CompetitorEntry.TABLE_NAME + " (" +
                    CompetitorEntry._ID + " INTEGER PRIMARY KEY," +
                    CompetitorEntry.COLUMN_NAME_NAME + TEXT_TYPE + COMMA_SEP +
                    CompetitorEntry.COLUMN_NAME_SURNAME + TEXT_TYPE + COMMA_SEP +
                    CompetitorEntry.COLUMN_NAME_IS_PERSON + " BOOLEAN" + COMMA_SEP +
                    CompetitorEntry.COLUMN_NAME_GROUP_COMPETITOR_ID + " INTEGER" + COMMA_SEP +
                    CompetitorEntry.COLUMN_NAME_FACULTY_ID + " INTEGER" + COMMA_SEP +
                    CompetitorEntry.COLUMN_NAME_COMPETITION_ID + " INTEGER" + COMMA_SEP +
                    CompetitorEntry.COLUMN_NAME_ORDINAL_NUM + " INTEGER" + COMMA_SEP +
                    CompetitorEntry.COLUMN_NAME_IS_DISQUALIFIED + " BOOLEAN" +
                    " )";

    public static final String SQL_DELETE_COMPETITOR =
            "DROP TABLE IF EXISTS " + CompetitorEntry.TABLE_NAME;

    //Boris: umjesto metode mogle su se definirati i int konstante... Kasnije mi je tek palo na pamet
    public static  int getColumnPos(String columnName){
        switch(columnName){
            case CompetitorEntry._ID:
                return 0;
            case CompetitorEntry.COLUMN_NAME_NAME:
                return 1;
            case CompetitorEntry.COLUMN_NAME_SURNAME:
                return 2;
            case CompetitorEntry.COLUMN_NAME_IS_PERSON:
                return 3;
            case CompetitorEntry.COLUMN_NAME_GROUP_COMPETITOR_ID:
                return 4;
            case CompetitorEntry.COLUMN_NAME_FACULTY_ID:
                return 5;
            case CompetitorEntry.COLUMN_NAME_COMPETITION_ID:
                return 6;
            case CompetitorEntry.COLUMN_NAME_ORDINAL_NUM:
                return 7;
            case CompetitorEntry.COLUMN_NAME_IS_DISQUALIFIED:
                return 8;
            default:
                throw new RuntimeException(String.format("Invalid column:%s for table %s",
                        columnName, CompetitorEntry.TABLE_NAME));
        }
    }

    /* Inner class that defines the table contents */
    public static abstract class CompetitorEntry implements BaseColumns {
        public static final String TABLE_NAME = "competitor";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_SURNAME = "surname";
        public static final String COLUMN_NAME_IS_PERSON = "isperson";
        public static final String COLUMN_NAME_GROUP_COMPETITOR_ID = "groupcompetitorid";
        public static final String COLUMN_NAME_FACULTY_ID = "facultyid";
        public static final String COLUMN_NAME_COMPETITION_ID = "competitionid";
        public static final String COLUMN_NAME_ORDINAL_NUM = "ordinalnum";
        public static final String COLUMN_NAME_IS_DISQUALIFIED = "isdisqualified";
    }
}

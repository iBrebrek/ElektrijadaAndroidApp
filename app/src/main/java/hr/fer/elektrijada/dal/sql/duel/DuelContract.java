package hr.fer.elektrijada.dal.sql.duel;

import android.provider.BaseColumns;

/**
 * Created by Sebastian Glad on 14.11.2015..
 */
public class DuelContract {
    private DuelContract(){

    }

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";

    //SQL za stvaranje i brisanje tablice duel
    public static final String SQL_CREATE_DUEL =
            "CREATE TABLE " + DuelEntry.TABLE_NAME + " (" +
                    DuelEntry._ID + " INTEGER PRIMARY KEY," +
                    DuelEntry.COLUMN_NAME_TIME_FROM + TEXT_TYPE + COMMA_SEP +
                    DuelEntry.COLUMN_NAME_TIME_TO + TEXT_TYPE + COMMA_SEP +
                    DuelEntry.COLUMN_NAME_CATEGORY_ID + " INTEGER" + COMMA_SEP +
                    DuelEntry.COLUMN_NAME_COMPETITOR_1_ID + " INTEGER" + COMMA_SEP +
                    DuelEntry.COLUMN_NAME_COMPETITOR_2_ID + " INTEGER" + COMMA_SEP +
                    DuelEntry.COLUMN_NAME_STAGE_ID + " INTEGER" + COMMA_SEP +
                    DuelEntry.COLUMN_NAME_LOCATION + TEXT_TYPE + COMMA_SEP +
                    DuelEntry.COLUMN_NAME_IS_ASSUMPTION + " BOOLEAN" + COMMA_SEP +
                    DuelEntry.COLUMN_NAME_SECTION + TEXT_TYPE +

    " )";

    public static final String SQL_DELETE_DUEL =
            "DROP TABLE IF EXISTS " + DuelEntry.TABLE_NAME;

    //Boris: umjesto metode mogle su se definirati i int konstante... Kasnije mi je tek palo na pamet
    public static  int getColumnPos(String columnName){
        switch(columnName){
            case DuelEntry._ID:
                return 0;
            case DuelEntry.COLUMN_NAME_TIME_FROM:
                return 1;
            case DuelEntry.COLUMN_NAME_TIME_TO:
                return 2;
            case DuelEntry.COLUMN_NAME_CATEGORY_ID:
                return 3;
            case DuelEntry.COLUMN_NAME_COMPETITOR_1_ID:
                return 4;
            case DuelEntry.COLUMN_NAME_COMPETITOR_2_ID:
                return 5;
            case DuelEntry.COLUMN_NAME_STAGE_ID:
                return 6;
            case DuelEntry.COLUMN_NAME_LOCATION:
                return 7;
            case DuelEntry.COLUMN_NAME_IS_ASSUMPTION:
                return 8;
            case DuelEntry.COLUMN_NAME_SECTION:
                return 9;
            default:
                throw new RuntimeException(String.format("Invalid column:%s for table %s",
                        columnName, DuelEntry.TABLE_NAME));
        }
    }

    /* Inner class that defines the table contents */
    public static abstract class DuelEntry implements BaseColumns {
        public static final String TABLE_NAME = "duel";
        public static final String COLUMN_NAME_TIME_FROM = "timefrom";
        public static final String COLUMN_NAME_TIME_TO = "timeto";
        public static final String COLUMN_NAME_CATEGORY_ID = "categoryid";
        public static final String COLUMN_NAME_COMPETITOR_1_ID = "competitor1id";
        public static final String COLUMN_NAME_COMPETITOR_2_ID = "competitor2id";
        public static final String COLUMN_NAME_STAGE_ID = "stageid";
        public static final String COLUMN_NAME_LOCATION = "location";
        public static final String COLUMN_NAME_IS_ASSUMPTION = "isassumption";
        public static final String COLUMN_NAME_SECTION = "section";
    }
}

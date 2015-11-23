package hr.fer.elektrijada.dal.sql.duelscore;

import android.provider.BaseColumns;


/**
 * Created by Sebastian Glad on 14.11.2015..
 */
public class DuelScoreContract {
    private DuelScoreContract(){

    }

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";

    //SQL za stvaranje i brisanje tablice duelscore
    public static final String SQL_CREATE_DUEL_SCORE =
            "CREATE TABLE " + DuelScoreEntry.TABLE_NAME + " (" +
                    DuelScoreEntry._ID + " INTEGER PRIMARY KEY," +
                    DuelScoreEntry.COLUMN_NAME_SCORE_1 + " DECIMAL" + COMMA_SEP +
                    DuelScoreEntry.COLUMN_NAME_SCORE_2 + " DECIMAL" + COMMA_SEP +
                    DuelScoreEntry.COLUMN_NAME_DUEL_ID + " INTEGER" + COMMA_SEP +
                    DuelScoreEntry.COLUMN_NAME_USER_ID + " INTEGER" + COMMA_SEP +
                    DuelScoreEntry.COLUMN_NAME_IS_ASSUMPTION + " BOOLEAN" + COMMA_SEP +
                    DuelScoreEntry.COLUMN_NAME_NOTE + TEXT_TYPE + COMMA_SEP +
                    DuelScoreEntry.COLUMN_NAME_IS_OFFICIAL + " BOOLEAN" +

                    " )";

    public static final String SQL_DELETE_DUEL_SCORE =
            "DROP TABLE IF EXISTS " + DuelScoreEntry.TABLE_NAME;

    //Boris: umjesto metode mogle su se definirati i int konstante... Kasnije mi je tek palo na pamet
    public static  int getColumnPos(String columnName){
        switch(columnName){
            case DuelScoreEntry._ID:
                return 0;
            case DuelScoreEntry.COLUMN_NAME_SCORE_1:
                return 1;
            case DuelScoreEntry.COLUMN_NAME_SCORE_2:
                return 2;
            case DuelScoreEntry.COLUMN_NAME_DUEL_ID:
                return 3;
            case DuelScoreEntry.COLUMN_NAME_USER_ID:
                return 4;
            case DuelScoreEntry.COLUMN_NAME_IS_ASSUMPTION:
                return 5;
            case DuelScoreEntry.COLUMN_NAME_NOTE:
                return 6;
            case DuelScoreEntry.COLUMN_NAME_IS_OFFICIAL:
                return 7;
            default:
                throw new RuntimeException(String.format("Invalid column:%s for table %s",
                        columnName, DuelScoreEntry.TABLE_NAME));
        }
    }

    /* Inner class that defines the table contents */
    public static abstract class DuelScoreEntry implements BaseColumns {
        public static final String TABLE_NAME = "duelscore";
        public static final String COLUMN_NAME_SCORE_1 = "score1";
        public static final String COLUMN_NAME_SCORE_2 = "score2";
        public static final String COLUMN_NAME_DUEL_ID = "duelid";
        public static final String COLUMN_NAME_USER_ID = "userid";
        public static final String COLUMN_NAME_IS_ASSUMPTION = "isasssumptio";
        public static final String COLUMN_NAME_NOTE = "note";
        public static final String COLUMN_NAME_IS_OFFICIAL = "isofficial";

    }
}

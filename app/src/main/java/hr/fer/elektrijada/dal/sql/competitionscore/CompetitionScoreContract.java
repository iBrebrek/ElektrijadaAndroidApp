package hr.fer.elektrijada.dal.sql.competitionscore;

import android.provider.BaseColumns;

/**
 * Created by Sebastian Glad on 14.11.2015..
 */
public class CompetitionScoreContract {
    private CompetitionScoreContract(){

    }

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";

    //SQL za stvaranje i brisanje tablice competitionscore
    public static final String SQL_CREATE_COMPETITION_SCORE =
            "CREATE TABLE " + CompetitionScoreEntry.TABLE_NAME + " (" +
                    CompetitionScoreEntry._ID + " INTEGER PRIMARY KEY," +
                    CompetitionScoreEntry.COLUMN_NAME_RESULT + " DECIMAL" + COMMA_SEP +
                    CompetitionScoreEntry.COLUMN_NAME_NOTE + TEXT_TYPE + COMMA_SEP +
                    CompetitionScoreEntry.COLUMN_NAME_COMPETITION_ID + " INTEGER" + COMMA_SEP +
                    CompetitionScoreEntry.COLUMN_NAME_USER_ID + " INTEGER" + COMMA_SEP +
                    CompetitionScoreEntry.COLUMN_NAME_COMPETITOR_ID + " INTEGER" + COMMA_SEP +
                    CompetitionScoreEntry.COLUMN_NAME_IS_ASSUMPTION + " BOOLEAN" + COMMA_SEP +
                    CompetitionScoreEntry.COLUMN_NAME_IS_OFFICIAL + " BOOLEAN" +
                    " )";

    public static final String SQL_DELETE_COMPETITION_SCORE =
            "DROP TABLE IF EXISTS " + CompetitionScoreEntry.TABLE_NAME;

    //Boris: umjesto metode mogle su se definirati i int konstante... Kasnije mi je tek palo na pamet
    public static  int getColumnPos(String columnName){
        switch(columnName){
            case CompetitionScoreEntry._ID:
                return 0;
            case CompetitionScoreEntry.COLUMN_NAME_RESULT:
                return 1;
            case CompetitionScoreEntry.COLUMN_NAME_NOTE:
                return 2;
            case CompetitionScoreEntry.COLUMN_NAME_COMPETITION_ID:
                return 3;
            case CompetitionScoreEntry.COLUMN_NAME_USER_ID:
                return 4;
            case CompetitionScoreEntry.COLUMN_NAME_COMPETITOR_ID:
                return 5;
            case CompetitionScoreEntry.COLUMN_NAME_IS_ASSUMPTION:
                return 6;
            case CompetitionScoreEntry.COLUMN_NAME_IS_OFFICIAL:
                return 7;

            default:
                throw new RuntimeException(String.format("Invalid column:%s for table %s",
                        columnName, CompetitionScoreEntry.TABLE_NAME));
        }
    }

    /* Inner class that defines the table contents */
    public static abstract class CompetitionScoreEntry implements BaseColumns {
        public static final String TABLE_NAME = "competitionscore";
        public static final String COLUMN_NAME_RESULT = "result";
        public static final String COLUMN_NAME_NOTE = "note";
        public static final String COLUMN_NAME_COMPETITION_ID = "competitionid";
        public static final String COLUMN_NAME_USER_ID = "userid";
        public static final String COLUMN_NAME_COMPETITOR_ID = "competitorid";
        public static final String COLUMN_NAME_IS_ASSUMPTION = "isassumption";
        public static final String COLUMN_NAME_IS_OFFICIAL = "isofficial";
    }
}

package hr.fer.elektrijada.dal.sql.competition;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import hr.fer.elektrijada.dal.sql.DbHelper;

/**
 * Created by Ivica Brebrek
 */
public class SqlCompetitionRepository {
    private SQLiteOpenHelper dbHelper;

    public SqlCompetitionRepository(Context context) {
        dbHelper = new DbHelper(context);
    }

    public void close() {
        if (dbHelper != null) {
            dbHelper.close();
        }
    }

    //TODO: napisati ostale metode

    public boolean createNewCompetition(CompetitionFromDb competition) {
        SQLiteDatabase db = null;
        try {
            db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(CompetitionContract.CompetitionEntry.COLUMN_NAME_TIME_FROM,competition.timeFrom);
            if(competition.timeTo != null) {
                values.put(CompetitionContract.CompetitionEntry.COLUMN_NAME_TIME_TO, competition.timeTo);
            }
            values.put(CompetitionContract.CompetitionEntry.COLUMN_NAME_CATEGORY_ID, competition.categoryId);
            if (competition.location != null) {
                values.put(CompetitionContract.CompetitionEntry.COLUMN_NAME_LOCATION, competition.location);
            }
            values.put(CompetitionContract.CompetitionEntry.COLUMN_NAME_IS_ASSUMPTION, competition.isAssumption);

            long rowId = db.insert(CompetitionContract.CompetitionEntry.TABLE_NAME, null, values);
            return rowId != -1;
        } catch (Exception exc) {
            //TO DO: Call Logger.ShowError;
            return false;
        } finally {
            if (db != null)
                db.close();
        }
    }

    public static class CompetitionFromDb{
        private int id;
        private String  timeFrom;
        private String timeTo;
        private int categoryId;
        private String location;
        private boolean isAssumption;

        //napisi si nove konstuktore ako ti trebaju, meni nije trebao id za izradu novog natjecanja
        public CompetitionFromDb(String timeFrom, String timeTo, int categoryId, String location, boolean isAssumption) {
            this.timeFrom = timeFrom;
            this.timeTo = timeTo;
            this.categoryId = categoryId;
            this.location = location;
            this.isAssumption = isAssumption;
        }
    }
}

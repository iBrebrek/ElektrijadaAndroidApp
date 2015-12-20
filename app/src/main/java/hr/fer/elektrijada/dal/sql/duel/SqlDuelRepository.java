package hr.fer.elektrijada.dal.sql.duel;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import hr.fer.elektrijada.dal.sql.DbHelper;

/**
 * Created by Ivica Brebrek
 */
public class SqlDuelRepository {
    private SQLiteOpenHelper dbHelper;

    public SqlDuelRepository(Context context) {
        dbHelper = new DbHelper(context);
    }

    public void close() {
        if (dbHelper != null) {
            dbHelper.close();
        }
    }

    //TODO: napisati ostale metode

    public boolean createNewDuel(DuelFromDb duel) {
        SQLiteDatabase db = null;
        try {
            db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(DuelContract.DuelEntry.COLUMN_NAME_COMPETITOR_1_ID, duel.firstId);
            values.put(DuelContract.DuelEntry.COLUMN_NAME_COMPETITOR_2_ID, duel.secondId);
            values.put(DuelContract.DuelEntry.COLUMN_NAME_TIME_FROM, duel.timeFrom);
            if(duel.timeTo != null) {
                values.put(DuelContract.DuelEntry.COLUMN_NAME_TIME_TO, duel.timeTo);
            }
            values.put(DuelContract.DuelEntry.COLUMN_NAME_CATEGORY_ID, duel.categoryId);
            if (duel.location != null) {
                values.put(DuelContract.DuelEntry.COLUMN_NAME_LOCATION, duel.location);
            }
            values.put(DuelContract.DuelEntry.COLUMN_NAME_IS_ASSUMPTION, duel.isAssumption);

            long rowId = db.insert(DuelContract.DuelEntry.TABLE_NAME, null, values);
            return rowId != -1;
        } catch (Exception exc) {
            //TO DO: Call Logger.ShowError;
            return false;
        } finally {
            if (db != null)
                db.close();
        }
    }

    public static class DuelFromDb{
        private int id;
        private String timeFrom;
        private String timeTo;
        private int categoryId;
        private int firstId;
        private int secondId;
        private int stageId;
        private String location;
        private boolean isAssumption;
        private String section;

        //napisi si nove konstuktore ako ti trebaju, meni nije trebao id, stage i section
        public DuelFromDb(String timeFrom, String timeTo, int categoryId, int firstId, int secondId, String location, boolean isAssumption) {
            this.timeFrom = timeFrom;
            this.timeTo = timeTo;
            this.categoryId = categoryId;
            this.firstId = firstId;
            this.secondId = secondId;
            this.location = location;
            this.isAssumption = isAssumption;
        }
    }
}

package hr.fer.elektrijada.dal.sql.duel;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import hr.fer.elektrijada.dal.sql.DbHelper;
import hr.fer.elektrijada.util.DateParserUtil;

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

    //napisati ostale metode, ako zatreba

    public DuelFromDb getDuel(int id) {
        DuelFromDb duelFromDb = null;
        SQLiteDatabase db = null;
        try {
            db = dbHelper.getReadableDatabase();

            String[] projection = {
                    DuelContract.DuelEntry._ID,
                    DuelContract.DuelEntry.COLUMN_NAME_TIME_FROM,
                    DuelContract.DuelEntry.COLUMN_NAME_TIME_TO,
                    DuelContract.DuelEntry.COLUMN_NAME_CATEGORY_ID,
                    DuelContract.DuelEntry.COLUMN_NAME_COMPETITOR_1_ID,
                    DuelContract.DuelEntry.COLUMN_NAME_COMPETITOR_2_ID,
                    DuelContract.DuelEntry.COLUMN_NAME_STAGE_ID,
                    DuelContract.DuelEntry.COLUMN_NAME_LOCATION,
                    DuelContract.DuelEntry.COLUMN_NAME_IS_ASSUMPTION,
                    DuelContract.DuelEntry.COLUMN_NAME_SECTION
            };

            Cursor cursor = db.query(
                    DuelContract.DuelEntry.TABLE_NAME,
                    projection,
                    DuelContract.DuelEntry._ID + "=?",
                    new String[]{String.valueOf(id)},
                    null,
                    null,
                    null
            );

            if (cursor != null) {
                cursor.moveToFirst();
                duelFromDb = new DuelFromDb(
                        cursor.getInt(DuelContract.getColumnPos(DuelContract.DuelEntry._ID)),
                        cursor.getString(DuelContract.getColumnPos(DuelContract.DuelEntry.COLUMN_NAME_TIME_FROM)),
                        cursor.getString(DuelContract.getColumnPos(DuelContract.DuelEntry.COLUMN_NAME_TIME_TO)),
                        cursor.getInt(DuelContract.getColumnPos(DuelContract.DuelEntry.COLUMN_NAME_CATEGORY_ID)),
                        cursor.getInt(DuelContract.getColumnPos(DuelContract.DuelEntry.COLUMN_NAME_COMPETITOR_1_ID)),
                        cursor.getInt(DuelContract.getColumnPos(DuelContract.DuelEntry.COLUMN_NAME_COMPETITOR_2_ID)),
                        cursor.getInt(DuelContract.getColumnPos(DuelContract.DuelEntry.COLUMN_NAME_STAGE_ID)),
                        cursor.getString(DuelContract.getColumnPos(DuelContract.DuelEntry.COLUMN_NAME_LOCATION)),
                        cursor.getInt(DuelContract.getColumnPos(DuelContract.DuelEntry.COLUMN_NAME_IS_ASSUMPTION)) > 0,
                        cursor.getString(DuelContract.getColumnPos(DuelContract.DuelEntry.COLUMN_NAME_SECTION))
                );
                cursor.close();
            }
        } catch (Exception exc) {
            //TO DO: Call Logger.ShowError;
        } finally {
            if (db != null)
                db.close();
        }
        return duelFromDb;
    }

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

    public void updateDuel(DuelFromDb duel) {
        SQLiteDatabase db = null;
        try {
            db = dbHelper.getReadableDatabase();
            ContentValues values = new ContentValues();
            values.put(DuelContract.DuelEntry.COLUMN_NAME_TIME_FROM, duel.timeFrom);
            values.put(DuelContract.DuelEntry.COLUMN_NAME_TIME_TO, duel.timeTo);
            values.put(DuelContract.DuelEntry.COLUMN_NAME_CATEGORY_ID, duel.categoryId);
            values.put(DuelContract.DuelEntry.COLUMN_NAME_COMPETITOR_1_ID, duel.firstId);
            values.put(DuelContract.DuelEntry.COLUMN_NAME_COMPETITOR_2_ID, duel.secondId);
            values.put(DuelContract.DuelEntry.COLUMN_NAME_STAGE_ID, duel.stageId);
            values.put(DuelContract.DuelEntry.COLUMN_NAME_LOCATION, duel.location);
            values.put(DuelContract.DuelEntry.COLUMN_NAME_IS_ASSUMPTION, duel.isAssumption);
            values.put(DuelContract.DuelEntry.COLUMN_NAME_SECTION, duel.section);
            db.update(DuelContract.DuelEntry.TABLE_NAME,
                    values,
                    DuelContract.DuelEntry._ID + "=?",
                    new String[]{String.valueOf(duel.id)}
            );
        } catch (Exception exc) {
            //TO DO: Call Logger.ShowError;
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

        /*
        bez id, stageId, section
         */
        public DuelFromDb(String timeFrom, String timeTo, int categoryId, int firstId, int secondId, String location, boolean isAssumption) {
            if(timeTo != null && DateParserUtil.stringToDate(timeFrom)
                                    .compareTo(DateParserUtil.stringToDate(timeTo)) == 1) {
                throw new IllegalArgumentException("Kraj mora biti nakon početka!");
            }
            this.timeFrom = timeFrom;
            this.timeTo = timeTo;
            this.categoryId = categoryId;
            this.firstId = firstId;
            this.secondId = secondId;
            this.location = location;
            this.isAssumption = isAssumption;
        }

        /*
        konstruktor sa svim podatcima
         */
        public DuelFromDb(int id, String timeFrom, String timeTo, int categoryId, int firstId,
                          int secondId, int stageId, String location, boolean isAssumption, String section) {
            this(timeFrom, timeTo, categoryId, firstId, secondId, location, isAssumption);
            this.id = id;
            this.stageId = stageId;
            this.section = section;
        }

        public int getId() {
            return id;
        }

        public String getTimeFrom() {
            return timeFrom;
        }

        public void setTimeFrom(String timeFrom) {
            this.timeFrom = timeFrom;
        }

        public String getTimeTo() {
            return timeTo;
        }

        public void setTimeTo(String timeTo) {
            this.timeTo = timeTo;
        }

        public int getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(int categoryId) {
            this.categoryId = categoryId;
        }

        public int getFirstId() {
            return firstId;
        }

        public void setFirstId(int firstId) {
            this.firstId = firstId;
        }

        public int getSecondId() {
            return secondId;
        }

        public void setSecondId(int secondId) {
            this.secondId = secondId;
        }

        public int getStageId() {
            return stageId;
        }

        public void setStageId(int stageId) {
            this.stageId = stageId;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public boolean isAssumption() {
            return isAssumption;
        }

        public void setIsAssumption(boolean isAssumption) {
            this.isAssumption = isAssumption;
        }

        public String getSection() {
            return section;
        }

        public void setSection(String section) {
            this.section = section;
        }
    }
}

package hr.fer.elektrijada.dal.sql.duel;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
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
            values.put(DuelContract.DuelEntry.COLUMN_NAME_COMPETITOR_1_ID, duel.getFirstId());
            values.put(DuelContract.DuelEntry.COLUMN_NAME_COMPETITOR_2_ID, duel.getSecondId());
            values.put(DuelContract.DuelEntry.COLUMN_NAME_TIME_FROM, duel.getTimeFrom());
            String endingTime = duel.getTimeTo();
            if(endingTime != null) {
                values.put(DuelContract.DuelEntry.COLUMN_NAME_TIME_TO, endingTime);
            }
            values.put(DuelContract.DuelEntry.COLUMN_NAME_CATEGORY_ID, duel.getCategoryId());
            String location = duel.getLocation();
            if (location != null) {
                values.put(DuelContract.DuelEntry.COLUMN_NAME_LOCATION, location);
            }
            values.put(DuelContract.DuelEntry.COLUMN_NAME_STAGE_ID, duel.getStageId());
            values.put(DuelContract.DuelEntry.COLUMN_NAME_IS_ASSUMPTION, duel.isAssumption());

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
            values.put(DuelContract.DuelEntry.COLUMN_NAME_TIME_FROM, duel.getTimeFrom());
            values.put(DuelContract.DuelEntry.COLUMN_NAME_TIME_TO, duel.getTimeTo());
            values.put(DuelContract.DuelEntry.COLUMN_NAME_CATEGORY_ID, duel.getCategoryId());
            values.put(DuelContract.DuelEntry.COLUMN_NAME_COMPETITOR_1_ID, duel.getFirstId());
            values.put(DuelContract.DuelEntry.COLUMN_NAME_COMPETITOR_2_ID, duel.getSecondId());
            values.put(DuelContract.DuelEntry.COLUMN_NAME_STAGE_ID, duel.getStageId());
            values.put(DuelContract.DuelEntry.COLUMN_NAME_LOCATION, duel.getLocation());
            values.put(DuelContract.DuelEntry.COLUMN_NAME_IS_ASSUMPTION, duel.isAssumption());
            values.put(DuelContract.DuelEntry.COLUMN_NAME_SECTION, duel.getSection());
            db.update(DuelContract.DuelEntry.TABLE_NAME,
                    values,
                    DuelContract.DuelEntry._ID + "=?",
                    new String[]{String.valueOf(duel.getId())}
            );
        } catch (Exception exc) {
            //TO DO: Call Logger.ShowError;
        } finally {
            if (db != null)
                db.close();
        }
    }

    public void deleteDuel(DuelFromDb duel) {
        deleteDuel(duel.getId());
    }

    public void deleteDuel(int duelId) {
        SQLiteDatabase db = null;
        try {
            db = dbHelper.getWritableDatabase();
            db.delete(DuelContract.DuelEntry.TABLE_NAME, DuelContract.DuelEntry._ID + "=?", new String[]{String.valueOf(duelId)});
        } catch (Exception exc) {
            //TO DO: Call Logger.ShowError;
        } finally {
            if (db != null)
                db.close();
        }
    }
}

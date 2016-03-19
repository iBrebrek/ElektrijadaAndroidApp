package hr.fer.elektrijada.dal.sql.competition;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import hr.fer.elektrijada.dal.sql.DbHelper;
import hr.fer.elektrijada.dal.sql.competitionscore.CompetitionScoreContract;

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

    //napisati ostale metode, ako zatreba

    public boolean hasScore(int id) {
        boolean hasScore = false;
        SQLiteDatabase db = null;
        try {
            db = dbHelper.getReadableDatabase();

            Cursor cursor = db.rawQuery(
                    "SELECT " + CompetitionScoreContract.CompetitionScoreEntry._ID
                            + " FROM " + CompetitionScoreContract.CompetitionScoreEntry.TABLE_NAME
                            + " WHERE " + CompetitionScoreContract.CompetitionScoreEntry.COLUMN_NAME_COMPETITION_ID +" = " + id,
                    null);

            if (cursor != null && cursor.getCount()>0) {
                hasScore = true;
            }
        } catch (Exception exc) {
            //TO DO: Call Logger.ShowError;
        } finally {
            if (db != null)
                db.close();
        }
        return hasScore;
    }

    public CompetitionFromDb getCompetition(int id) {
        CompetitionFromDb competitionFromDb = null;
        SQLiteDatabase db = null;
        try {
            db = dbHelper.getReadableDatabase();

            String[] projection = {
                    CompetitionContract.CompetitionEntry._ID,
                    CompetitionContract.CompetitionEntry.COLUMN_NAME_TIME_FROM,
                    CompetitionContract.CompetitionEntry.COLUMN_NAME_TIME_TO,
                    CompetitionContract.CompetitionEntry.COLUMN_NAME_CATEGORY_ID,
                    CompetitionContract.CompetitionEntry.COLUMN_NAME_LOCATION,
                    CompetitionContract.CompetitionEntry.COLUMN_NAME_IS_ASSUMPTION
            };

            Cursor cursor = db.query(
                    CompetitionContract.CompetitionEntry.TABLE_NAME,
                    projection,
                    CompetitionContract.CompetitionEntry._ID + "=?",
                    new String[]{String.valueOf(id)},
                    null,
                    null,
                    null
            );

            if (cursor != null) {
                cursor.moveToFirst();
                competitionFromDb = new CompetitionFromDb(
                        cursor.getInt(CompetitionContract.getColumnPos(CompetitionContract.CompetitionEntry._ID)),
                        cursor.getString(CompetitionContract.getColumnPos(CompetitionContract.CompetitionEntry.COLUMN_NAME_TIME_FROM)),
                        cursor.getString(CompetitionContract.getColumnPos(CompetitionContract.CompetitionEntry.COLUMN_NAME_TIME_TO)),
                        cursor.getInt(CompetitionContract.getColumnPos(CompetitionContract.CompetitionEntry.COLUMN_NAME_CATEGORY_ID)),
                        cursor.getString(CompetitionContract.getColumnPos(CompetitionContract.CompetitionEntry.COLUMN_NAME_LOCATION)),
                        cursor.getInt(CompetitionContract.getColumnPos(CompetitionContract.CompetitionEntry.COLUMN_NAME_IS_ASSUMPTION)) > 0
                );
                cursor.close();
            }
        } catch (Exception exc) {
            //TO DO: Call Logger.ShowError;
        } finally {
            if (db != null)
                db.close();
        }
        return competitionFromDb;
    }

    public boolean createNewCompetition(CompetitionFromDb competition) {
        SQLiteDatabase db = null;
        try {
            db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(CompetitionContract.CompetitionEntry.COLUMN_NAME_TIME_FROM,competition.getTimeFrom());
            String endingTime = competition.getTimeTo();
            if(endingTime != null) {
                values.put(CompetitionContract.CompetitionEntry.COLUMN_NAME_TIME_TO, endingTime);
            }
            values.put(CompetitionContract.CompetitionEntry.COLUMN_NAME_CATEGORY_ID, competition.getCategoryId());
            String location = competition.getLocation();
            if (location != null) {
                values.put(CompetitionContract.CompetitionEntry.COLUMN_NAME_LOCATION, location);
            }
            values.put(CompetitionContract.CompetitionEntry.COLUMN_NAME_IS_ASSUMPTION, competition.isAssumption());

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

    public void updateCompetition(CompetitionFromDb competition) {
        SQLiteDatabase db = null;
        try {
            db = dbHelper.getReadableDatabase();
            ContentValues values = new ContentValues();
            values.put(CompetitionContract.CompetitionEntry.COLUMN_NAME_TIME_FROM, competition.getTimeFrom());
            values.put(CompetitionContract.CompetitionEntry.COLUMN_NAME_TIME_TO, competition.getTimeTo());
            values.put(CompetitionContract.CompetitionEntry.COLUMN_NAME_CATEGORY_ID, competition.getCategoryId());
            values.put(CompetitionContract.CompetitionEntry.COLUMN_NAME_LOCATION, competition.getLocation());
            values.put(CompetitionContract.CompetitionEntry.COLUMN_NAME_IS_ASSUMPTION, competition.isAssumption());
            db.update(CompetitionContract.CompetitionEntry.TABLE_NAME,
                    values,
                    CompetitionContract.CompetitionEntry._ID + "=?",
                    new String[]{String.valueOf(competition.getId())}
            );
        } catch (Exception exc) {
            //TO DO: Call Logger.ShowError;
        } finally {
            if (db != null)
                db.close();
        }
    }

    public void deleteCompetition(int competitionId) {
        SQLiteDatabase db = null;
        try {
            db = dbHelper.getWritableDatabase();
            db.delete(CompetitionContract.CompetitionEntry.TABLE_NAME,
                    CompetitionContract.CompetitionEntry._ID + "=?", new String[]{String.valueOf(competitionId)});
        } catch (Exception exc) {
            //TO DO: Call Logger.ShowError;
        } finally {
            if (db != null)
                db.close();
        }
    }

    public void deleteCompetition(CompetitionFromDb competition) {
        deleteCompetition(competition.getId());
    }
}

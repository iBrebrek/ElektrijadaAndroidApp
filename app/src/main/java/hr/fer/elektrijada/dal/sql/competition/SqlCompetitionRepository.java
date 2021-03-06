package hr.fer.elektrijada.dal.sql.competition;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import hr.fer.elektrijada.dal.sql.DbHelper;
import hr.fer.elektrijada.dal.sql.category.CategoryFromDb;
import hr.fer.elektrijada.dal.sql.category.SqlCategoryRepository;
import hr.fer.elektrijada.dal.sql.competitionscore.CompetitionScoreContract;
import hr.fer.elektrijada.dal.sql.competitor.CompetitorContract;
import hr.fer.elektrijada.dal.sql.competitor.CompetitorFromDb;
import hr.fer.elektrijada.util.Logger;

/**
 * Created by Ivica Brebrek
 */
public class SqlCompetitionRepository {
    private SQLiteOpenHelper dbHelper;
    private final Context context;

    public SqlCompetitionRepository(Context context) {
        dbHelper = new DbHelper(context);
        this.context = context;
    }

    public void close() {
        if (dbHelper != null) {
            dbHelper.close();
        }
    }

    public void fixId(CompetitionFromDb competition) {
        if(competition == null) return;
        SQLiteDatabase db = null;
        try {
            db = dbHelper.getReadableDatabase();

            Cursor cursor = db.rawQuery("SELECT "+ CompetitionContract.CompetitionEntry._ID+" FROM " + CompetitionContract.CompetitionEntry.TABLE_NAME
                    + " WHERE " + CompetitionContract.CompetitionEntry.COLUMN_NAME_CATEGORY_ID +"='"+competition.getCategory().getId()+"'", null);

            if (cursor != null) {
                cursor.moveToFirst();
                competition.setId(cursor.getInt(0));
                cursor.close();
            }

        } catch (Exception exc) {
            //TO DO: Call Logger.ShowError;
        } finally {
            if (db != null)
                db.close();
        }
    }

    public ArrayList<CompetitionFromDb> getAllCompetitions(){
        ArrayList<CompetitionFromDb> list = new ArrayList<>();
        SQLiteDatabase db = null;
        try {
            db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM " + CompetitionContract.CompetitionEntry.TABLE_NAME, null);
            if (cursor.moveToFirst()) {
                do {
                    list.add(new CompetitionFromDb(
                            cursor.getInt(CompetitionContract.getColumnPos(CompetitionContract.CompetitionEntry._ID)),
                            cursor.getString(CompetitionContract.getColumnPos(CompetitionContract.CompetitionEntry.COLUMN_NAME_TIME_FROM)),
                            cursor.getString(CompetitionContract.getColumnPos(CompetitionContract.CompetitionEntry.COLUMN_NAME_TIME_TO)),
                            getCategory(cursor.getInt(CompetitionContract.getColumnPos(CompetitionContract.CompetitionEntry.COLUMN_NAME_CATEGORY_ID))),
                            cursor.getString(CompetitionContract.getColumnPos(CompetitionContract.CompetitionEntry.COLUMN_NAME_LOCATION)), false
                    ));
                } while (cursor.moveToNext());
            }
            cursor.close();
        } catch (Exception exc) {
            Logger.LogException(exc);
        } finally {
            if (db != null)
                db.close();
        }
        return list;
    }

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
            if(cursor != null) {
                cursor.close();
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
                        getCategory(cursor.getInt(CompetitionContract.getColumnPos(CompetitionContract.CompetitionEntry.COLUMN_NAME_CATEGORY_ID))),
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
            values.put(CompetitionContract.CompetitionEntry.COLUMN_NAME_CATEGORY_ID, competition.getCategory().getId());
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
            values.put(CompetitionContract.CompetitionEntry.COLUMN_NAME_CATEGORY_ID, competition.getCategory().getId());
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

    private CategoryFromDb getCategory(int id) {
        SqlCategoryRepository categoryRepository = new SqlCategoryRepository(context);
        CategoryFromDb category = categoryRepository.getCategory(id);
        categoryRepository.close();
        return category;
    }
}

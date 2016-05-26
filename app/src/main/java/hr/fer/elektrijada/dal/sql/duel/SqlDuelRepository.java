package hr.fer.elektrijada.dal.sql.duel;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import hr.fer.elektrijada.dal.sql.DbHelper;
import hr.fer.elektrijada.dal.sql.category.CategoryFromDb;
import hr.fer.elektrijada.dal.sql.category.SqlCategoryRepository;
import hr.fer.elektrijada.dal.sql.competitor.CompetitorFromDb;
import hr.fer.elektrijada.dal.sql.competitor.SqlCompetitorRepository;
import hr.fer.elektrijada.dal.sql.stage.SqlStageRepository;
import hr.fer.elektrijada.dal.sql.stage.StageFromDb;
import hr.fer.elektrijada.dal.sql.user.UserContract;
import hr.fer.elektrijada.dal.sql.user.UserFromDb;
import hr.fer.elektrijada.util.Logger;

/**
 * Created by Ivica Brebrek
 */
public class SqlDuelRepository {
    private SQLiteOpenHelper dbHelper;
    private final Context context;

    public SqlDuelRepository(Context context) {
        dbHelper = new DbHelper(context);
        this.context = context;
    }

    public void close() {
        if (dbHelper != null) {
            dbHelper.close();
        }
    }

    public void fixId(DuelFromDb duel) {
        SQLiteDatabase db = null;
        try {
            db = dbHelper.getReadableDatabase();

            Cursor cursor = db.rawQuery("SELECT "+ DuelContract.DuelEntry._ID+" FROM " + DuelContract.DuelEntry.TABLE_NAME
                    + " WHERE " + DuelContract.DuelEntry.COLUMN_NAME_CATEGORY_ID +"='"+duel.getCategory().getId()+"'"
                    + " AND " + DuelContract.DuelEntry.COLUMN_NAME_COMPETITOR_1_ID +"='"+duel.getFirstCompetitor().getId()+"'"
                    + " AND " + DuelContract.DuelEntry.COLUMN_NAME_COMPETITOR_2_ID +"='"+duel.getSecondCompetitor().getId()+"'",
                    null);

            if (cursor != null) {
                cursor.moveToFirst();
                duel.setId(cursor.getInt(0));
                cursor.close();
            }

        } catch (Exception exc) {
            //TO DO: Call Logger.ShowError;
        } finally {
            if (db != null)
                db.close();
        }
    }

    public List<DuelFromDb> getAllDuels(){
        List<DuelFromDb> list = new ArrayList<>();
        SQLiteDatabase db = null;
        SqlCategoryRepository categories = new SqlCategoryRepository(context);
        SqlCompetitorRepository competitors = new SqlCompetitorRepository(context);
        SqlStageRepository stages = new SqlStageRepository(context);
        try {
            db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM " + DuelContract.DuelEntry.TABLE_NAME, null);
            if (cursor.moveToFirst()) {
                do {
                    list.add(new DuelFromDb(
                            cursor.getInt(DuelContract.getColumnPos(DuelContract.DuelEntry._ID)),
                            cursor.getString(DuelContract.getColumnPos(DuelContract.DuelEntry.COLUMN_NAME_TIME_FROM)),
                            cursor.getString(DuelContract.getColumnPos(DuelContract.DuelEntry.COLUMN_NAME_TIME_TO)),
                            categories.getCategory(cursor.getInt(DuelContract.getColumnPos(DuelContract.DuelEntry.COLUMN_NAME_CATEGORY_ID))),
                            competitors.getCompetitor(cursor.getInt(DuelContract.getColumnPos(DuelContract.DuelEntry.COLUMN_NAME_COMPETITOR_1_ID))),
                            competitors.getCompetitor(cursor.getInt(DuelContract.getColumnPos(DuelContract.DuelEntry.COLUMN_NAME_COMPETITOR_2_ID))),
                            stages.getStage(cursor.getInt(DuelContract.getColumnPos(DuelContract.DuelEntry.COLUMN_NAME_STAGE_ID))),
                            cursor.getString(DuelContract.getColumnPos(DuelContract.DuelEntry.COLUMN_NAME_LOCATION)),
                            false, null //nema assumption i sekcije
                    ));
                } while (cursor.moveToNext());
            }
            cursor.close();
        } catch (Exception exc) {
            Logger.LogException(exc);
        } finally {
            stages.close();
            competitors.close();
            categories.close();
            if (db != null)
                db.close();
        }
        return list;
    }

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
                        getCategory(cursor.getInt(DuelContract.getColumnPos(DuelContract.DuelEntry.COLUMN_NAME_CATEGORY_ID))),
                        getCompetitor(cursor.getInt(DuelContract.getColumnPos(DuelContract.DuelEntry.COLUMN_NAME_COMPETITOR_1_ID))),
                        getCompetitor(cursor.getInt(DuelContract.getColumnPos(DuelContract.DuelEntry.COLUMN_NAME_COMPETITOR_2_ID))),
                        getStage(cursor.getInt(DuelContract.getColumnPos(DuelContract.DuelEntry.COLUMN_NAME_STAGE_ID))),
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
            values.put(DuelContract.DuelEntry.COLUMN_NAME_COMPETITOR_1_ID, duel.getFirstCompetitor().getId());
            values.put(DuelContract.DuelEntry.COLUMN_NAME_COMPETITOR_2_ID, duel.getSecondCompetitor().getId());
            values.put(DuelContract.DuelEntry.COLUMN_NAME_TIME_FROM, duel.getTimeFrom());
            String endingTime = duel.getTimeTo();
            if(endingTime != null) {
                values.put(DuelContract.DuelEntry.COLUMN_NAME_TIME_TO, endingTime);
            }
            values.put(DuelContract.DuelEntry.COLUMN_NAME_CATEGORY_ID, duel.getCategory().getId());
            String location = duel.getLocation();
            if (location != null) {
                values.put(DuelContract.DuelEntry.COLUMN_NAME_LOCATION, location);
            }
            values.put(DuelContract.DuelEntry.COLUMN_NAME_STAGE_ID, duel.getStage().getId());
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
            values.put(DuelContract.DuelEntry.COLUMN_NAME_CATEGORY_ID, duel.getCategory().getId());
            values.put(DuelContract.DuelEntry.COLUMN_NAME_COMPETITOR_1_ID, duel.getFirstCompetitor().getId());
            values.put(DuelContract.DuelEntry.COLUMN_NAME_COMPETITOR_2_ID, duel.getSecondCompetitor().getId());
            values.put(DuelContract.DuelEntry.COLUMN_NAME_STAGE_ID, duel.getStage().getId());
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

    private CompetitorFromDb getCompetitor(int id) {
        SqlCompetitorRepository repo = new SqlCompetitorRepository(context);
        CompetitorFromDb competitor = repo.getCompetitor(id);
        repo.close();
        return competitor;
    }

    private StageFromDb getStage(int id) {
        SqlStageRepository repo = new SqlStageRepository(context);
        StageFromDb stage = repo.getStage(id);
        repo.close();
        return stage;
    }

    private CategoryFromDb getCategory(int id) {
        SqlCategoryRepository repo = new SqlCategoryRepository(context);
        CategoryFromDb category = repo.getCategory(id);
        repo.close();
        return category;
    }
}

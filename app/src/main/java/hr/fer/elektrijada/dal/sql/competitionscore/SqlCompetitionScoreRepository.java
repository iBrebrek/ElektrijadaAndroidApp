package hr.fer.elektrijada.dal.sql.competitionscore;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import hr.fer.elektrijada.dal.sql.DbHelper;
import hr.fer.elektrijada.dal.sql.competition.CompetitionFromDb;
import hr.fer.elektrijada.dal.sql.competition.SqlCompetitionRepository;
import hr.fer.elektrijada.dal.sql.competitor.CompetitorFromDb;
import hr.fer.elektrijada.dal.sql.competitor.SqlCompetitorRepository;
import hr.fer.elektrijada.dal.sql.user.SqlUserRepository;
import hr.fer.elektrijada.extras.MyInfo;

/**
 * Created by Ivica Brebrek
 */
public class SqlCompetitionScoreRepository {
    private SQLiteOpenHelper dbHelper;
    private final Context context;

    public SqlCompetitionScoreRepository(Context context) {
        dbHelper = new DbHelper(context);
        this.context = context;
    }

    public void close() {
        if (dbHelper != null) {
            dbHelper.close();
        }
    }

    public Double getScore(int competitorId, int competitionId) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);

        String showResults = sp.getString("showResults", "");

        Double score = null;

        if(showResults.equals("0")) {
            score = getMyScore(competitorId, competitionId);
        }

        if(score == null) {
            score = getMostFrequentScore(competitorId, competitionId);
        }

        return score;
    }

    public Double getMostFrequentScore(int competitorId, int competitionId) {
        Double score = null;
        SQLiteDatabase db = null;
        try {
            db = dbHelper.getReadableDatabase();

            Cursor cursor = db.rawQuery(
                    "SELECT " + CompetitionScoreContract.CompetitionScoreEntry.COLUMN_NAME_RESULT + ", " +
                            " COUNT(" + CompetitionScoreContract.CompetitionScoreEntry._ID + ") AS count" +
                            " FROM " +  CompetitionScoreContract.CompetitionScoreEntry.TABLE_NAME +
                            " WHERE " + CompetitionScoreContract.CompetitionScoreEntry.COLUMN_NAME_COMPETITOR_ID + " = " + competitorId +
                            " AND " + CompetitionScoreContract.CompetitionScoreEntry.COLUMN_NAME_COMPETITION_ID + " = " + competitionId +
                            " GROUP BY " + CompetitionScoreContract.CompetitionScoreEntry.COLUMN_NAME_RESULT +
                            " ORDER BY count DESC LIMIT 1",
                    null);

            if (cursor.moveToFirst()) {
                score = cursor.getDouble(0);
            }
            cursor.close();
        } catch (Exception exc) {
            //TO DO: Call Logger.ShowError;
        } finally {
            if (db != null)
                db.close();
        }
        return score;
    }

    public Double getMyScore(int competitorId, int competitionId) {
        Double score = null;
        SQLiteDatabase db = null;
        try {
            db = dbHelper.getReadableDatabase();

            int myUsernameId = MyInfo.getMyUsername(context).getId();

            Cursor cursor = db.rawQuery(
                    "SELECT " + CompetitionScoreContract.CompetitionScoreEntry.COLUMN_NAME_RESULT
                    + " FROM " + CompetitionScoreContract.CompetitionScoreEntry.TABLE_NAME
                    + " WHERE " +CompetitionScoreContract.CompetitionScoreEntry.COLUMN_NAME_COMPETITION_ID + " = " + competitionId
                    + " AND " + CompetitionScoreContract.CompetitionScoreEntry.COLUMN_NAME_COMPETITOR_ID + " = " + competitorId
                    + " AND " + CompetitionScoreContract.CompetitionScoreEntry.COLUMN_NAME_USER_ID + " = " + myUsernameId,
                    null);

            if (cursor.moveToFirst()) {
                score = cursor.getDouble(0);
            }
            cursor.close();
        } catch (Exception exc) {
            //TO DO: Call Logger.ShowError;
        } finally {
            if (db != null)
                db.close();
        }
        return score;
    }

    //all scores in a competition, ONLY TEAMS (isPerson = false)
    public List<CompetitionScoreFromDb> getTeamsScores(int competitionId) {
        List<CompetitionScoreFromDb> scores = new ArrayList<>();
        SQLiteDatabase db = null;
        try {
            db = dbHelper.getReadableDatabase();

            Cursor cursor = db.rawQuery("SELECT * FROM " + CompetitionScoreContract.CompetitionScoreEntry.TABLE_NAME
                                        + " WHERE " + competitionId + " = " + CompetitionScoreContract.CompetitionScoreEntry.COLUMN_NAME_COMPETITION_ID
                                        + " GROUP BY " + CompetitionScoreContract.CompetitionScoreEntry.COLUMN_NAME_COMPETITOR_ID,
                                        null);

            SqlCompetitionRepository compRepo = new SqlCompetitionRepository(context);
            CompetitionFromDb competition = compRepo.getCompetition(competitionId);
            compRepo.close();

            SqlUserRepository userRepo = new SqlUserRepository(context);
            SqlCompetitorRepository contestantRepo = new SqlCompetitorRepository(context);

            if (cursor != null) {
                while(cursor.moveToNext()) {
                    CompetitorFromDb competitor = contestantRepo.getCompetitor(cursor.getInt(CompetitionScoreContract.getColumnPos(CompetitionScoreContract.CompetitionScoreEntry.COLUMN_NAME_COMPETITOR_ID)));
                    if(competitor.isPerson()) continue;
                    scores.add(new CompetitionScoreFromDb(
                            cursor.getInt(CompetitionScoreContract.getColumnPos(CompetitionScoreContract.CompetitionScoreEntry._ID)),
                            -1, //will be set later
                            cursor.getString(CompetitionScoreContract.getColumnPos(CompetitionScoreContract.CompetitionScoreEntry.COLUMN_NAME_NOTE)),
                            competition,
                            userRepo.getUser(cursor.getInt(CompetitionScoreContract.getColumnPos(CompetitionScoreContract.CompetitionScoreEntry.COLUMN_NAME_USER_ID))),
                            competitor,
                            false
                    ));
                }
                for(CompetitionScoreFromDb score : scores) {
                    score.setResult(getScore(score.getCompetitor().getId(), score.getCompetition().getId()));
                }
                cursor.close();
            }

            userRepo.close();
            contestantRepo.close();

        } catch (Exception exc) {
            //TO DO: Call Logger.ShowError;
        } finally {
            if (db != null)
                db.close();
        }
        return scores;
    }

    //all scores in a competition, ONLY PEOPLE (isPerson = true)
    public List<CompetitionScoreFromDb> getIndividualScores(int competitionId) {
        List<CompetitionScoreFromDb> scores = new ArrayList<>();
        SQLiteDatabase db = null;
        try {
            db = dbHelper.getReadableDatabase();

            Cursor cursor = db.rawQuery("SELECT * FROM " + CompetitionScoreContract.CompetitionScoreEntry.TABLE_NAME
                            + " WHERE " + competitionId + " = " + CompetitionScoreContract.CompetitionScoreEntry.COLUMN_NAME_COMPETITION_ID
                            + " GROUP BY " + CompetitionScoreContract.CompetitionScoreEntry.COLUMN_NAME_COMPETITOR_ID,
                    null);

            SqlCompetitionRepository compRepo = new SqlCompetitionRepository(context);
            CompetitionFromDb competition = compRepo.getCompetition(competitionId);
            compRepo.close();

            SqlUserRepository userRepo = new SqlUserRepository(context);
            SqlCompetitorRepository contestantRepo = new SqlCompetitorRepository(context);

            if (cursor != null) {
                while(cursor.moveToNext()) {
                    CompetitorFromDb competitor = contestantRepo.getCompetitor(cursor.getInt(CompetitionScoreContract.getColumnPos(CompetitionScoreContract.CompetitionScoreEntry.COLUMN_NAME_COMPETITOR_ID)));
                    if(!competitor.isPerson()) continue;
                    scores.add(new CompetitionScoreFromDb(
                            cursor.getInt(CompetitionScoreContract.getColumnPos(CompetitionScoreContract.CompetitionScoreEntry._ID)),
                            -1, //will be set later
                            cursor.getString(CompetitionScoreContract.getColumnPos(CompetitionScoreContract.CompetitionScoreEntry.COLUMN_NAME_NOTE)),
                            competition,
                            userRepo.getUser(cursor.getInt(CompetitionScoreContract.getColumnPos(CompetitionScoreContract.CompetitionScoreEntry.COLUMN_NAME_USER_ID))),
                            competitor,
                            true
                    ));
                }
                for(CompetitionScoreFromDb score : scores) {
                    score.setResult(getScore(score.getCompetitor().getId(), score.getCompetition().getId()));
                }
                cursor.close();
            }

            userRepo.close();
            contestantRepo.close();

        } catch (Exception exc) {
            //TO DO: Call Logger.ShowError;
        } finally {
            if (db != null)
                db.close();
        }
        return scores;
    }

    //all individial scores in competition in team
    public List<CompetitionScoreFromDb> getIndividualsPerTeam(int competitionId, int teamId) {
        List<CompetitionScoreFromDb> students = getIndividualScores(competitionId);

        Iterator<CompetitionScoreFromDb> iterator = students.iterator();
        while(iterator.hasNext()) {
            CompetitionScoreFromDb one = iterator.next();
            if(one.getCompetitor().getGroupCompetitor().getId() != teamId) {
                iterator.remove();
            }
        }

        return students;
    }

    //brise SVE rezultate povezane s tim natjecanjem/natjecateljem
    public void deleteScore(CompetitionScoreFromDb score) {
        SQLiteDatabase db = null;
        try {
            db = dbHelper.getWritableDatabase();
            db.delete(CompetitionScoreContract.CompetitionScoreEntry.TABLE_NAME,
                    CompetitionScoreContract.CompetitionScoreEntry.COLUMN_NAME_COMPETITION_ID + "=? AND "
                    + CompetitionScoreContract.CompetitionScoreEntry.COLUMN_NAME_COMPETITOR_ID + "=?",
                    new String[]{String.valueOf(score.getCompetition().getId()), String.valueOf(score.getCompetitor().getId())});

            if(!score.getCompetitor().isPerson()) { //if whole team is being deleted
                List<CompetitionScoreFromDb> individuals = getIndividualsPerTeam(score.getCompetition().getId(), score.getCompetitor().getId());
                for(CompetitionScoreFromDb one : individuals) {
                    deleteScore(one);
                }
            }
        } catch (Exception exc) {
            //TO DO: Call Logger.ShowError;
        } finally {
            if (db != null)
                db.close();
        }
    }

    public void updateScore(CompetitionScoreFromDb score) {
        SQLiteDatabase db = null;
        try {
            db = dbHelper.getReadableDatabase();
            ContentValues values = new ContentValues();
            values.put(CompetitionScoreContract.CompetitionScoreEntry.COLUMN_NAME_RESULT, score.getResult());
            values.put(CompetitionScoreContract.CompetitionScoreEntry.COLUMN_NAME_NOTE, score.getNote());
            values.put(CompetitionScoreContract.CompetitionScoreEntry.COLUMN_NAME_COMPETITION_ID, score.getCompetition().getId());
            values.put(CompetitionScoreContract.CompetitionScoreEntry.COLUMN_NAME_USER_ID, score.getUser().getId());
            values.put(CompetitionScoreContract.CompetitionScoreEntry.COLUMN_NAME_COMPETITOR_ID, score.getCompetitor().getId());
            values.put(CompetitionScoreContract.CompetitionScoreEntry.COLUMN_NAME_IS_OFFICIAL, score.isOfficial());

            db.update(CompetitionScoreContract.CompetitionScoreEntry.TABLE_NAME,
                    values,
                    CompetitionScoreContract.CompetitionScoreEntry._ID + "=?",
                    new String[]{String.valueOf(score.getId())});
        } catch (Exception exc) {
            //TO DO: Call Logger.ShowError;
        } finally {
            if (db != null)
                db.close();
        }
    }

    public int addScore(CompetitionScoreFromDb score) {
        SQLiteDatabase db = null;
        try {
            db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(CompetitionScoreContract.CompetitionScoreEntry.COLUMN_NAME_RESULT, score.getResult());
            values.put(CompetitionScoreContract.CompetitionScoreEntry.COLUMN_NAME_NOTE, score.getNote());
            values.put(CompetitionScoreContract.CompetitionScoreEntry.COLUMN_NAME_COMPETITION_ID, score.getCompetition().getId());
            values.put(CompetitionScoreContract.CompetitionScoreEntry.COLUMN_NAME_USER_ID, score.getUser().getId());
            values.put(CompetitionScoreContract.CompetitionScoreEntry.COLUMN_NAME_COMPETITOR_ID, score.getCompetitor().getId());
            values.put(CompetitionScoreContract.CompetitionScoreEntry.COLUMN_NAME_IS_OFFICIAL, score.isOfficial());

            return (int) db.insert(CompetitionScoreContract.CompetitionScoreEntry.TABLE_NAME, null, values);
        } catch (Exception exc) {
            //TO DO: Call Logger.ShowError;
            return -1;
        } finally {
            if (db != null)
                db.close();
        }
    }
}

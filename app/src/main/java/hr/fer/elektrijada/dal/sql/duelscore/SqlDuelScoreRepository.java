package hr.fer.elektrijada.dal.sql.duelscore;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import hr.fer.elektrijada.dal.sql.DbHelper;
import hr.fer.elektrijada.dal.sql.duel.DuelFromDb;
import hr.fer.elektrijada.dal.sql.duel.SqlDuelRepository;
import hr.fer.elektrijada.dal.sql.user.SqlUserRepository;
import hr.fer.elektrijada.dal.sql.user.UserContract;
import hr.fer.elektrijada.dal.sql.user.UserFromDb;
import hr.fer.elektrijada.extras.MyInfo;
import hr.fer.elektrijada.model.score.DuelScore;
import hr.fer.elektrijada.model.score.DuelScoreCounter;

/**
 * Created by Ivica Brebrek
 */
public class SqlDuelScoreRepository {
    private SQLiteOpenHelper dbHelper;
    private final Context context;

    public SqlDuelScoreRepository(Context context) {
        dbHelper = new DbHelper(context);
        this.context = context;
    }

    public void close() {
        if (dbHelper != null) {
            dbHelper.close();
        }
    }

    //dodati ostale metode ako zatrebaju...

    /**
     * ovo ce biti metoda koja ce odluciti koju metodu treba pozvati,
     * bit ce moguce mostFrequent(koj je sad),
     * sluzben rezultat, i moj rezultat
     *
     * ili mozda nac bolje mjesto koje za to odlucuje, posto i competitionScore mora imat te 3 mogucnosti
     */
    public DuelScore getScore(int duelId) {
        return getMostFrequentScore(duelId);
    }

    /**
     *
     * Vraca rezultat koji se najcesce pojavluje za taj duel
     */
    public DuelScore getMostFrequentScore(int duelId) {
        DuelScore score = null;
        SQLiteDatabase db = null;
        try {
            db = dbHelper.getReadableDatabase();

            Cursor cursor = db.rawQuery(
                    "SELECT " + DuelScoreContract.DuelScoreEntry.COLUMN_NAME_SCORE_1 + ", " +
                            DuelScoreContract.DuelScoreEntry.COLUMN_NAME_SCORE_2 + ", " +
                            " COUNT(" + DuelScoreContract.DuelScoreEntry._ID + ") AS count" +
                            " FROM " + DuelScoreContract.DuelScoreEntry.TABLE_NAME +
                            " WHERE " + DuelScoreContract.DuelScoreEntry.COLUMN_NAME_DUEL_ID + " = " + duelId +
                            " GROUP BY " + DuelScoreContract.DuelScoreEntry.COLUMN_NAME_SCORE_1 + ", " +
                            DuelScoreContract.DuelScoreEntry.COLUMN_NAME_SCORE_2 +
                            " ORDER BY count DESC LIMIT 1",
                    null);

            if (cursor.moveToFirst()) {
                score = new DuelScore(
                        cursor.getDouble(0),
                        cursor.getDouble(1)
                );
            } else {
                score = new DuelScore();
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

    /**
     *
     * Vraca moj rezultat tog duela
     */
    public DuelScore getMyScore(int duelId) {
        DuelScore score = new DuelScore();
        SQLiteDatabase db = null;
        try {
            db = dbHelper.getReadableDatabase();

            int myUsernameId = MyInfo.getMyUsername(context).getId();

            Cursor cursor = db.rawQuery(
                    "SELECT " + DuelScoreContract.DuelScoreEntry.COLUMN_NAME_SCORE_1 + ", " +
                            DuelScoreContract.DuelScoreEntry.COLUMN_NAME_SCORE_2 +
                            " FROM " + DuelScoreContract.DuelScoreEntry.TABLE_NAME +
                            " WHERE " + DuelScoreContract.DuelScoreEntry.COLUMN_NAME_DUEL_ID + " = " + duelId +
                            " AND " + DuelScoreContract.DuelScoreEntry.COLUMN_NAME_USER_ID + " = " + myUsernameId,
                    null);

            if (cursor.moveToFirst()) {
                score = new DuelScore(
                        cursor.getDouble(0),
                        cursor.getDouble(1)
                );
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

    public DuelScoreFromDb getDuelScoreFromDb(int duelId, int userId) {
        DuelScoreFromDb score = null;
        SQLiteDatabase db = null;
        try {
            db = dbHelper.getReadableDatabase();

            String[] projection = {
                    DuelScoreContract.DuelScoreEntry._ID,
                    DuelScoreContract.DuelScoreEntry.COLUMN_NAME_SCORE_1,
                    DuelScoreContract.DuelScoreEntry.COLUMN_NAME_SCORE_2,
                    DuelScoreContract.DuelScoreEntry.COLUMN_NAME_DUEL_ID,
                    DuelScoreContract.DuelScoreEntry.COLUMN_NAME_USER_ID,
                    DuelScoreContract.DuelScoreEntry.COLUMN_NAME_IS_ASSUMPTION,
                    DuelScoreContract.DuelScoreEntry.COLUMN_NAME_NOTE,
                    DuelScoreContract.DuelScoreEntry.COLUMN_NAME_IS_OFFICIAL
            };

            Cursor cursor = db.query(
                    DuelScoreContract.DuelScoreEntry.TABLE_NAME,
                    projection,
                    DuelScoreContract.DuelScoreEntry.COLUMN_NAME_DUEL_ID + "=? AND "
                            + DuelScoreContract.DuelScoreEntry.COLUMN_NAME_USER_ID + "=?",
                    new String[]{String.valueOf(duelId), String.valueOf(userId)},
                    null,
                    null,
                    null
            );

            if (cursor != null) {
                cursor.moveToFirst();
                score = new DuelScoreFromDb(
                        cursor.getInt(DuelScoreContract.getColumnPos(DuelScoreContract.DuelScoreEntry._ID)),
                        cursor.getDouble(DuelScoreContract.getColumnPos(DuelScoreContract.DuelScoreEntry.COLUMN_NAME_SCORE_1)),
                        cursor.getDouble(DuelScoreContract.getColumnPos(DuelScoreContract.DuelScoreEntry.COLUMN_NAME_SCORE_2)),
                        getDuel(cursor.getInt(DuelScoreContract.getColumnPos(DuelScoreContract.DuelScoreEntry.COLUMN_NAME_DUEL_ID))),
                        getUser(cursor.getInt(DuelScoreContract.getColumnPos(DuelScoreContract.DuelScoreEntry.COLUMN_NAME_USER_ID))),
                        cursor.getString(DuelScoreContract.getColumnPos(DuelScoreContract.DuelScoreEntry.COLUMN_NAME_NOTE)),
                        cursor.getInt(DuelScoreContract.getColumnPos(DuelScoreContract.DuelScoreEntry.COLUMN_NAME_IS_OFFICIAL)) > 0
                );
                cursor.close();
            }
        } catch (Exception exc) {
            //TO DO: Call Logger.ShowError;
        } finally {
            if (db != null)
                db.close();
        }
        return score;
    }


    /**
     *
     * Vraca sluzbeni rezultat tog duela
     */
    public DuelScore getOfficialScore(int duelId) {
        DuelScore score = new DuelScore();
        SQLiteDatabase db = null;
        try {
            db = dbHelper.getReadableDatabase();

            Cursor cursor = db.rawQuery(
                    "SELECT " + DuelScoreContract.DuelScoreEntry.COLUMN_NAME_SCORE_1 + ", " +
                            DuelScoreContract.DuelScoreEntry.COLUMN_NAME_SCORE_2 +
                            " FROM " + DuelScoreContract.DuelScoreEntry.TABLE_NAME +
                            " WHERE " + DuelScoreContract.DuelScoreEntry.COLUMN_NAME_DUEL_ID + " = " + duelId +
                            " AND " + DuelScoreContract.DuelScoreEntry.COLUMN_NAME_IS_OFFICIAL + " > 0",
                    null);

            if (cursor.moveToFirst()) {
                score = new DuelScore(
                        cursor.getDouble(0),
                        cursor.getDouble(1)
                );
            } else {
                score = new DuelScore();
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

    /**
     * Vraca sve rezultate za taj duel, i to s brojem njihovog pojavljivanja
     */
    public ArrayList<DuelScoreCounter> getAllResults(int duelId) {
        ArrayList<DuelScoreCounter> list = new ArrayList<>();
        SQLiteDatabase db = null;
        try {
            db = dbHelper.getReadableDatabase();

            Cursor cursor = db.rawQuery(
                    "SELECT " + DuelScoreContract.DuelScoreEntry.COLUMN_NAME_SCORE_1 + ", " +
                            DuelScoreContract.DuelScoreEntry.COLUMN_NAME_SCORE_2 + ", " +
                            " COUNT(" + DuelScoreContract.DuelScoreEntry._ID + ") AS count" +
                            " FROM " + DuelScoreContract.DuelScoreEntry.TABLE_NAME +
                            " WHERE " + DuelScoreContract.DuelScoreEntry.COLUMN_NAME_DUEL_ID + " = " + duelId +
                            " GROUP BY " + DuelScoreContract.DuelScoreEntry.COLUMN_NAME_SCORE_1 + ", " +
                            DuelScoreContract.DuelScoreEntry.COLUMN_NAME_SCORE_2 +
                            " ORDER BY count DESC",
                    null);

            if (cursor.moveToFirst()) {
                do {
                    list.add(new DuelScoreCounter(
                            cursor.getDouble(0), //taj redoslijed (0,1,2) je vidljiv u sql upitu
                            cursor.getDouble(1),
                            cursor.getInt(2)
                    ));
                } while (cursor.moveToNext());
            }
            cursor.close();
        } catch (Exception exc) {
            //TO DO: Call Logger.ShowError;
        } finally {
            if (db != null)
                db.close();
        }
        return list;
    }

    public void addDuelScore(DuelScoreFromDb duelScore) {
        SQLiteDatabase db = null;
        try {
            db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(DuelScoreContract.DuelScoreEntry.COLUMN_NAME_SCORE_1, duelScore.getFirstScore());
            values.put(DuelScoreContract.DuelScoreEntry.COLUMN_NAME_SCORE_2, duelScore.getSecondScore());
            values.put(DuelScoreContract.DuelScoreEntry.COLUMN_NAME_DUEL_ID, duelScore.getDuel().getId());
            values.put(DuelScoreContract.DuelScoreEntry.COLUMN_NAME_USER_ID, duelScore.getUser().getId());
            values.put(DuelScoreContract.DuelScoreEntry.COLUMN_NAME_NOTE, duelScore.getNote());
            values.put(DuelScoreContract.DuelScoreEntry.COLUMN_NAME_IS_OFFICIAL, duelScore.isOfficial());

            db.insert(DuelScoreContract.DuelScoreEntry.TABLE_NAME, null, values);
        } catch (Exception exc) {
            //TO DO: Call Logger.ShowError;
        } finally {
            if (db != null)
                db.close();
        }
    }

    public void updateDuelScore(DuelScoreFromDb duelScore) {
        SQLiteDatabase db = null;
        try {
            db = dbHelper.getReadableDatabase();
            ContentValues values = new ContentValues();
            values.put(DuelScoreContract.DuelScoreEntry.COLUMN_NAME_SCORE_1, duelScore.getFirstScore());
            values.put(DuelScoreContract.DuelScoreEntry.COLUMN_NAME_SCORE_2, duelScore.getSecondScore());
            values.put(DuelScoreContract.DuelScoreEntry.COLUMN_NAME_DUEL_ID, duelScore.getDuel().getId());
            values.put(DuelScoreContract.DuelScoreEntry.COLUMN_NAME_USER_ID, duelScore.getUser().getId());
            values.put(DuelScoreContract.DuelScoreEntry.COLUMN_NAME_NOTE, duelScore.getNote());
            values.put(DuelScoreContract.DuelScoreEntry.COLUMN_NAME_IS_OFFICIAL, duelScore.isOfficial());

            db.update(DuelScoreContract.DuelScoreEntry.TABLE_NAME,
                    values,
                    DuelScoreContract.DuelScoreEntry._ID + "=?",
                    new String[]{String.valueOf(duelScore.getId())}
            );
        } catch (Exception exc) {
            //TO DO: Call Logger.ShowError;
        } finally {
            if (db != null)
                db.close();
        }
    }

    public void deleteDuelScore(int duelScoreId) {
        SQLiteDatabase db = null;
        try {
            db = dbHelper.getWritableDatabase();
            db.delete(DuelScoreContract.DuelScoreEntry.TABLE_NAME,
                    DuelScoreContract.DuelScoreEntry._ID + "=?",
                    new String[]{String.valueOf(duelScoreId)});
        } catch (Exception exc) {
            //TO DO: Call Logger.ShowError;
        } finally {
            if (db != null)
                db.close();
        }
    }

    public void deleteMyScore(int duelId) {
        int myId = MyInfo.getMyUsername(context).getId();
        SQLiteDatabase db = null;
        try {
            db = dbHelper.getWritableDatabase();
            db.delete(DuelScoreContract.DuelScoreEntry.TABLE_NAME,
                    DuelScoreContract.DuelScoreEntry.COLUMN_NAME_DUEL_ID + "=? AND "
                            + DuelScoreContract.DuelScoreEntry.COLUMN_NAME_USER_ID + "=?",
                    new String[]{String.valueOf(duelId), String.valueOf(myId)});
        } catch (Exception exc) {
            //TO DO: Call Logger.ShowError;
        } finally {
            if (db != null)
                db.close();
        }

    }

    //vraca listu svih korisnika koji su odabrali taj rezultat za taj duel
    public ArrayList<UserFromDb> getAllUserThatPutThisScore(DuelScore duelScore, int duelId) {
        ArrayList<UserFromDb> list = new ArrayList<>();
        SQLiteDatabase db = null;
        try {
            db = dbHelper.getReadableDatabase();

            Cursor cursor = db.rawQuery(
                    "SELECT " + UserContract.UserEntry.TABLE_NAME + "." + UserContract.UserEntry._ID + ", " +
                            UserContract.UserEntry.COLUMN_NAME_NAME + ", " +
                            UserContract.UserEntry.COLUMN_NAME_SURNAME +
                            " FROM " + DuelScoreContract.DuelScoreEntry.TABLE_NAME +
                            " INNER JOIN " + UserContract.UserEntry.TABLE_NAME +
                            " ON " + DuelScoreContract.DuelScoreEntry.COLUMN_NAME_USER_ID +
                            " = " + UserContract.UserEntry.TABLE_NAME + "." + UserContract.UserEntry._ID +
                            " WHERE " + DuelScoreContract.DuelScoreEntry.COLUMN_NAME_DUEL_ID + " = " + duelId +
                            " AND " + DuelScoreContract.DuelScoreEntry.COLUMN_NAME_SCORE_1 + " = " + duelScore.getFirstScore() +
                            " AND " + DuelScoreContract.DuelScoreEntry.COLUMN_NAME_SCORE_2 + " = " + duelScore.getSecondScore() +
                            " ORDER BY " + UserContract.UserEntry.COLUMN_NAME_SURNAME + " DESC",
                    null);

            if (cursor.moveToFirst()) {
                do {
                    list.add(new UserFromDb(
                            cursor.getInt(0),
                            cursor.getString(1),
                            cursor.getString(2)
                    ));
                } while (cursor.moveToNext());
            }
            cursor.close();
        } catch (Exception exc) {
            //TO DO: Call Logger.ShowError;
        } finally {
            if (db != null)
                db.close();
        }
        return list;
    }

    private DuelFromDb getDuel(int id) {
        SqlDuelRepository repo = new SqlDuelRepository(context);
        DuelFromDb duel = repo.getDuel(id);
        repo.close();
        return duel;
    }

    private UserFromDb getUser(int id) {
        SqlUserRepository repo = new SqlUserRepository(context);
        UserFromDb user = repo.getUser(id);
        repo.close();
        return user;
    }

    /**
     *
     * OVO NIJE DOBRO!!!
     * -vraca prvi rezultat koj ima taj duelId
     *
     */
//    public DuelScore getScoreFromDuel(int duelId) {
//        DuelScore score = null;
//        SQLiteDatabase db = null;
//        try {
//            db = dbHelper.getReadableDatabase();
//
//            Cursor cursor = db.rawQuery(
//                    "SELECT " + DuelScoreContract.DuelScoreEntry.COLUMN_NAME_SCORE_1 + ", "
//                            + DuelScoreContract.DuelScoreEntry.COLUMN_NAME_SCORE_2
//                            + " FROM " + DuelScoreContract.DuelScoreEntry.TABLE_NAME
//                            + " WHERE " + DuelScoreContract.DuelScoreEntry.COLUMN_NAME_DUEL_ID +" = " + duelId,
//                    null);
//
//            if (cursor != null) {
//                cursor.moveToFirst();
//                score = new DuelScore(
//                        cursor.getInt(0),
//                        cursor.getInt(1)
//                );
//                cursor.close();
//            }
//        } catch (Exception exc) {
//            //TO DO: Call Logger.ShowError;
//        } finally {
//            if (db != null)
//                db.close();
//        }
//        return score;
//    }
}

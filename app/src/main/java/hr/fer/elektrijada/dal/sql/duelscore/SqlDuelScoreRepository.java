package hr.fer.elektrijada.dal.sql.duelscore;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import hr.fer.elektrijada.dal.sql.DbHelper;
import hr.fer.elektrijada.model.score.DuelScore;

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
                        cursor.getInt(0),
                        cursor.getInt(1)
                );
            } else {
                score = new DuelScore();
            }
            cursor.close();
        } catch (Exception exc) {
            int a, b;
            b=5+3;
            a=b;
            //TO DO: Call Logger.ShowError;
        } finally {
            if (db != null)
                db.close();
        }
        return score;
    }



    /**
     *
     * OVO NIJE DOBRO
     * -vraca prvi rezultat koj ima taj duelId
     *
     */
    public DuelScore getScoreFromDuel(int duelId) {
        DuelScore score = null;
        SQLiteDatabase db = null;
        try {
            db = dbHelper.getReadableDatabase();

            Cursor cursor = db.rawQuery(
                    "SELECT " + DuelScoreContract.DuelScoreEntry.COLUMN_NAME_SCORE_1 + ", "
                            + DuelScoreContract.DuelScoreEntry.COLUMN_NAME_SCORE_2
                            + " FROM " + DuelScoreContract.DuelScoreEntry.TABLE_NAME
                            + " WHERE " + DuelScoreContract.DuelScoreEntry.COLUMN_NAME_DUEL_ID +" = " + duelId,
                    null);

            if (cursor != null) {
                cursor.moveToFirst();
                score = new DuelScore(
                        cursor.getInt(0),
                        cursor.getInt(1)
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
}

package hr.fer.elektrijada.dal.sql.duelscore;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import hr.fer.elektrijada.dal.sql.DbHelper;
import hr.fer.elektrijada.model.events.DuelEvent;

/**
 * Created by Ivica Brebrek
 */
public class SqlDuelScoreRepository {
    private SQLiteOpenHelper dbHelper;

    public SqlDuelScoreRepository(Context context) {
        dbHelper = new DbHelper(context);
    }

    public void close() {
        if (dbHelper != null) {
            dbHelper.close();
        }
    }

    //dodati ostale metode ako zatrebaju...

    public DuelEvent.Score getScoreFromDuel(int duelId) {
        DuelEvent.Score score = null;
        SQLiteDatabase db = null;
        try {
            db = dbHelper.getReadableDatabase();

            Cursor cursor = db.rawQuery(
                    "SELECT " + DuelScoreContract.DuelScoreEntry.COLUMN_NAME_SCORE_1 + ", "
                            + DuelScoreContract.DuelScoreEntry.COLUMN_NAME_SCORE_2
                            + " FROM " + DuelScoreContract.DuelScoreEntry.TABLE_NAME
                            + " WHERE " + DuelScoreContract.DuelScoreEntry._ID +" = " + duelId,
                    null);

            if (cursor != null) {
                cursor.moveToFirst();
                score = new DuelEvent.Score(
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

package hr.fer.elektrijada.dal.sql.competitor;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.HashMap;

import hr.fer.elektrijada.dal.sql.DbHelper;
import hr.fer.elektrijada.util.Logger;

/**
 * Created by Ivica Brebrek
 */
public class SqlCompetitorRepository {
    private SQLiteOpenHelper dbHelper;

    public SqlCompetitorRepository(Context context) {
        dbHelper = new DbHelper(context);
    }

    public void close() {
        if (dbHelper != null) {
            dbHelper.close();
        }
    }

    //dodati ostale metode ako zatrebaju...

    public String getCompetitorName(int id) {
        String name = null;
        SQLiteDatabase db = null;
        try {
            db = dbHelper.getReadableDatabase();

            Cursor cursor = db.rawQuery(
                    "SELECT " + "CASE "
                                + "WHEN " + CompetitorContract.CompetitorEntry.COLUMN_NAME_SURNAME + " IS NULL "
                                + "THEN " + CompetitorContract.CompetitorEntry.COLUMN_NAME_NAME
                                + " ELSE " + CompetitorContract.CompetitorEntry.COLUMN_NAME_NAME + " ||  ' ' || "
                                + CompetitorContract.CompetitorEntry.COLUMN_NAME_SURNAME
                                + " END "
                            + "FROM " + CompetitorContract.CompetitorEntry.TABLE_NAME
                            + " WHERE " + CompetitorContract.CompetitorEntry._ID +" = " + id,
                    null);

            if (cursor != null) {
                cursor.moveToFirst();
                name = cursor.getString(0);
                cursor.close();
            }
        } catch (Exception exc) {
            //TO DO: Call Logger.ShowError;
        } finally {
            if (db != null)
                db.close();
        }
        return name;
    }

    /**
     * na prvu bi izgledalo bolje da je id kljuc, ali ovakva struktura mi bolje pase za spinner
     *
     * String je Name (+ SureName ako ga ima), Integer je ID
     * @return  mapa u kojoj su kljucevi IMENA, a ID vrijednosti
     */
    public HashMap<String, Integer> getMapOfCompetitors() {
        HashMap<String, Integer> allCompetitors = new HashMap<>();
        SQLiteDatabase db = null;
        try {
            db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(
                    "SELECT " + CompetitorContract.CompetitorEntry._ID + ", "
                            + "CASE "
                                + "WHEN " + CompetitorContract.CompetitorEntry.COLUMN_NAME_SURNAME + " IS NULL "
                                + "THEN " + CompetitorContract.CompetitorEntry.COLUMN_NAME_NAME
                                + " ELSE " + CompetitorContract.CompetitorEntry.COLUMN_NAME_NAME + " ||  ' ' || "
                                + CompetitorContract.CompetitorEntry.COLUMN_NAME_SURNAME
                                + " END " +
                    "FROM " + CompetitorContract.CompetitorEntry.TABLE_NAME,
                    null);
            if (cursor.moveToFirst()) {
                do {
                    allCompetitors.put(cursor.getString(1), cursor.getInt(0));
                } while (cursor.moveToNext());
            }
            cursor.close();
        } catch (Exception exc) {
            Logger.LogException(exc);
        } finally {
            if (db != null)
                db.close();
        }
        return allCompetitors;
    }
}

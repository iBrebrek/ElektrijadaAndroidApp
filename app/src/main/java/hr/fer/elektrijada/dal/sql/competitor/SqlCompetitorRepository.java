package hr.fer.elektrijada.dal.sql.competitor;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.HashMap;

import hr.fer.elektrijada.dal.sql.DbHelper;
import hr.fer.elektrijada.dal.sql.competition.CompetitionFromDb;
import hr.fer.elektrijada.dal.sql.competition.SqlCompetitionRepository;
import hr.fer.elektrijada.dal.sql.faculty.FacultyFromDb;
import hr.fer.elektrijada.dal.sql.faculty.SqlFacultyRepository;
import hr.fer.elektrijada.util.Logger;

/**
 * Created by Ivica Brebrek
 */
public class SqlCompetitorRepository {
    private SQLiteOpenHelper dbHelper;
    private final Context context;

    public SqlCompetitorRepository(Context context) {
        dbHelper = new DbHelper(context);
        this.context = context;
    }

    public void close() {
        if (dbHelper != null) {
            dbHelper.close();
        }
    }

    //dodati ostale metode ako zatrebaju...

    public CompetitorFromDb getCompetitor(int id) {
        SQLiteDatabase db = null;
        CompetitorFromDb competitor = null;
        try {
            db = dbHelper.getReadableDatabase();

            String[] projection = {
                    CompetitorContract.CompetitorEntry._ID,
                    CompetitorContract.CompetitorEntry.COLUMN_NAME_NAME,
                    CompetitorContract.CompetitorEntry.COLUMN_NAME_SURNAME,
                    CompetitorContract.CompetitorEntry.COLUMN_NAME_IS_PERSON,
                    CompetitorContract.CompetitorEntry.COLUMN_NAME_GROUP_COMPETITOR_ID,
                    CompetitorContract.CompetitorEntry.COLUMN_NAME_FACULTY_ID,
                    CompetitorContract.CompetitorEntry.COLUMN_NAME_COMPETITION_ID,
                    CompetitorContract.CompetitorEntry.COLUMN_NAME_ORDINAL_NUM,
                    CompetitorContract.CompetitorEntry.COLUMN_NAME_IS_DISQUALIFIED
            };

            Cursor cursor = db.query(
                    CompetitorContract.CompetitorEntry.TABLE_NAME,
                    projection,
                    CompetitorContract.CompetitorEntry._ID + "=?",
                    new String[]{String.valueOf(id)},
                    null,
                    null,
                    null
            );

            if(cursor != null && cursor.moveToFirst()) {
                competitor = new CompetitorFromDb(
                        cursor.getInt(CompetitorContract.getColumnPos(CompetitorContract.CompetitorEntry._ID)),
                        cursor.getString(CompetitorContract.getColumnPos(CompetitorContract.CompetitorEntry.COLUMN_NAME_NAME)),
                        cursor.getString(CompetitorContract.getColumnPos(CompetitorContract.CompetitorEntry.COLUMN_NAME_SURNAME)),
                        cursor.getInt(CompetitorContract.getColumnPos(CompetitorContract.CompetitorEntry.COLUMN_NAME_IS_PERSON)) > 0,
                        getCompetitor(cursor.getInt(CompetitorContract.getColumnPos(CompetitorContract.CompetitorEntry.COLUMN_NAME_GROUP_COMPETITOR_ID))),
                        getFaculty(cursor.getInt(CompetitorContract.getColumnPos(CompetitorContract.CompetitorEntry.COLUMN_NAME_FACULTY_ID))),
                        getCompetition(cursor.getInt(CompetitorContract.getColumnPos(CompetitorContract.CompetitorEntry.COLUMN_NAME_COMPETITION_ID))),
                        cursor.getInt(CompetitorContract.getColumnPos(CompetitorContract.CompetitorEntry.COLUMN_NAME_ORDINAL_NUM)),
                        cursor.getInt(CompetitorContract.getColumnPos(CompetitorContract.CompetitorEntry.COLUMN_NAME_IS_DISQUALIFIED)) > 0
                );
            }
        } catch (Exception exc) {
            //TO DO: Call Logger.ShowError;
        } finally {
            if (db != null)
                db.close();
        }
        return competitor;
    }

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

    private FacultyFromDb getFaculty(int id) {
        SqlFacultyRepository repo = new SqlFacultyRepository(context);
        FacultyFromDb faculty = repo.getFaculty(id);
        repo.close();
        return faculty;
    }

    private CompetitionFromDb getCompetition(int id) {
        SqlCompetitionRepository repo = new SqlCompetitionRepository(context);
        CompetitionFromDb competition = repo.getCompetition(id);
        repo.close();
        return competition;
    }
}

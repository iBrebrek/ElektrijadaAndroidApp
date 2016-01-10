package hr.fer.elektrijada.dal.sql.helper.events;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import hr.fer.elektrijada.dal.sql.DbHelper;
import hr.fer.elektrijada.dal.sql.category.CategoryContract;
import hr.fer.elektrijada.dal.sql.competition.CompetitionContract;
import hr.fer.elektrijada.dal.sql.competitionscore.CompetitionScoreContract;
import hr.fer.elektrijada.dal.sql.competitor.CompetitorContract;
import hr.fer.elektrijada.dal.sql.duel.DuelContract;
import hr.fer.elektrijada.dal.sql.duelscore.DuelScoreContract;
import hr.fer.elektrijada.model.events.Event;
import hr.fer.elektrijada.model.events.CompetitionEvent;
import hr.fer.elektrijada.model.events.DuelEvent;
import hr.fer.elektrijada.util.DateParserUtil;
import hr.fer.elektrijada.util.Logger;

/**
 * ovo cu mozda izbrisati i koristiti sql upite ostalih razreda, ako ce biti prakticnije
 */
public class SqlGetEventsInfo {
    SQLiteOpenHelper dbHelper;

    public SqlGetEventsInfo(Context context) {
        dbHelper = new DbHelper(context);
    }

    public void close() {
        if (dbHelper != null) {
            dbHelper.close();
        }
    }

    //samo za testiranje
    public void addFakeEvents() {
        long test;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        //db.delete(CategoryContract.CategoryEntry.TABLE_NAME, CategoryContract.CategoryEntry._ID + "=?", new String[]{"5"});
        //db.delete(CompetitionContract.CompetitionEntry.TABLE_NAME, CompetitionContract.CompetitionEntry._ID + "=?", new String[]{"7"});

        values.put(CategoryContract.CategoryEntry._ID, 5);
        values.put(CategoryContract.CategoryEntry.COLUMN_NAME_NAME, "Matematika");
        values.put(CategoryContract.CategoryEntry.COLUMN_NAME_NICK, "mat");
        values.put(CategoryContract.CategoryEntry.COLUMN_NAME_IS_SPORT, 0);
        test = db.insert(CategoryContract.CategoryEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CategoryContract.CategoryEntry._ID, 3);
        values.put(CategoryContract.CategoryEntry.COLUMN_NAME_NAME, "Nogomet");
        values.put(CategoryContract.CategoryEntry.COLUMN_NAME_NICK, "Muški");
        values.put(CategoryContract.CategoryEntry.COLUMN_NAME_IS_SPORT, 5);
        test = db.insert(CategoryContract.CategoryEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CategoryContract.CategoryEntry._ID, 55);
        values.put(CategoryContract.CategoryEntry.COLUMN_NAME_NAME, "Informatika");
        values.put(CategoryContract.CategoryEntry.COLUMN_NAME_IS_SPORT, 0);
        test = db.insert(CategoryContract.CategoryEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CategoryContract.CategoryEntry._ID, 777);
        values.put(CategoryContract.CategoryEntry.COLUMN_NAME_NAME, "Veslanje");
        values.put(CategoryContract.CategoryEntry.COLUMN_NAME_IS_SPORT, 5);
        test = db.insert(CategoryContract.CategoryEntry.TABLE_NAME, null, values);
        for (int i = 100; i < 150; i++) {
            values = new ContentValues();
            values.put(CategoryContract.CategoryEntry._ID, i);
            values.put(CategoryContract.CategoryEntry.COLUMN_NAME_NAME, "Test " + i);
            values.put(CategoryContract.CategoryEntry.COLUMN_NAME_IS_SPORT, -9);
            test = db.insert(CategoryContract.CategoryEntry.TABLE_NAME, null, values);
        }


        values = new ContentValues();
        values.put(CompetitionContract.CompetitionEntry._ID, 7);
        values.put(CompetitionContract.CompetitionEntry.COLUMN_NAME_CATEGORY_ID, 5);
        values.put(CompetitionContract.CompetitionEntry.COLUMN_NAME_LOCATION, "mjesto");
        values.put(CompetitionContract.CompetitionEntry.COLUMN_NAME_TIME_FROM, "2015.12.16. 20:59:00");
        values.put(CompetitionContract.CompetitionEntry.COLUMN_NAME_TIME_TO, "2015.12.16. 22:33:00");
        values.put(CompetitionContract.CompetitionEntry.COLUMN_NAME_IS_ASSUMPTION, 0);
        test = db.insert(CompetitionContract.CompetitionEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CompetitionScoreContract.CompetitionScoreEntry._ID, 777);
        values.put(CompetitionScoreContract.CompetitionScoreEntry.COLUMN_NAME_RESULT, "100");
        values.put(CompetitionScoreContract.CompetitionScoreEntry.COLUMN_NAME_COMPETITION_ID, 7);
        values.put(CompetitionScoreContract.CompetitionScoreEntry.COLUMN_NAME_USER_ID, 11);
        values.put(CompetitionScoreContract.CompetitionScoreEntry.COLUMN_NAME_COMPETITOR_ID, 55);
        values.put(CompetitionScoreContract.CompetitionScoreEntry.COLUMN_NAME_IS_ASSUMPTION, 0);
        values.put(CompetitionScoreContract.CompetitionScoreEntry.COLUMN_NAME_IS_OFFICIAL, 1);
        test = db.insert(CompetitionScoreContract.CompetitionScoreEntry.TABLE_NAME, null, values);
        //db.delete(CompetitionScoreContract.CompetitionScoreEntry.TABLE_NAME,null,null);
        values = new ContentValues();
        values.put(DuelContract.DuelEntry._ID, 85);
        values.put(DuelContract.DuelEntry.COLUMN_NAME_TIME_FROM, "2015.12.16. 20:59:00");
        values.put(DuelContract.DuelEntry.COLUMN_NAME_TIME_TO, "2015.12.16. 22:33:00");
        values.put(DuelContract.DuelEntry.COLUMN_NAME_CATEGORY_ID, 3);
        values.put(DuelContract.DuelEntry.COLUMN_NAME_COMPETITOR_1_ID, 1);
        values.put(DuelContract.DuelEntry.COLUMN_NAME_COMPETITOR_2_ID, 2);
        values.put(DuelContract.DuelEntry.COLUMN_NAME_IS_ASSUMPTION, 0);
        test = db.insert(DuelContract.DuelEntry.TABLE_NAME, null, values);

        for (int i = 100; i < 103; i++) {
            values = new ContentValues();
            values.put(DuelScoreContract.DuelScoreEntry._ID, i);
            values.put(DuelScoreContract.DuelScoreEntry.COLUMN_NAME_SCORE_1, 5);
            values.put(DuelScoreContract.DuelScoreEntry.COLUMN_NAME_SCORE_2, 7);
            values.put(DuelScoreContract.DuelScoreEntry.COLUMN_NAME_DUEL_ID, 85);
            values.put(DuelScoreContract.DuelScoreEntry.COLUMN_NAME_USER_ID, 1);
            values.put(DuelScoreContract.DuelScoreEntry.COLUMN_NAME_IS_ASSUMPTION, 0);
            test = db.insert(DuelScoreContract.DuelScoreEntry.TABLE_NAME, null, values);
        }
        values = new ContentValues();
        values.put(DuelScoreContract.DuelScoreEntry._ID, 99);
        values.put(DuelScoreContract.DuelScoreEntry.COLUMN_NAME_SCORE_1, 5);
        values.put(DuelScoreContract.DuelScoreEntry.COLUMN_NAME_SCORE_2, 5);
        values.put(DuelScoreContract.DuelScoreEntry.COLUMN_NAME_DUEL_ID, 85);
        values.put(DuelScoreContract.DuelScoreEntry.COLUMN_NAME_USER_ID, 1);
        values.put(DuelScoreContract.DuelScoreEntry.COLUMN_NAME_IS_ASSUMPTION, 0);
        test = db.insert(DuelScoreContract.DuelScoreEntry.TABLE_NAME, null, values);

        values = new ContentValues();
        values.put(CompetitorContract.CompetitorEntry._ID, 1);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_NAME, "Ime");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_SURNAME, "Prezime");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_IS_PERSON, 1);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_FACULTY_ID, 1);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_IS_DISQUALIFIED, 0);
        test = db.insert(CompetitorContract.CompetitorEntry.TABLE_NAME, null, values);

        values = new ContentValues();
        values.put(CompetitorContract.CompetitorEntry._ID, 2);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_NAME, "Tim");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_IS_PERSON, 0);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_FACULTY_ID, 1);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_IS_DISQUALIFIED, 0);
        test = db.insert(CompetitorContract.CompetitorEntry.TABLE_NAME, null, values);
        db.close();
    }

    public ArrayList<Event> getAllEvents() {
        ArrayList<Event> listOfEvents = new ArrayList<>();
        listOfEvents.addAll(getAllCompetitionEvents());
        listOfEvents.addAll(getAllDuelEvents());
        return listOfEvents;
    }

    public ArrayList<Event> getAllDuelEvents() {
        ArrayList<Event> listOfDuelEvents = new ArrayList<>();
        SQLiteDatabase db = null;
        try {
            db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(
                    "SELECT " + DuelContract.DuelEntry.TABLE_NAME + "." + DuelContract.DuelEntry._ID + ", "
                            + CategoryContract.CategoryEntry.TABLE_NAME + "." + CategoryContract.CategoryEntry.COLUMN_NAME_NAME + ", "
                            + DuelContract.DuelEntry.TABLE_NAME + "." + DuelContract.DuelEntry.COLUMN_NAME_TIME_FROM + ", "
                            + DuelContract.DuelEntry.TABLE_NAME + "." + DuelContract.DuelEntry.COLUMN_NAME_TIME_TO + ", "
                            + "(SELECT CASE "
                            + "WHEN " + CompetitorContract.CompetitorEntry.COLUMN_NAME_SURNAME + " IS NULL "
                            + "THEN " + CompetitorContract.CompetitorEntry.COLUMN_NAME_NAME
                            + " ELSE " + CompetitorContract.CompetitorEntry.COLUMN_NAME_NAME + " ||  ' ' || "
                            + CompetitorContract.CompetitorEntry.COLUMN_NAME_SURNAME
                            + " END AS FullName1 "
                            + "FROM " + CompetitorContract.CompetitorEntry.TABLE_NAME
                            + " WHERE " + CompetitorContract.CompetitorEntry._ID + " = "
                            + DuelContract.DuelEntry.TABLE_NAME + "." + DuelContract.DuelEntry.COLUMN_NAME_COMPETITOR_1_ID
                            + "), "
                            + "(SELECT CASE "
                            + "WHEN " + CompetitorContract.CompetitorEntry.COLUMN_NAME_SURNAME + " IS NULL "
                            + "THEN " + CompetitorContract.CompetitorEntry.COLUMN_NAME_NAME
                            + " ELSE " + CompetitorContract.CompetitorEntry.COLUMN_NAME_NAME + " ||  ' ' || "
                            + CompetitorContract.CompetitorEntry.COLUMN_NAME_SURNAME
                            + " END AS FullName2 "
                            + "FROM " + CompetitorContract.CompetitorEntry.TABLE_NAME
                            + " WHERE " + CompetitorContract.CompetitorEntry._ID + " = "
                            + DuelContract.DuelEntry.TABLE_NAME + "." + DuelContract.DuelEntry.COLUMN_NAME_COMPETITOR_2_ID
                            + ") " +
                            "FROM " + DuelContract.DuelEntry.TABLE_NAME + ", "
                            + CategoryContract.CategoryEntry.TABLE_NAME +
                            " WHERE " + DuelContract.DuelEntry.TABLE_NAME + "." + DuelContract.DuelEntry.COLUMN_NAME_CATEGORY_ID +
                            " = "
                            + CategoryContract.CategoryEntry.TABLE_NAME + "." + CategoryContract.CategoryEntry._ID,
                    null);
            if (cursor.moveToFirst()) {
                do {
                    DuelEvent event = new DuelEvent(
                            cursor.getInt(0),
                            cursor.getString(1),
                            DateParserUtil.stringToDate(cursor.getString(2)),
                            DateParserUtil.stringToDate(cursor.getString(3)),
                            cursor.getString(4),
                            cursor.getString(5)
                    );
                    listOfDuelEvents.add(event);
                    setResultMap(event);
                } while (cursor.moveToNext());
            }
            cursor.close();
        } catch (Exception exc) {
            Logger.LogException(exc);
        } finally {
            if (db != null)
                db.close();
        }
        return listOfDuelEvents;
    }

    private void setResultMap(DuelEvent event) {
        SQLiteDatabase db = null;
        try {
            db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(
                    "SELECT " + DuelScoreContract.DuelScoreEntry.COLUMN_NAME_SCORE_1 + ", "
                            + DuelScoreContract.DuelScoreEntry.COLUMN_NAME_SCORE_2 + ", "
                            + "COUNT(*) " +
                            "FROM " + DuelScoreContract.DuelScoreEntry.TABLE_NAME +
                            " WHERE " + DuelScoreContract.DuelScoreEntry.COLUMN_NAME_DUEL_ID + " = " + event.getId() +
                            " GROUP BY "
                            + DuelScoreContract.DuelScoreEntry.COLUMN_NAME_SCORE_1 + ", "
                            + DuelScoreContract.DuelScoreEntry.COLUMN_NAME_SCORE_2,
                    null);
            if (cursor.moveToFirst()) {
                do {
                    event.addPossibleResult(
                            cursor.getInt(0),
                            cursor.getInt(1),
                            cursor.getInt(2)
                    );
                } while (cursor.moveToNext());
            }
            cursor.close();
        } catch (Exception exc) {
            Logger.LogException(exc);
        } finally {
            if (db != null)
                db.close();
        }
    }

    public ArrayList<Event> getAllCompetitionEvents() {
        ArrayList<Event> listOfCompetitionEvents = new ArrayList<>();
        SQLiteDatabase db = null;
        try {
            db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(
                    "SELECT " + CompetitionContract.CompetitionEntry.TABLE_NAME + "." + CompetitionContract.CompetitionEntry._ID + ", "
                            + CategoryContract.CategoryEntry.TABLE_NAME + "." + CategoryContract.CategoryEntry.COLUMN_NAME_NAME + ", "
                            + CompetitionContract.CompetitionEntry.TABLE_NAME + "." + CompetitionContract.CompetitionEntry.COLUMN_NAME_TIME_FROM + ", "
                            + CompetitionContract.CompetitionEntry.TABLE_NAME + "." + CompetitionContract.CompetitionEntry.COLUMN_NAME_TIME_TO + ", "
                            + "(SELECT " + CompetitionScoreContract.CompetitionScoreEntry._ID +
                            " FROM " + CompetitionScoreContract.CompetitionScoreEntry.TABLE_NAME +
                            " WHERE " + CompetitionScoreContract.CompetitionScoreEntry.COLUMN_NAME_COMPETITION_ID +
                            " = "
                            + CompetitionContract.CompetitionEntry.TABLE_NAME + "." + CompetitionContract.CompetitionEntry._ID
                            + ") " +
                            "FROM " + CompetitionContract.CompetitionEntry.TABLE_NAME + ", "
                            + CategoryContract.CategoryEntry.TABLE_NAME +
                            " WHERE " + CompetitionContract.CompetitionEntry.TABLE_NAME + "." + CompetitionContract.CompetitionEntry.COLUMN_NAME_CATEGORY_ID +
                            " = "
                            + CategoryContract.CategoryEntry.TABLE_NAME + "." + CategoryContract.CategoryEntry._ID,
                    null);
            if (cursor.moveToFirst()) {
                do {
                    CompetitionEvent event = new CompetitionEvent(
                            cursor.getInt(0),
                            cursor.getString(1),
                            DateParserUtil.stringToDate(cursor.getString(2)),
                            DateParserUtil.stringToDate(cursor.getString(3)),
                            cursor.getInt(4) > 0
                    );
                    listOfCompetitionEvents.add(event);
                } while (cursor.moveToNext());
            }
            cursor.close();
        } catch (Exception exc) {
            Logger.LogException(exc);
        } finally {
            if (db != null)
                db.close();
        }
        return listOfCompetitionEvents;
    }
}

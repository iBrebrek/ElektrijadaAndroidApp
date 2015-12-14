package hr.fer.elektrijada.dal.sql.helper.events;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import hr.fer.elektrijada.dal.sql.DbHelper;
import hr.fer.elektrijada.dal.sql.duel.DuelContract;
import hr.fer.elektrijada.model.events.Event;
import hr.fer.elektrijada.model.events.KnowledgeEvent;
import hr.fer.elektrijada.model.events.SportEvent;
import hr.fer.elektrijada.util.Logger;

/**
 * ovo cu mozda izbrisati i koristiti sql upite ostalih razreda, kada se dodaju
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

    public List<Event> getAllEvents() {
        List<Event> listOfEvents = new ArrayList<>();
        listOfEvents.addAll(getAllKnowledgeEvents());
        listOfEvents.addAll(getAllSportEvents());
        return listOfEvents;
    }

    public List<SportEvent> getAllSportEvents() {
        List<SportEvent> listOfSportEvents = new ArrayList<>();
        SQLiteDatabase db = null;
        try {
            db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(
                    "SELECT * FROM " + DuelContract.DuelEntry.TABLE_NAME, null);
            if (cursor.moveToFirst()) {
                do {
                    // SportEvent event = new SportEvent()
                } while (cursor.moveToNext());
            }
            cursor.close();
        } catch (Exception exc) {
            Logger.LogException(exc);
        } finally {
            if (db != null)
                db.close();
        }
        return listOfSportEvents;
    }

    public List<KnowledgeEvent> getAllKnowledgeEvents() {
        List<KnowledgeEvent> listOfKnowledgeEvents = new ArrayList<>();


        return listOfKnowledgeEvents;
    }
}

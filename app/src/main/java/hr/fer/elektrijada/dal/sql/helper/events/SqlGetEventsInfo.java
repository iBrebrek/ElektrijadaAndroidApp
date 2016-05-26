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
import hr.fer.elektrijada.dal.sql.duelscore.SqlDuelScoreRepository;
import hr.fer.elektrijada.dal.sql.faculty.FacultyContract;
import hr.fer.elektrijada.dal.sql.stage.StageContract;
import hr.fer.elektrijada.dal.sql.user.UserContract;
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
    private final Context context;

    public SqlGetEventsInfo(Context context) {
        dbHelper = new DbHelper(context);
        this.context = context;
    }

    public void close() {
        if (dbHelper != null) {
            dbHelper.close();
        }
    }

    public void addData() {
        long test;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values;

        /********************************************************************************
                                            FAKULTETI
        ********************************************************************************/
        values = new ContentValues();
        values.put(FacultyContract.FacultyEntry._ID, 1);
        values.put(FacultyContract.FacultyEntry.COLUMN_NAME_NAME, "FTN Novi Sad");
        values.put(FacultyContract.FacultyEntry.COLUMN_NAME_NICK, "FTN");
        test = db.insert(FacultyContract.FacultyEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(FacultyContract.FacultyEntry._ID, 2);
        values.put(FacultyContract.FacultyEntry.COLUMN_NAME_NAME, "FESB Split");
        //values.put(FacultyContract.FacultyEntry.COLUMN_NAME_NICK, "FESB");
        test = db.insert(FacultyContract.FacultyEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(FacultyContract.FacultyEntry._ID, 3);
        values.put(FacultyContract.FacultyEntry.COLUMN_NAME_NAME, "ETF Sarajevo");
        //values.put(FacultyContract.FacultyEntry.COLUMN_NAME_NICK, "EFS");
        test = db.insert(FacultyContract.FacultyEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(FacultyContract.FacultyEntry._ID, 4);
        values.put(FacultyContract.FacultyEntry.COLUMN_NAME_NAME, "ETF Beograd");
       // values.put(FacultyContract.FacultyEntry.COLUMN_NAME_NICK, "ETF");
        test = db.insert(FacultyContract.FacultyEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(FacultyContract.FacultyEntry._ID, 5);
        values.put(FacultyContract.FacultyEntry.COLUMN_NAME_NAME, "ETF Banja Luka");
        //values.put(FacultyContract.FacultyEntry.COLUMN_NAME_NICK, "ETF");
        test = db.insert(FacultyContract.FacultyEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(FacultyContract.FacultyEntry._ID, 6);
        values.put(FacultyContract.FacultyEntry.COLUMN_NAME_NAME, "ETF Podgorica");
        //values.put(FacultyContract.FacultyEntry.COLUMN_NAME_NICK, "ETF");
        test = db.insert(FacultyContract.FacultyEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(FacultyContract.FacultyEntry._ID, 7);
        values.put(FacultyContract.FacultyEntry.COLUMN_NAME_NAME, "FEEIT Skoplje");
        //values.put(FacultyContract.FacultyEntry.COLUMN_NAME_NICK, "ETF");
        test = db.insert(FacultyContract.FacultyEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(FacultyContract.FacultyEntry._ID, 8);
        values.put(FacultyContract.FacultyEntry.COLUMN_NAME_NAME, "TVZ Zagreb");
        //values.put(FacultyContract.FacultyEntry.COLUMN_NAME_NICK, "ETF");
        test = db.insert(FacultyContract.FacultyEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(FacultyContract.FacultyEntry._ID, 9);
        values.put(FacultyContract.FacultyEntry.COLUMN_NAME_NAME, "Sveučilište, Split");
        //values.put(FacultyContract.FacultyEntry.COLUMN_NAME_NICK, "ETF");
        test = db.insert(FacultyContract.FacultyEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(FacultyContract.FacultyEntry._ID, 10);
        values.put(FacultyContract.FacultyEntry.COLUMN_NAME_NAME, "ICT Beograd");
        //values.put(FacultyContract.FacultyEntry.COLUMN_NAME_NICK, "ETF");
        test = db.insert(FacultyContract.FacultyEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(FacultyContract.FacultyEntry._ID, 11);
        values.put(FacultyContract.FacultyEntry.COLUMN_NAME_NAME, "Elektronski fakultet, Niš");
        //values.put(FacultyContract.FacultyEntry.COLUMN_NAME_NICK, "ETF");
        test = db.insert(FacultyContract.FacultyEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(FacultyContract.FacultyEntry._ID, 12);
        values.put(FacultyContract.FacultyEntry.COLUMN_NAME_NAME, "VSITE Zagreb");
        //values.put(FacultyContract.FacultyEntry.COLUMN_NAME_NICK, "ETF");
        test = db.insert(FacultyContract.FacultyEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(FacultyContract.FacultyEntry._ID, 13);
        values.put(FacultyContract.FacultyEntry.COLUMN_NAME_NAME, "ETF Osijek");
        //values.put(FacultyContract.FacultyEntry.COLUMN_NAME_NICK, "ETF");
        test = db.insert(FacultyContract.FacultyEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(FacultyContract.FacultyEntry._ID, 14);
        values.put(FacultyContract.FacultyEntry.COLUMN_NAME_NAME, "FTN Čačak");
        //values.put(FacultyContract.FacultyEntry.COLUMN_NAME_NICK, "ETF");
        test = db.insert(FacultyContract.FacultyEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(FacultyContract.FacultyEntry._ID, 15);
        values.put(FacultyContract.FacultyEntry.COLUMN_NAME_NAME, "ETF Istočno Sarajevo");
        //values.put(FacultyContract.FacultyEntry.COLUMN_NAME_NICK, "ETF");
        test = db.insert(FacultyContract.FacultyEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(FacultyContract.FacultyEntry._ID, 16);
        values.put(FacultyContract.FacultyEntry.COLUMN_NAME_NAME, "FSB Zagreb");
        //values.put(FacultyContract.FacultyEntry.COLUMN_NAME_NICK, "ETF");
        test = db.insert(FacultyContract.FacultyEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(FacultyContract.FacultyEntry._ID, 17);
        values.put(FacultyContract.FacultyEntry.COLUMN_NAME_NAME, "FOI Varaždin");
        //values.put(FacultyContract.FacultyEntry.COLUMN_NAME_NICK, "ETF");
        test = db.insert(FacultyContract.FacultyEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(FacultyContract.FacultyEntry._ID, 18);
        values.put(FacultyContract.FacultyEntry.COLUMN_NAME_NAME, "FTN Kosovska Mitrovica");
        //values.put(FacultyContract.FacultyEntry.COLUMN_NAME_NICK, "ETF");
        test = db.insert(FacultyContract.FacultyEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(FacultyContract.FacultyEntry._ID, 19);
        values.put(FacultyContract.FacultyEntry.COLUMN_NAME_NAME, "FER Zagreb");
        //values.put(FacultyContract.FacultyEntry.COLUMN_NAME_NICK, "ETF");
        test = db.insert(FacultyContract.FacultyEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(FacultyContract.FacultyEntry._ID, 20);
        values.put(FacultyContract.FacultyEntry.COLUMN_NAME_NAME, "Tehnički fakultet, Rijeka");
        //values.put(FacultyContract.FacultyEntry.COLUMN_NAME_NICK, "ETF");
        test = db.insert(FacultyContract.FacultyEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(FacultyContract.FacultyEntry._ID, 21);
        values.put(FacultyContract.FacultyEntry.COLUMN_NAME_NAME, "Fakultet elektrotehnike, Tuzla");
        //values.put(FacultyContract.FacultyEntry.COLUMN_NAME_NICK, "ETF");
        test = db.insert(FacultyContract.FacultyEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(FacultyContract.FacultyEntry._ID, 22);
        values.put(FacultyContract.FacultyEntry.COLUMN_NAME_NAME, "Tehničko sveučilište, Sofija");
        //values.put(FacultyContract.FacultyEntry.COLUMN_NAME_NICK, "ETF");
        test = db.insert(FacultyContract.FacultyEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(FacultyContract.FacultyEntry._ID, 23);
        values.put(FacultyContract.FacultyEntry.COLUMN_NAME_NAME, "FINKI Skoplje");
        //values.put(FacultyContract.FacultyEntry.COLUMN_NAME_NICK, "ETF");
        test = db.insert(FacultyContract.FacultyEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(FacultyContract.FacultyEntry._ID, 24);
        values.put(FacultyContract.FacultyEntry.COLUMN_NAME_NAME, "Tehnološko sveučilište, Varšava");
        //values.put(FacultyContract.FacultyEntry.COLUMN_NAME_NICK, "ETF");
        test = db.insert(FacultyContract.FacultyEntry.TABLE_NAME, null, values);


        /********************************************************************************
                                         Kategorije: SPORT
         ********************************************************************************/
        values = new ContentValues();
        values.put(CategoryContract.CategoryEntry._ID, 1);
        values.put(CategoryContract.CategoryEntry.COLUMN_NAME_NAME, "Nogomet (M)");
        values.put(CategoryContract.CategoryEntry.COLUMN_NAME_IS_SPORT, 1);
        values.put(CategoryContract.CategoryEntry.COLUMN_NAME_IS_DUEL, 1);
        test = db.insert(CategoryContract.CategoryEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CategoryContract.CategoryEntry._ID, 2);
        values.put(CategoryContract.CategoryEntry.COLUMN_NAME_NAME, "Nogomet (Ž)");
        values.put(CategoryContract.CategoryEntry.COLUMN_NAME_IS_SPORT, 1);
        values.put(CategoryContract.CategoryEntry.COLUMN_NAME_IS_DUEL, 1);
        test = db.insert(CategoryContract.CategoryEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CategoryContract.CategoryEntry._ID, 3);
        values.put(CategoryContract.CategoryEntry.COLUMN_NAME_NAME, "Odbojka (M)");
        values.put(CategoryContract.CategoryEntry.COLUMN_NAME_IS_SPORT, 1);
        values.put(CategoryContract.CategoryEntry.COLUMN_NAME_IS_DUEL, 1);
        test = db.insert(CategoryContract.CategoryEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CategoryContract.CategoryEntry._ID, 4);
        values.put(CategoryContract.CategoryEntry.COLUMN_NAME_NAME, "Odbojka (Ž)");
        values.put(CategoryContract.CategoryEntry.COLUMN_NAME_IS_SPORT, 1);
        values.put(CategoryContract.CategoryEntry.COLUMN_NAME_IS_DUEL, 1);
        test = db.insert(CategoryContract.CategoryEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CategoryContract.CategoryEntry._ID, 5);
        values.put(CategoryContract.CategoryEntry.COLUMN_NAME_NAME, "Košarka (M)");
        values.put(CategoryContract.CategoryEntry.COLUMN_NAME_IS_SPORT, 1);
        values.put(CategoryContract.CategoryEntry.COLUMN_NAME_IS_DUEL, 1);
        test = db.insert(CategoryContract.CategoryEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CategoryContract.CategoryEntry._ID, 6);
        values.put(CategoryContract.CategoryEntry.COLUMN_NAME_NAME, "Košarka (Ž)");
        values.put(CategoryContract.CategoryEntry.COLUMN_NAME_IS_SPORT, 1);
        values.put(CategoryContract.CategoryEntry.COLUMN_NAME_IS_DUEL, 1);
        test = db.insert(CategoryContract.CategoryEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CategoryContract.CategoryEntry._ID, 7);
        values.put(CategoryContract.CategoryEntry.COLUMN_NAME_NAME, "Rukomet (M)");
        values.put(CategoryContract.CategoryEntry.COLUMN_NAME_IS_SPORT, 1);
        values.put(CategoryContract.CategoryEntry.COLUMN_NAME_IS_DUEL, 1);
        test = db.insert(CategoryContract.CategoryEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CategoryContract.CategoryEntry._ID, 8);
        values.put(CategoryContract.CategoryEntry.COLUMN_NAME_NAME, "Stolni tenis (M)");
        values.put(CategoryContract.CategoryEntry.COLUMN_NAME_IS_SPORT, 1);
        values.put(CategoryContract.CategoryEntry.COLUMN_NAME_IS_DUEL, 1);
        test = db.insert(CategoryContract.CategoryEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CategoryContract.CategoryEntry._ID, 9);
        values.put(CategoryContract.CategoryEntry.COLUMN_NAME_NAME, "Stolni tenis (Ž)");
        values.put(CategoryContract.CategoryEntry.COLUMN_NAME_IS_SPORT, 1);
        values.put(CategoryContract.CategoryEntry.COLUMN_NAME_IS_DUEL, 1);
        test = db.insert(CategoryContract.CategoryEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CategoryContract.CategoryEntry._ID, 10);
        values.put(CategoryContract.CategoryEntry.COLUMN_NAME_NAME, "Stolni tenis (M+Ž)");
        values.put(CategoryContract.CategoryEntry.COLUMN_NAME_IS_SPORT, 1);
        values.put(CategoryContract.CategoryEntry.COLUMN_NAME_IS_DUEL, 1);
        test = db.insert(CategoryContract.CategoryEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CategoryContract.CategoryEntry._ID, 11);
        values.put(CategoryContract.CategoryEntry.COLUMN_NAME_NAME, "Šah");
        values.put(CategoryContract.CategoryEntry.COLUMN_NAME_IS_SPORT, 1);
        values.put(CategoryContract.CategoryEntry.COLUMN_NAME_IS_DUEL, 1);
        test = db.insert(CategoryContract.CategoryEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CategoryContract.CategoryEntry._ID, 12);
        values.put(CategoryContract.CategoryEntry.COLUMN_NAME_NAME, "Kros (M)");
        values.put(CategoryContract.CategoryEntry.COLUMN_NAME_IS_SPORT, 1);
        values.put(CategoryContract.CategoryEntry.COLUMN_NAME_IS_DUEL, 0);
        test = db.insert(CategoryContract.CategoryEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CategoryContract.CategoryEntry._ID, 13);
        values.put(CategoryContract.CategoryEntry.COLUMN_NAME_NAME, "Kros (Ž)");
        values.put(CategoryContract.CategoryEntry.COLUMN_NAME_IS_SPORT, 1);
        values.put(CategoryContract.CategoryEntry.COLUMN_NAME_IS_DUEL, 0);
        test = db.insert(CategoryContract.CategoryEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CategoryContract.CategoryEntry._ID, 14);
        values.put(CategoryContract.CategoryEntry.COLUMN_NAME_NAME, "Kros (M+Ž)");
        values.put(CategoryContract.CategoryEntry.COLUMN_NAME_IS_SPORT, 1);
        values.put(CategoryContract.CategoryEntry.COLUMN_NAME_IS_DUEL, 0);
        test = db.insert(CategoryContract.CategoryEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CategoryContract.CategoryEntry._ID, 15);
        values.put(CategoryContract.CategoryEntry.COLUMN_NAME_NAME, "Veslanje");
        values.put(CategoryContract.CategoryEntry.COLUMN_NAME_IS_SPORT, 1);
        values.put(CategoryContract.CategoryEntry.COLUMN_NAME_IS_DUEL, 0);
        test = db.insert(CategoryContract.CategoryEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CategoryContract.CategoryEntry._ID, 16);
        values.put(CategoryContract.CategoryEntry.COLUMN_NAME_NAME, "Odbojka na pijesku (M)");
        values.put(CategoryContract.CategoryEntry.COLUMN_NAME_IS_SPORT, 1);
        values.put(CategoryContract.CategoryEntry.COLUMN_NAME_IS_DUEL, 1);
        test = db.insert(CategoryContract.CategoryEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CategoryContract.CategoryEntry._ID, 17);
        values.put(CategoryContract.CategoryEntry.COLUMN_NAME_NAME, "Odbojka na pijesku (Ž)");
        values.put(CategoryContract.CategoryEntry.COLUMN_NAME_IS_SPORT, 1);
        values.put(CategoryContract.CategoryEntry.COLUMN_NAME_IS_DUEL, 1);
        test = db.insert(CategoryContract.CategoryEntry.TABLE_NAME, null, values);

        /********************************************************************************
                                        Kategorije: ZNANJE
         ********************************************************************************/
        values = new ContentValues();
        values.put(CategoryContract.CategoryEntry._ID, 21);
        values.put(CategoryContract.CategoryEntry.COLUMN_NAME_NAME, "Matematika 1");
        values.put(CategoryContract.CategoryEntry.COLUMN_NAME_IS_SPORT, 0);
        values.put(CategoryContract.CategoryEntry.COLUMN_NAME_IS_DUEL, 0);
        test = db.insert(CategoryContract.CategoryEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CategoryContract.CategoryEntry._ID, 22);
        values.put(CategoryContract.CategoryEntry.COLUMN_NAME_NAME, "Matematika 2");
        values.put(CategoryContract.CategoryEntry.COLUMN_NAME_IS_SPORT, 0);
        values.put(CategoryContract.CategoryEntry.COLUMN_NAME_IS_DUEL, 0);
        test = db.insert(CategoryContract.CategoryEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CategoryContract.CategoryEntry._ID, 23);
        values.put(CategoryContract.CategoryEntry.COLUMN_NAME_NAME, "Osnove elektrotehnike");
        values.put(CategoryContract.CategoryEntry.COLUMN_NAME_IS_SPORT, 0);
        values.put(CategoryContract.CategoryEntry.COLUMN_NAME_IS_DUEL, 0);
        test = db.insert(CategoryContract.CategoryEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CategoryContract.CategoryEntry._ID, 24);
        values.put(CategoryContract.CategoryEntry.COLUMN_NAME_NAME, "Fizika");
        values.put(CategoryContract.CategoryEntry.COLUMN_NAME_IS_SPORT, 0);
        values.put(CategoryContract.CategoryEntry.COLUMN_NAME_IS_DUEL, 0);
        test = db.insert(CategoryContract.CategoryEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CategoryContract.CategoryEntry._ID, 25);
        values.put(CategoryContract.CategoryEntry.COLUMN_NAME_NAME, "Teorija električnih krugova");
        values.put(CategoryContract.CategoryEntry.COLUMN_NAME_IS_SPORT, 0);
        values.put(CategoryContract.CategoryEntry.COLUMN_NAME_IS_DUEL, 0);
        test = db.insert(CategoryContract.CategoryEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CategoryContract.CategoryEntry._ID, 26);
        values.put(CategoryContract.CategoryEntry.COLUMN_NAME_NAME, "Elektronika 1");
        values.put(CategoryContract.CategoryEntry.COLUMN_NAME_IS_SPORT, 0);
        values.put(CategoryContract.CategoryEntry.COLUMN_NAME_IS_DUEL, 0);
        test = db.insert(CategoryContract.CategoryEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CategoryContract.CategoryEntry._ID, 27);
        values.put(CategoryContract.CategoryEntry.COLUMN_NAME_NAME, "Elektronika 2");
        values.put(CategoryContract.CategoryEntry.COLUMN_NAME_IS_SPORT, 0);
        values.put(CategoryContract.CategoryEntry.COLUMN_NAME_IS_DUEL, 0);
        test = db.insert(CategoryContract.CategoryEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CategoryContract.CategoryEntry._ID, 28);
        values.put(CategoryContract.CategoryEntry.COLUMN_NAME_NAME, "Automatika");
        values.put(CategoryContract.CategoryEntry.COLUMN_NAME_IS_SPORT, 0);
        values.put(CategoryContract.CategoryEntry.COLUMN_NAME_IS_DUEL, 0);
        test = db.insert(CategoryContract.CategoryEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CategoryContract.CategoryEntry._ID, 29);
        values.put(CategoryContract.CategoryEntry.COLUMN_NAME_NAME, "Telekomunikacije");
        values.put(CategoryContract.CategoryEntry.COLUMN_NAME_IS_SPORT, 0);
        values.put(CategoryContract.CategoryEntry.COLUMN_NAME_IS_DUEL, 0);
        test = db.insert(CategoryContract.CategoryEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CategoryContract.CategoryEntry._ID, 30);
        values.put(CategoryContract.CategoryEntry.COLUMN_NAME_NAME, "Električni strojevi");
        values.put(CategoryContract.CategoryEntry.COLUMN_NAME_IS_SPORT, 0);
        values.put(CategoryContract.CategoryEntry.COLUMN_NAME_IS_DUEL, 0);
        test = db.insert(CategoryContract.CategoryEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CategoryContract.CategoryEntry._ID, 31);
        values.put(CategoryContract.CategoryEntry.COLUMN_NAME_NAME, "Analiza elektroenergetskih sustava");
        values.put(CategoryContract.CategoryEntry.COLUMN_NAME_IS_SPORT, 0);
        values.put(CategoryContract.CategoryEntry.COLUMN_NAME_IS_DUEL, 0);
        test = db.insert(CategoryContract.CategoryEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CategoryContract.CategoryEntry._ID, 32);
        values.put(CategoryContract.CategoryEntry.COLUMN_NAME_NAME, "Informatika");
        values.put(CategoryContract.CategoryEntry.COLUMN_NAME_IS_SPORT, 0);
        values.put(CategoryContract.CategoryEntry.COLUMN_NAME_IS_DUEL, 0);
        test = db.insert(CategoryContract.CategoryEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CategoryContract.CategoryEntry._ID, 33);
        values.put(CategoryContract.CategoryEntry.COLUMN_NAME_NAME, "Objektno orijentirano programiranje");
        values.put(CategoryContract.CategoryEntry.COLUMN_NAME_IS_SPORT, 0);
        values.put(CategoryContract.CategoryEntry.COLUMN_NAME_IS_DUEL, 0);
        test = db.insert(CategoryContract.CategoryEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CategoryContract.CategoryEntry._ID, 34);
        values.put(CategoryContract.CategoryEntry.COLUMN_NAME_NAME, "Engleski jezik");
        values.put(CategoryContract.CategoryEntry.COLUMN_NAME_IS_SPORT, 0);
        values.put(CategoryContract.CategoryEntry.COLUMN_NAME_IS_DUEL, 0);
        test = db.insert(CategoryContract.CategoryEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CategoryContract.CategoryEntry._ID, 35);
        values.put(CategoryContract.CategoryEntry.COLUMN_NAME_NAME, "Obnovljivi izvori energije");
        values.put(CategoryContract.CategoryEntry.COLUMN_NAME_IS_SPORT, 0);
        values.put(CategoryContract.CategoryEntry.COLUMN_NAME_IS_DUEL, 0);
        test = db.insert(CategoryContract.CategoryEntry.TABLE_NAME, null, values);


        /********************************************************************************
                                    Natjecatelji u sportu: FAKULTETI (timovi)
         ********************************************************************************/
        values = new ContentValues();
        values.put(CompetitorContract.CompetitorEntry._ID, 101);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_NAME, "FTN Novi Sad");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_IS_PERSON, 0);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_FACULTY_ID, 1);
        test = db.insert(CompetitorContract.CompetitorEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CompetitorContract.CompetitorEntry._ID, 102);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_NAME, "FESB Split");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_IS_PERSON, 0);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_FACULTY_ID, 2);
        test = db.insert(CompetitorContract.CompetitorEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CompetitorContract.CompetitorEntry._ID, 103);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_NAME, "ETF Sarajevo");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_IS_PERSON, 0);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_FACULTY_ID, 3);
        test = db.insert(CompetitorContract.CompetitorEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CompetitorContract.CompetitorEntry._ID, 104);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_NAME, "ETF Beograd");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_IS_PERSON, 0);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_FACULTY_ID, 4);
        test = db.insert(CompetitorContract.CompetitorEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CompetitorContract.CompetitorEntry._ID, 105);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_NAME, "ETF Banja Luka");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_IS_PERSON, 0);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_FACULTY_ID, 5);
        test = db.insert(CompetitorContract.CompetitorEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CompetitorContract.CompetitorEntry._ID, 106);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_NAME, "ETF Podgorica");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_IS_PERSON, 0);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_FACULTY_ID, 6);
        test = db.insert(CompetitorContract.CompetitorEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CompetitorContract.CompetitorEntry._ID, 107);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_NAME, "FEEIT Skoplje");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_IS_PERSON, 0);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_FACULTY_ID, 7);
        test = db.insert(CompetitorContract.CompetitorEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CompetitorContract.CompetitorEntry._ID, 108);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_NAME, "TVZ Zagreb");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_IS_PERSON, 0);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_FACULTY_ID, 8);
        test = db.insert(CompetitorContract.CompetitorEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CompetitorContract.CompetitorEntry._ID, 109);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_NAME, "Sveučilište, Split");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_IS_PERSON, 0);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_FACULTY_ID, 9);
        test = db.insert(CompetitorContract.CompetitorEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CompetitorContract.CompetitorEntry._ID, 110);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_NAME, "ICT Beograd");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_IS_PERSON, 0);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_FACULTY_ID, 10);
        test = db.insert(CompetitorContract.CompetitorEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CompetitorContract.CompetitorEntry._ID, 111);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_NAME, "Elektronski fakultet, Niš");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_IS_PERSON, 0);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_FACULTY_ID, 11);
        test = db.insert(CompetitorContract.CompetitorEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CompetitorContract.CompetitorEntry._ID, 112);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_NAME, "VSITE Zagreb");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_IS_PERSON, 0);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_FACULTY_ID, 12);
        test = db.insert(CompetitorContract.CompetitorEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CompetitorContract.CompetitorEntry._ID, 113);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_NAME, "ETF Osijek");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_IS_PERSON, 0);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_FACULTY_ID, 13);
        test = db.insert(CompetitorContract.CompetitorEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CompetitorContract.CompetitorEntry._ID, 114);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_NAME, "FTN Čačak");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_IS_PERSON, 0);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_FACULTY_ID, 14);
        test = db.insert(CompetitorContract.CompetitorEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CompetitorContract.CompetitorEntry._ID, 115);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_NAME, "ETF Istočno Sarajevo");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_IS_PERSON, 0);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_FACULTY_ID, 15);
        test = db.insert(CompetitorContract.CompetitorEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CompetitorContract.CompetitorEntry._ID, 116);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_NAME, "FSB Zagreb");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_IS_PERSON, 0);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_FACULTY_ID, 16);
        test = db.insert(CompetitorContract.CompetitorEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CompetitorContract.CompetitorEntry._ID, 117);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_NAME, "FOI Varaždin");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_IS_PERSON, 0);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_FACULTY_ID, 17);
        test = db.insert(CompetitorContract.CompetitorEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CompetitorContract.CompetitorEntry._ID, 118);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_NAME, "FTN Kosovska Mitrovica");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_IS_PERSON, 0);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_FACULTY_ID, 18);
        test = db.insert(CompetitorContract.CompetitorEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CompetitorContract.CompetitorEntry._ID, 119);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_NAME, "FER Zagreb");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_IS_PERSON, 0);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_FACULTY_ID, 19);
        test = db.insert(CompetitorContract.CompetitorEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CompetitorContract.CompetitorEntry._ID, 120);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_NAME, "Tehnički fakultet, Rijeka");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_IS_PERSON, 0);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_FACULTY_ID, 20);
        test = db.insert(CompetitorContract.CompetitorEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CompetitorContract.CompetitorEntry._ID, 121);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_NAME, "Fakultet elektrotehnike, Tuzla");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_IS_PERSON, 0);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_FACULTY_ID, 21);
        test = db.insert(CompetitorContract.CompetitorEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CompetitorContract.CompetitorEntry._ID, 122);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_NAME, "Tehničko sveučilište, Sofija");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_IS_PERSON, 0);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_FACULTY_ID, 22);
        test = db.insert(CompetitorContract.CompetitorEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CompetitorContract.CompetitorEntry._ID, 123);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_NAME, "FINKI Skoplje");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_IS_PERSON, 0);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_FACULTY_ID, 23);
        test = db.insert(CompetitorContract.CompetitorEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CompetitorContract.CompetitorEntry._ID, 124);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_NAME, "Tehnološko sveučilište, Varšava");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_IS_PERSON, 0);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_FACULTY_ID, 24);
        test = db.insert(CompetitorContract.CompetitorEntry.TABLE_NAME, null, values);


        /********************************************************************************
                                   Natjecatelji u znanju: FER
         ********************************************************************************/
        values = new ContentValues();
        values.put(CompetitorContract.CompetitorEntry._ID, 1);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_NAME, "Dominik");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_SURNAME, "Šekrst");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_IS_PERSON, 1);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_FACULTY_ID, 19);
        test = db.insert(CompetitorContract.CompetitorEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CompetitorContract.CompetitorEntry._ID, 2);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_NAME, "Meri");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_SURNAME, "Tukač");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_IS_PERSON, 1);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_FACULTY_ID, 19);
        test = db.insert(CompetitorContract.CompetitorEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CompetitorContract.CompetitorEntry._ID, 3);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_NAME, "Dora");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_SURNAME, "Mešić");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_IS_PERSON, 1);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_FACULTY_ID, 19);
        test = db.insert(CompetitorContract.CompetitorEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CompetitorContract.CompetitorEntry._ID, 4);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_NAME, "Ivana");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_SURNAME, "Damjanović");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_IS_PERSON, 1);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_FACULTY_ID, 19);
        test = db.insert(CompetitorContract.CompetitorEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CompetitorContract.CompetitorEntry._ID, 5);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_NAME, "Zlatko");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_SURNAME, "Ofak");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_IS_PERSON, 1);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_FACULTY_ID, 19);
        test = db.insert(CompetitorContract.CompetitorEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CompetitorContract.CompetitorEntry._ID, 6);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_NAME, "Antonela");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_SURNAME, "Obad");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_IS_PERSON, 1);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_FACULTY_ID, 19);
        test = db.insert(CompetitorContract.CompetitorEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CompetitorContract.CompetitorEntry._ID, 7);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_NAME, "Borna");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_SURNAME, "Bićanić");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_IS_PERSON, 1);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_FACULTY_ID, 19);
        test = db.insert(CompetitorContract.CompetitorEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CompetitorContract.CompetitorEntry._ID, 8);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_NAME, "Marko");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_SURNAME, "Šarlija");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_IS_PERSON, 1);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_FACULTY_ID, 19);
        test = db.insert(CompetitorContract.CompetitorEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CompetitorContract.CompetitorEntry._ID, 9);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_NAME, "Juraj");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_SURNAME, "Peršić");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_IS_PERSON, 1);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_FACULTY_ID, 19);
        test = db.insert(CompetitorContract.CompetitorEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CompetitorContract.CompetitorEntry._ID, 10);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_NAME, "Tea");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_SURNAME, "Tovarović");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_IS_PERSON, 1);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_FACULTY_ID, 19);
        test = db.insert(CompetitorContract.CompetitorEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CompetitorContract.CompetitorEntry._ID, 11);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_NAME, "Luka");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_SURNAME, "Pevec");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_IS_PERSON, 1);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_FACULTY_ID, 19);
        test = db.insert(CompetitorContract.CompetitorEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CompetitorContract.CompetitorEntry._ID, 12);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_NAME, "Vice");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_SURNAME, "Živković");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_IS_PERSON, 1);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_FACULTY_ID, 19);
        test = db.insert(CompetitorContract.CompetitorEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CompetitorContract.CompetitorEntry._ID, 13);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_NAME, "Mate");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_SURNAME, "Lenkić");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_IS_PERSON, 1);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_FACULTY_ID, 19);
        test = db.insert(CompetitorContract.CompetitorEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CompetitorContract.CompetitorEntry._ID, 14);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_NAME, "Ivana");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_SURNAME, "Pavlić");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_IS_PERSON, 1);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_FACULTY_ID, 19);
        test = db.insert(CompetitorContract.CompetitorEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CompetitorContract.CompetitorEntry._ID, 15);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_NAME, "Boris");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_SURNAME, "Gardijan");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_IS_PERSON, 1);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_FACULTY_ID, 19);
        test = db.insert(CompetitorContract.CompetitorEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CompetitorContract.CompetitorEntry._ID, 16);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_NAME, "Ivan-Porin");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_SURNAME, "Tolić");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_IS_PERSON, 1);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_FACULTY_ID, 19);
        test = db.insert(CompetitorContract.CompetitorEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CompetitorContract.CompetitorEntry._ID, 17);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_NAME, "Anita");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_SURNAME, "Gribl");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_IS_PERSON, 1);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_FACULTY_ID, 19);
        test = db.insert(CompetitorContract.CompetitorEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CompetitorContract.CompetitorEntry._ID, 18);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_NAME, "Ivan");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_SURNAME, "Brezovec");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_IS_PERSON, 1);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_FACULTY_ID, 19);
        test = db.insert(CompetitorContract.CompetitorEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CompetitorContract.CompetitorEntry._ID, 19);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_NAME, "Ivan");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_SURNAME, "Pavić");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_IS_PERSON, 1);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_FACULTY_ID, 19);
        test = db.insert(CompetitorContract.CompetitorEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CompetitorContract.CompetitorEntry._ID, 20);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_NAME, "Mateo");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_SURNAME, "Stanić");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_IS_PERSON, 1);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_FACULTY_ID, 19);
        test = db.insert(CompetitorContract.CompetitorEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CompetitorContract.CompetitorEntry._ID, 21);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_NAME, "Dominik");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_SURNAME, "Kisić");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_IS_PERSON, 1);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_FACULTY_ID, 19);
        test = db.insert(CompetitorContract.CompetitorEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CompetitorContract.CompetitorEntry._ID, 22);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_NAME, "Tin");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_SURNAME, "Vlašić");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_IS_PERSON, 1);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_FACULTY_ID, 19);
        test = db.insert(CompetitorContract.CompetitorEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CompetitorContract.CompetitorEntry._ID, 23);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_NAME, "Josip");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_SURNAME, "Puškar");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_IS_PERSON, 1);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_FACULTY_ID, 19);
        test = db.insert(CompetitorContract.CompetitorEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CompetitorContract.CompetitorEntry._ID, 24);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_NAME, "Tomislav");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_SURNAME, "Marinović");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_IS_PERSON, 1);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_FACULTY_ID, 19);
        test = db.insert(CompetitorContract.CompetitorEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CompetitorContract.CompetitorEntry._ID, 25);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_NAME, "Marija");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_SURNAME, "Kalebota");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_IS_PERSON, 1);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_FACULTY_ID, 19);
        test = db.insert(CompetitorContract.CompetitorEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CompetitorContract.CompetitorEntry._ID, 26);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_NAME, "Dominik");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_SURNAME, "Barbarić");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_IS_PERSON, 1);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_FACULTY_ID, 19);
        test = db.insert(CompetitorContract.CompetitorEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CompetitorContract.CompetitorEntry._ID, 27);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_NAME, "Jan");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_SURNAME, "Corazza");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_IS_PERSON, 1);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_FACULTY_ID, 19);
        test = db.insert(CompetitorContract.CompetitorEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CompetitorContract.CompetitorEntry._ID, 28);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_NAME, "Petar");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_SURNAME, "Tomić");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_IS_PERSON, 1);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_FACULTY_ID, 19);
        test = db.insert(CompetitorContract.CompetitorEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CompetitorContract.CompetitorEntry._ID, 29);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_NAME, "Luka");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_SURNAME, "Mandić");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_IS_PERSON, 1);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_FACULTY_ID, 19);
        test = db.insert(CompetitorContract.CompetitorEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CompetitorContract.CompetitorEntry._ID, 30);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_NAME, "Marko");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_SURNAME, "Matijaščić");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_IS_PERSON, 1);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_FACULTY_ID, 19);
        test = db.insert(CompetitorContract.CompetitorEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CompetitorContract.CompetitorEntry._ID, 31);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_NAME, "Ante");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_SURNAME, "Orešković");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_IS_PERSON, 1);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_FACULTY_ID, 19);
        test = db.insert(CompetitorContract.CompetitorEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CompetitorContract.CompetitorEntry._ID, 32);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_NAME, "Tomislav");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_SURNAME, "Pavičić");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_IS_PERSON, 1);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_FACULTY_ID, 19);
        test = db.insert(CompetitorContract.CompetitorEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CompetitorContract.CompetitorEntry._ID, 33);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_NAME, "Marina");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_SURNAME, "Brezak");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_IS_PERSON, 1);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_FACULTY_ID, 19);
        test = db.insert(CompetitorContract.CompetitorEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CompetitorContract.CompetitorEntry._ID, 34);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_NAME, "Luka");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_SURNAME, "Barišić");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_IS_PERSON, 1);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_FACULTY_ID, 19);
        test = db.insert(CompetitorContract.CompetitorEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CompetitorContract.CompetitorEntry._ID, 35);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_NAME, "Mislav");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_SURNAME, "Bradač");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_IS_PERSON, 1);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_FACULTY_ID, 19);
        test = db.insert(CompetitorContract.CompetitorEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CompetitorContract.CompetitorEntry._ID, 36);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_NAME, "Petar");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_SURNAME, "Šegina");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_IS_PERSON, 1);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_FACULTY_ID, 19);
        test = db.insert(CompetitorContract.CompetitorEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CompetitorContract.CompetitorEntry._ID, 37);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_NAME, "Vedran");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_SURNAME, "Pintarić");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_IS_PERSON, 1);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_FACULTY_ID, 19);
        test = db.insert(CompetitorContract.CompetitorEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CompetitorContract.CompetitorEntry._ID, 38);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_NAME, "Ante");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_SURNAME, "Čulo");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_IS_PERSON, 1);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_FACULTY_ID, 19);
        test = db.insert(CompetitorContract.CompetitorEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CompetitorContract.CompetitorEntry._ID, 39);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_NAME, "Filip");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_SURNAME, "Reškov");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_IS_PERSON, 1);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_FACULTY_ID, 19);
        test = db.insert(CompetitorContract.CompetitorEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CompetitorContract.CompetitorEntry._ID, 40);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_NAME, "Zvonimir");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_SURNAME, "Jurelinac");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_IS_PERSON, 1);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_FACULTY_ID, 19);
        test = db.insert(CompetitorContract.CompetitorEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CompetitorContract.CompetitorEntry._ID, 41);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_NAME, "Albert");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_SURNAME, "Škegro");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_IS_PERSON, 1);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_FACULTY_ID, 19);
        test = db.insert(CompetitorContract.CompetitorEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CompetitorContract.CompetitorEntry._ID, 42);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_NAME, "Andrija");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_SURNAME, "Drk");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_IS_PERSON, 1);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_FACULTY_ID, 19);
        test = db.insert(CompetitorContract.CompetitorEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CompetitorContract.CompetitorEntry._ID, 43);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_NAME, "Dorotea");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_SURNAME, "Protrka");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_IS_PERSON, 1);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_FACULTY_ID, 19);
        test = db.insert(CompetitorContract.CompetitorEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CompetitorContract.CompetitorEntry._ID, 44);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_NAME, "Juraj");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_SURNAME, "Oršulić");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_IS_PERSON, 1);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_FACULTY_ID, 19);
        test = db.insert(CompetitorContract.CompetitorEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CompetitorContract.CompetitorEntry._ID, 45);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_NAME, "Lovre");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_SURNAME, "Mrčela");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_IS_PERSON, 1);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_FACULTY_ID, 19);
        test = db.insert(CompetitorContract.CompetitorEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CompetitorContract.CompetitorEntry._ID, 46);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_NAME, "Marsela");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_SURNAME, "Polić");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_IS_PERSON, 1);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_FACULTY_ID, 19);
        test = db.insert(CompetitorContract.CompetitorEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CompetitorContract.CompetitorEntry._ID, 47);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_NAME, "Borna");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_SURNAME, "Vukadinović");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_IS_PERSON, 1);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_FACULTY_ID, 19);
        test = db.insert(CompetitorContract.CompetitorEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CompetitorContract.CompetitorEntry._ID, 48);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_NAME, "Luka");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_SURNAME, "Petrović");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_IS_PERSON, 1);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_FACULTY_ID, 19);
        test = db.insert(CompetitorContract.CompetitorEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CompetitorContract.CompetitorEntry._ID, 49);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_NAME, "Patrik");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_SURNAME, "Kolarić");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_IS_PERSON, 1);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_FACULTY_ID, 19);
        test = db.insert(CompetitorContract.CompetitorEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CompetitorContract.CompetitorEntry._ID, 50);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_NAME, "Karlo");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_SURNAME, "Dumbović");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_IS_PERSON, 1);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_FACULTY_ID, 19);
        test = db.insert(CompetitorContract.CompetitorEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CompetitorContract.CompetitorEntry._ID, 51);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_NAME, "Nenad");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_SURNAME, "Banfić");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_IS_PERSON, 1);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_FACULTY_ID, 19);
        test = db.insert(CompetitorContract.CompetitorEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CompetitorContract.CompetitorEntry._ID, 52);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_NAME, "Ivan");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_SURNAME, "Orehovec");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_IS_PERSON, 1);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_FACULTY_ID, 19);
        test = db.insert(CompetitorContract.CompetitorEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CompetitorContract.CompetitorEntry._ID, 53);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_NAME, "Kristijan");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_SURNAME, "Bišćanić");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_IS_PERSON, 1);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_FACULTY_ID, 19);
        test = db.insert(CompetitorContract.CompetitorEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CompetitorContract.CompetitorEntry._ID, 54);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_NAME, "Viktor");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_SURNAME, "Kolobarić");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_IS_PERSON, 1);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_FACULTY_ID, 19);
        test = db.insert(CompetitorContract.CompetitorEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CompetitorContract.CompetitorEntry._ID, 55);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_NAME, "Denis");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_SURNAME, "Šeper");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_IS_PERSON, 1);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_FACULTY_ID, 19);
        test = db.insert(CompetitorContract.CompetitorEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CompetitorContract.CompetitorEntry._ID, 56);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_NAME, "Dora");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_SURNAME, "Gazivoda");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_IS_PERSON, 1);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_FACULTY_ID, 19);
        test = db.insert(CompetitorContract.CompetitorEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CompetitorContract.CompetitorEntry._ID, 57);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_NAME, "Dunja");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_SURNAME, "Kunštek");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_IS_PERSON, 1);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_FACULTY_ID, 19);
        test = db.insert(CompetitorContract.CompetitorEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CompetitorContract.CompetitorEntry._ID, 58);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_NAME, "Toni");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_SURNAME, "Lončar");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_IS_PERSON, 1);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_FACULTY_ID, 19);
        test = db.insert(CompetitorContract.CompetitorEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CompetitorContract.CompetitorEntry._ID, 59);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_NAME, "Ante");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_SURNAME, "Ravlić");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_IS_PERSON, 1);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_FACULTY_ID, 19);
        test = db.insert(CompetitorContract.CompetitorEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CompetitorContract.CompetitorEntry._ID, 60);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_NAME, "Mladen");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_SURNAME, "Božić");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_IS_PERSON, 1);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_FACULTY_ID, 19);
        test = db.insert(CompetitorContract.CompetitorEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CompetitorContract.CompetitorEntry._ID, 61);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_NAME, "Krešimir");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_SURNAME, "Benčić");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_IS_PERSON, 1);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_FACULTY_ID, 19);
        test = db.insert(CompetitorContract.CompetitorEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CompetitorContract.CompetitorEntry._ID, 62);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_NAME, "Ivan");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_SURNAME, "Novko");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_IS_PERSON, 1);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_FACULTY_ID, 19);
        test = db.insert(CompetitorContract.CompetitorEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CompetitorContract.CompetitorEntry._ID, 63);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_NAME, "Karlo");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_SURNAME, "Šepetanc");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_IS_PERSON, 1);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_FACULTY_ID, 19);
        test = db.insert(CompetitorContract.CompetitorEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CompetitorContract.CompetitorEntry._ID, 64);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_NAME, "Dorian");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_SURNAME, "Ljubenko");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_IS_PERSON, 1);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_FACULTY_ID, 19);
        test = db.insert(CompetitorContract.CompetitorEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CompetitorContract.CompetitorEntry._ID, 65);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_NAME, "Zvonimir");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_SURNAME, "Hartl");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_IS_PERSON, 1);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_FACULTY_ID, 19);
        test = db.insert(CompetitorContract.CompetitorEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CompetitorContract.CompetitorEntry._ID, 66);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_NAME, "Zvonimir");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_SURNAME, "Zvone");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_IS_PERSON, 1);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_FACULTY_ID, 19);
        test = db.insert(CompetitorContract.CompetitorEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CompetitorContract.CompetitorEntry._ID, 67);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_NAME, "Petar");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_SURNAME, "Lovrić");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_IS_PERSON, 1);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_FACULTY_ID, 19);
        test = db.insert(CompetitorContract.CompetitorEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CompetitorContract.CompetitorEntry._ID, 68);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_NAME, "Antonio");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_SURNAME, "Krajinović");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_IS_PERSON, 1);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_FACULTY_ID, 19);
        test = db.insert(CompetitorContract.CompetitorEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CompetitorContract.CompetitorEntry._ID, 69);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_NAME, "Vjekoslav");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_SURNAME, "Salapić");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_IS_PERSON, 1);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_FACULTY_ID, 19);
        test = db.insert(CompetitorContract.CompetitorEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CompetitorContract.CompetitorEntry._ID, 70);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_NAME, "Ana");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_SURNAME, "Batinović");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_IS_PERSON, 1);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_FACULTY_ID, 19);
        test = db.insert(CompetitorContract.CompetitorEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CompetitorContract.CompetitorEntry._ID, 71);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_NAME, "Kristina");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_SURNAME, "Čižić");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_IS_PERSON, 1);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_FACULTY_ID, 19);
        test = db.insert(CompetitorContract.CompetitorEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CompetitorContract.CompetitorEntry._ID, 72);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_NAME, "Rafaela");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_SURNAME, "Vujević");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_IS_PERSON, 1);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_FACULTY_ID, 19);
        test = db.insert(CompetitorContract.CompetitorEntry.TABLE_NAME, null, values);

        /********************************************************************************
                                         DOGAĐAJI
         ********************************************************************************/
        values = new ContentValues();
        values.put(CompetitionContract.CompetitionEntry._ID, 1);
        values.put(CompetitionContract.CompetitionEntry.COLUMN_NAME_CATEGORY_ID, 28);
        //values.put(CompetitionContract.CompetitionEntry.COLUMN_NAME_LOCATION, "mjesto");
        values.put(CompetitionContract.CompetitionEntry.COLUMN_NAME_TIME_FROM, "2016.5.13. 9:30:00");
        values.put(CompetitionContract.CompetitionEntry.COLUMN_NAME_TIME_TO, "2016.5.13. 13:00:00");
        test = db.insert(CompetitionContract.CompetitionEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CompetitionContract.CompetitionEntry._ID, 2);
        values.put(CompetitionContract.CompetitionEntry.COLUMN_NAME_CATEGORY_ID, 29);
        //values.put(CompetitionContract.CompetitionEntry.COLUMN_NAME_LOCATION, "mjesto");
        values.put(CompetitionContract.CompetitionEntry.COLUMN_NAME_TIME_FROM, "2016.5.13. 9:30:00");
        values.put(CompetitionContract.CompetitionEntry.COLUMN_NAME_TIME_TO, "2016.5.13. 13:00:00");
        test = db.insert(CompetitionContract.CompetitionEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CompetitionContract.CompetitionEntry._ID, 3);
        values.put(CompetitionContract.CompetitionEntry.COLUMN_NAME_CATEGORY_ID, 25);
        //values.put(CompetitionContract.CompetitionEntry.COLUMN_NAME_LOCATION, "mjesto");
        values.put(CompetitionContract.CompetitionEntry.COLUMN_NAME_TIME_FROM, "2016.5.13. 9:30:00");
        values.put(CompetitionContract.CompetitionEntry.COLUMN_NAME_TIME_TO, "2016.5.13. 13:00:00");
        test = db.insert(CompetitionContract.CompetitionEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CompetitionContract.CompetitionEntry._ID, 4);
        values.put(CompetitionContract.CompetitionEntry.COLUMN_NAME_CATEGORY_ID, 26);
        //values.put(CompetitionContract.CompetitionEntry.COLUMN_NAME_LOCATION, "mjesto");
        values.put(CompetitionContract.CompetitionEntry.COLUMN_NAME_TIME_FROM, "2016.5.13. 14:30:00");
        values.put(CompetitionContract.CompetitionEntry.COLUMN_NAME_TIME_TO, "2016.5.13. 18:00:00");
        test = db.insert(CompetitionContract.CompetitionEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CompetitionContract.CompetitionEntry._ID, 5);
        values.put(CompetitionContract.CompetitionEntry.COLUMN_NAME_CATEGORY_ID, 35);
        //values.put(CompetitionContract.CompetitionEntry.COLUMN_NAME_LOCATION, "mjesto");
        values.put(CompetitionContract.CompetitionEntry.COLUMN_NAME_TIME_FROM, "2016.5.13. 14:30:00");
        values.put(CompetitionContract.CompetitionEntry.COLUMN_NAME_TIME_TO, "2016.5.13. 18:00:00");
        test = db.insert(CompetitionContract.CompetitionEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CompetitionContract.CompetitionEntry._ID, 6);
        values.put(CompetitionContract.CompetitionEntry.COLUMN_NAME_CATEGORY_ID, 32);
        //values.put(CompetitionContract.CompetitionEntry.COLUMN_NAME_LOCATION, "mjesto");
        values.put(CompetitionContract.CompetitionEntry.COLUMN_NAME_TIME_FROM, "2016.5.14. 9:30:00");
        values.put(CompetitionContract.CompetitionEntry.COLUMN_NAME_TIME_TO, "2016.5.14. 13:00:00");
        test = db.insert(CompetitionContract.CompetitionEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CompetitionContract.CompetitionEntry._ID, 7);
        values.put(CompetitionContract.CompetitionEntry.COLUMN_NAME_CATEGORY_ID, 31);
        //values.put(CompetitionContract.CompetitionEntry.COLUMN_NAME_LOCATION, "mjesto");
        values.put(CompetitionContract.CompetitionEntry.COLUMN_NAME_TIME_FROM, "2016.5.14. 9:30:00");
        values.put(CompetitionContract.CompetitionEntry.COLUMN_NAME_TIME_TO, "2016.5.14. 13:00:00");
        test = db.insert(CompetitionContract.CompetitionEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CompetitionContract.CompetitionEntry._ID, 8);
        values.put(CompetitionContract.CompetitionEntry.COLUMN_NAME_CATEGORY_ID, 34);
        //values.put(CompetitionContract.CompetitionEntry.COLUMN_NAME_LOCATION, "mjesto");
        values.put(CompetitionContract.CompetitionEntry.COLUMN_NAME_TIME_FROM, "2016.5.14. 14:30:00");
        values.put(CompetitionContract.CompetitionEntry.COLUMN_NAME_TIME_TO, "2016.5.14. 18:00:00");
        test = db.insert(CompetitionContract.CompetitionEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CompetitionContract.CompetitionEntry._ID, 9);
        values.put(CompetitionContract.CompetitionEntry.COLUMN_NAME_CATEGORY_ID, 21);
        //values.put(CompetitionContract.CompetitionEntry.COLUMN_NAME_LOCATION, "mjesto");
        values.put(CompetitionContract.CompetitionEntry.COLUMN_NAME_TIME_FROM, "2016.5.14. 14:30:00");
        values.put(CompetitionContract.CompetitionEntry.COLUMN_NAME_TIME_TO, "2016.5.14. 18:00:00");
        test = db.insert(CompetitionContract.CompetitionEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CompetitionContract.CompetitionEntry._ID, 10);
        values.put(CompetitionContract.CompetitionEntry.COLUMN_NAME_CATEGORY_ID, 33);
        //values.put(CompetitionContract.CompetitionEntry.COLUMN_NAME_LOCATION, "mjesto");
        values.put(CompetitionContract.CompetitionEntry.COLUMN_NAME_TIME_FROM, "2016.5.15. 9:30:00");
        values.put(CompetitionContract.CompetitionEntry.COLUMN_NAME_TIME_TO, "2016.5.15. 13:00:00");
        test = db.insert(CompetitionContract.CompetitionEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CompetitionContract.CompetitionEntry._ID, 11);
        values.put(CompetitionContract.CompetitionEntry.COLUMN_NAME_CATEGORY_ID, 24);
        //values.put(CompetitionContract.CompetitionEntry.COLUMN_NAME_LOCATION, "mjesto");
        values.put(CompetitionContract.CompetitionEntry.COLUMN_NAME_TIME_FROM, "2016.5.15. 9:30:00");
        values.put(CompetitionContract.CompetitionEntry.COLUMN_NAME_TIME_TO, "2016.5.15. 13:00:00");
        test = db.insert(CompetitionContract.CompetitionEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CompetitionContract.CompetitionEntry._ID, 12);
        values.put(CompetitionContract.CompetitionEntry.COLUMN_NAME_CATEGORY_ID, 22);
        //values.put(CompetitionContract.CompetitionEntry.COLUMN_NAME_LOCATION, "mjesto");
        values.put(CompetitionContract.CompetitionEntry.COLUMN_NAME_TIME_FROM, "2016.5.15. 14:30:00");
        values.put(CompetitionContract.CompetitionEntry.COLUMN_NAME_TIME_TO, "2016.5.15. 18:00:00");
        test = db.insert(CompetitionContract.CompetitionEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CompetitionContract.CompetitionEntry._ID, 13);
        values.put(CompetitionContract.CompetitionEntry.COLUMN_NAME_CATEGORY_ID, 27);
        //values.put(CompetitionContract.CompetitionEntry.COLUMN_NAME_LOCATION, "mjesto");
        values.put(CompetitionContract.CompetitionEntry.COLUMN_NAME_TIME_FROM, "2016.5.15. 14:30:00");
        values.put(CompetitionContract.CompetitionEntry.COLUMN_NAME_TIME_TO, "2016.5.15. 18:00:00");
        test = db.insert(CompetitionContract.CompetitionEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CompetitionContract.CompetitionEntry._ID, 14);
        values.put(CompetitionContract.CompetitionEntry.COLUMN_NAME_CATEGORY_ID, 30);
        //values.put(CompetitionContract.CompetitionEntry.COLUMN_NAME_LOCATION, "mjesto");
        values.put(CompetitionContract.CompetitionEntry.COLUMN_NAME_TIME_FROM, "2016.5.15. 14:30:00");
        values.put(CompetitionContract.CompetitionEntry.COLUMN_NAME_TIME_TO, "2016.5.15. 18:00:00");
        test = db.insert(CompetitionContract.CompetitionEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CompetitionContract.CompetitionEntry._ID, 15);
        values.put(CompetitionContract.CompetitionEntry.COLUMN_NAME_CATEGORY_ID, 23);
        //values.put(CompetitionContract.CompetitionEntry.COLUMN_NAME_LOCATION, "mjesto");
        values.put(CompetitionContract.CompetitionEntry.COLUMN_NAME_TIME_FROM, "2016.5.16. 9:30:00");
        values.put(CompetitionContract.CompetitionEntry.COLUMN_NAME_TIME_TO, "2016.5.16. 13:00:00");
        test = db.insert(CompetitionContract.CompetitionEntry.TABLE_NAME, null, values);


        /********************************************************************************
                                         Sport: STAGE
         ********************************************************************************/
        values = new ContentValues();
        values.put(StageContract.StageEntry._ID, 0);
        values.put(StageContract.StageEntry.COLUMN_NAME_NAME, "---");
        test = db.insert(StageContract.StageEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(StageContract.StageEntry._ID, 1);
        values.put(StageContract.StageEntry.COLUMN_NAME_NAME, "Finale");
        test = db.insert(StageContract.StageEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(StageContract.StageEntry._ID, 2);
        values.put(StageContract.StageEntry.COLUMN_NAME_NAME, "Polufinale");
        test = db.insert(StageContract.StageEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(StageContract.StageEntry._ID, 3);
        values.put(StageContract.StageEntry.COLUMN_NAME_NAME, "Četvrtfinale");
        test = db.insert(StageContract.StageEntry.TABLE_NAME, null, values);
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
        values.put(CategoryContract.CategoryEntry.COLUMN_NAME_IS_DUEL, 0);
        test = db.insert(CategoryContract.CategoryEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CategoryContract.CategoryEntry._ID, 3);
        values.put(CategoryContract.CategoryEntry.COLUMN_NAME_NAME, "Nogomet");
        values.put(CategoryContract.CategoryEntry.COLUMN_NAME_NICK, "Muški");
        values.put(CategoryContract.CategoryEntry.COLUMN_NAME_IS_SPORT, 5);
        values.put(CategoryContract.CategoryEntry.COLUMN_NAME_IS_DUEL, 5);
        test = db.insert(CategoryContract.CategoryEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CategoryContract.CategoryEntry._ID, 444);
        values.put(CategoryContract.CategoryEntry.COLUMN_NAME_NAME, "Košarka");
        values.put(CategoryContract.CategoryEntry.COLUMN_NAME_IS_SPORT, 1);
        values.put(CategoryContract.CategoryEntry.COLUMN_NAME_IS_DUEL, 1);
        test = db.insert(CategoryContract.CategoryEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CategoryContract.CategoryEntry._ID, 55);
        values.put(CategoryContract.CategoryEntry.COLUMN_NAME_NAME, "Informatika");
        values.put(CategoryContract.CategoryEntry.COLUMN_NAME_IS_SPORT, 0);
        values.put(CategoryContract.CategoryEntry.COLUMN_NAME_IS_DUEL, 0);
        test = db.insert(CategoryContract.CategoryEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CategoryContract.CategoryEntry._ID, 777);
        values.put(CategoryContract.CategoryEntry.COLUMN_NAME_NAME, "Veslanje");
        values.put(CategoryContract.CategoryEntry.COLUMN_NAME_IS_SPORT, 5);
        values.put(CategoryContract.CategoryEntry.COLUMN_NAME_IS_DUEL, -5);
        test = db.insert(CategoryContract.CategoryEntry.TABLE_NAME, null, values);
//        for (int i = 100; i < 150; i++) {
//            values = new ContentValues();
//            values.put(CategoryContract.CategoryEntry._ID, i);
//            values.put(CategoryContract.CategoryEntry.COLUMN_NAME_NAME, "Test " + i);
//            values.put(CategoryContract.CategoryEntry.COLUMN_NAME_IS_SPORT, -9);
//            values.put(CategoryContract.CategoryEntry.COLUMN_NAME_IS_DUEL, -5);
//            test = db.insert(CategoryContract.CategoryEntry.TABLE_NAME, null, values);
//        }


        values = new ContentValues();
        values.put(CompetitionContract.CompetitionEntry._ID, 7);
        values.put(CompetitionContract.CompetitionEntry.COLUMN_NAME_CATEGORY_ID, 5);
        values.put(CompetitionContract.CompetitionEntry.COLUMN_NAME_LOCATION, "mjesto");
        values.put(CompetitionContract.CompetitionEntry.COLUMN_NAME_TIME_FROM, "2015.12.16. 20:59:00");
        values.put(CompetitionContract.CompetitionEntry.COLUMN_NAME_TIME_TO, "2015.12.16. 22:33:00");
        values.put(CompetitionContract.CompetitionEntry.COLUMN_NAME_IS_ASSUMPTION, 0);
        test = db.insert(CompetitionContract.CompetitionEntry.TABLE_NAME, null, values);
        //db.delete(CompetitionScoreContract.CompetitionScoreEntry.TABLE_NAME,null,null);
        values = new ContentValues();
        values.put(DuelContract.DuelEntry._ID, 85);
        values.put(DuelContract.DuelEntry.COLUMN_NAME_TIME_FROM, "2015.12.16. 20:59:00");
        values.put(DuelContract.DuelEntry.COLUMN_NAME_TIME_TO, "2015.12.16. 22:33:00");
        values.put(DuelContract.DuelEntry.COLUMN_NAME_CATEGORY_ID, 3);
        values.put(DuelContract.DuelEntry.COLUMN_NAME_COMPETITOR_1_ID, 1);
        values.put(DuelContract.DuelEntry.COLUMN_NAME_COMPETITOR_2_ID, 101);
        values.put(DuelContract.DuelEntry.COLUMN_NAME_STAGE_ID, 2);
        values.put(DuelContract.DuelEntry.COLUMN_NAME_IS_ASSUMPTION, 0);
        test = db.insert(DuelContract.DuelEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(DuelContract.DuelEntry._ID, 33);
        values.put(DuelContract.DuelEntry.COLUMN_NAME_TIME_FROM, "2016.2.16. 20:59:00");
        values.put(DuelContract.DuelEntry.COLUMN_NAME_TIME_TO, "2016.2.16. 22:33:00");
        values.put(DuelContract.DuelEntry.COLUMN_NAME_CATEGORY_ID, 444);
        values.put(DuelContract.DuelEntry.COLUMN_NAME_COMPETITOR_1_ID, 2);
        values.put(DuelContract.DuelEntry.COLUMN_NAME_COMPETITOR_2_ID, 100);
        values.put(DuelContract.DuelEntry.COLUMN_NAME_STAGE_ID, 1);
        values.put(DuelContract.DuelEntry.COLUMN_NAME_IS_ASSUMPTION, 0);
        test = db.insert(DuelContract.DuelEntry.TABLE_NAME, null, values);

        //ima ih tolko da bi testirali el uzima onog koj se najvise pojavljuje
        for (int i = 200; i < 204; i++) {
            values = new ContentValues();
            values.put(DuelScoreContract.DuelScoreEntry._ID, i);
            values.put(DuelScoreContract.DuelScoreEntry.COLUMN_NAME_SCORE_1, 3);
            values.put(DuelScoreContract.DuelScoreEntry.COLUMN_NAME_SCORE_2, 2);
            values.put(DuelScoreContract.DuelScoreEntry.COLUMN_NAME_DUEL_ID, 85);
            values.put(DuelScoreContract.DuelScoreEntry.COLUMN_NAME_USER_ID, 1+i);
            values.put(DuelScoreContract.DuelScoreEntry.COLUMN_NAME_IS_ASSUMPTION, 0);
            test = db.insert(DuelScoreContract.DuelScoreEntry.TABLE_NAME, null, values);
            values = new ContentValues();
            values.put(UserContract.UserEntry._ID, 1+i);
            values.put(UserContract.UserEntry.COLUMN_NAME_NAME, "Name" + (i+1));
            values.put(UserContract.UserEntry.COLUMN_NAME_SURNAME, "Lastname" + (i+1));
            values.put(UserContract.UserEntry.COLUMN_NAME_UNIQUE_ID, "abc"+i);
            test = db.insert(UserContract.UserEntry.TABLE_NAME, null, values);
        }
        for (int i = 100; i < 103; i++) {
            values = new ContentValues();
            values.put(DuelScoreContract.DuelScoreEntry._ID, i);
            values.put(DuelScoreContract.DuelScoreEntry.COLUMN_NAME_SCORE_1, 5);
            values.put(DuelScoreContract.DuelScoreEntry.COLUMN_NAME_SCORE_2, 7);
            values.put(DuelScoreContract.DuelScoreEntry.COLUMN_NAME_DUEL_ID, 85);
            values.put(DuelScoreContract.DuelScoreEntry.COLUMN_NAME_USER_ID, 1+i);
            values.put(DuelScoreContract.DuelScoreEntry.COLUMN_NAME_IS_ASSUMPTION, 0);
            test = db.insert(DuelScoreContract.DuelScoreEntry.TABLE_NAME, null, values);
            values = new ContentValues();
            values.put(UserContract.UserEntry._ID, 1+i);
            values.put(UserContract.UserEntry.COLUMN_NAME_NAME, "Name" + (i+1));
            values.put(UserContract.UserEntry.COLUMN_NAME_SURNAME, "Lastname" + (i+1));
            values.put(UserContract.UserEntry.COLUMN_NAME_UNIQUE_ID, "ggg"+i);
            test = db.insert(UserContract.UserEntry.TABLE_NAME, null, values);
        }
        values = new ContentValues();
        values.put(DuelScoreContract.DuelScoreEntry._ID, 99);
        values.put(DuelScoreContract.DuelScoreEntry.COLUMN_NAME_SCORE_1, 5);
        values.put(DuelScoreContract.DuelScoreEntry.COLUMN_NAME_SCORE_2, 5);
        values.put(DuelScoreContract.DuelScoreEntry.COLUMN_NAME_DUEL_ID, 85);
        values.put(DuelScoreContract.DuelScoreEntry.COLUMN_NAME_USER_ID, 1);
        values.put(DuelScoreContract.DuelScoreEntry.COLUMN_NAME_IS_OFFICIAL, 1);
        test = db.insert(DuelScoreContract.DuelScoreEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(UserContract.UserEntry._ID, 1);
        values.put(UserContract.UserEntry.COLUMN_NAME_NAME, "Name " + 1);
        values.put(UserContract.UserEntry.COLUMN_NAME_SURNAME, "Lastname  " + 1);
        values.put(UserContract.UserEntry.COLUMN_NAME_UNIQUE_ID, "Unique");
        test = db.insert(UserContract.UserEntry.TABLE_NAME, null, values);

        values = new ContentValues();
        values.put(DuelScoreContract.DuelScoreEntry._ID, 3);
        values.put(DuelScoreContract.DuelScoreEntry.COLUMN_NAME_SCORE_1, 101);
        values.put(DuelScoreContract.DuelScoreEntry.COLUMN_NAME_SCORE_2, 85);
        values.put(DuelScoreContract.DuelScoreEntry.COLUMN_NAME_DUEL_ID, 33);
        values.put(DuelScoreContract.DuelScoreEntry.COLUMN_NAME_USER_ID, 1);
        values.put(DuelScoreContract.DuelScoreEntry.COLUMN_NAME_IS_ASSUMPTION, 0);
        test = db.insert(DuelScoreContract.DuelScoreEntry.TABLE_NAME, null, values);

        for (int i = 10; i < 20; i++) {
            values = new ContentValues();
            values.put(CompetitorContract.CompetitorEntry._ID, i);
            values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_NAME, "Ferovac" + (i-9));
            values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_SURNAME, "Prezime" + (i-9));
            values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_IS_PERSON, 1);
            values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_GROUP_COMPETITOR_ID, i/15+1);
            values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_COMPETITION_ID, 7);
            values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_FACULTY_ID, 1);
            values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_IS_DISQUALIFIED, 0);
            test = db.insert(CompetitorContract.CompetitorEntry.TABLE_NAME, null, values);
        }
        for (int i = 20; i < 29; i++) {
            values = new ContentValues();
            values.put(CompetitorContract.CompetitorEntry._ID, i);
            values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_NAME, "Fesbovac" + (i-19));
            values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_SURNAME, "Prezime" + (i-19));
            values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_IS_PERSON, 1);
            values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_GROUP_COMPETITOR_ID, i/25+100);
            values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_COMPETITION_ID, 7);
            values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_FACULTY_ID, 2);
            values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_IS_DISQUALIFIED, 0);
            test = db.insert(CompetitorContract.CompetitorEntry.TABLE_NAME, null, values);
        }
        values = new ContentValues();
        values.put(CompetitorContract.CompetitorEntry._ID, 1);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_NAME, "FER 1");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_COMPETITION_ID, 7);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_IS_PERSON, 0);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_FACULTY_ID, 1);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_IS_DISQUALIFIED, 0);
        test = db.insert(CompetitorContract.CompetitorEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CompetitorContract.CompetitorEntry._ID, 2);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_NAME, "FER 2");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_COMPETITION_ID, 7);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_IS_PERSON, 0);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_FACULTY_ID, 1);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_IS_DISQUALIFIED, 0);
        test = db.insert(CompetitorContract.CompetitorEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CompetitorContract.CompetitorEntry._ID, 100);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_NAME, "FESB 1");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_COMPETITION_ID, 7);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_IS_PERSON, 0);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_FACULTY_ID, 2);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_IS_DISQUALIFIED, 0);
        test = db.insert(CompetitorContract.CompetitorEntry.TABLE_NAME, null, values);
        values.put(CompetitorContract.CompetitorEntry._ID, 101);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_NAME, "FESB 2");
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_COMPETITION_ID, 7);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_IS_PERSON, 0);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_FACULTY_ID, 2);
        values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_IS_DISQUALIFIED, 0);
        test = db.insert(CompetitorContract.CompetitorEntry.TABLE_NAME, null, values);

        for (int i = 10; i < 29; i++) {
            values = new ContentValues();
            values.put(CompetitionScoreContract.CompetitionScoreEntry._ID, i);
            values.put(CompetitionScoreContract.CompetitionScoreEntry.COLUMN_NAME_RESULT, i*2);
            values.put(CompetitionScoreContract.CompetitionScoreEntry.COLUMN_NAME_COMPETITION_ID, 7);
            values.put(CompetitionScoreContract.CompetitionScoreEntry.COLUMN_NAME_USER_ID, 1);
            values.put(CompetitionScoreContract.CompetitionScoreEntry.COLUMN_NAME_COMPETITOR_ID, i);
            test = db.insert(CompetitionScoreContract.CompetitionScoreEntry.TABLE_NAME, null, values);
        }
        values = new ContentValues();
        values.put(CompetitionScoreContract.CompetitionScoreEntry._ID, 1);
        values.put(CompetitionScoreContract.CompetitionScoreEntry.COLUMN_NAME_RESULT, 100);
        values.put(CompetitionScoreContract.CompetitionScoreEntry.COLUMN_NAME_COMPETITION_ID, 7);
        values.put(CompetitionScoreContract.CompetitionScoreEntry.COLUMN_NAME_USER_ID, 1);
        values.put(CompetitionScoreContract.CompetitionScoreEntry.COLUMN_NAME_COMPETITOR_ID, 1);
        test = db.insert(CompetitionScoreContract.CompetitionScoreEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(CompetitionScoreContract.CompetitionScoreEntry._ID, 2);
        values.put(CompetitionScoreContract.CompetitionScoreEntry.COLUMN_NAME_RESULT, 100);
        values.put(CompetitionScoreContract.CompetitionScoreEntry.COLUMN_NAME_COMPETITION_ID, 7);
        values.put(CompetitionScoreContract.CompetitionScoreEntry.COLUMN_NAME_USER_ID, 1);
        values.put(CompetitionScoreContract.CompetitionScoreEntry.COLUMN_NAME_COMPETITOR_ID, 100);
        test = db.insert(CompetitionScoreContract.CompetitionScoreEntry.TABLE_NAME, null, values);


        values = new ContentValues();
        values.put(FacultyContract.FacultyEntry._ID, 1);
        values.put(FacultyContract.FacultyEntry.COLUMN_NAME_NAME, "FER");
        values.put(FacultyContract.FacultyEntry.COLUMN_NAME_NICK, "nadimak");
        test = db.insert(FacultyContract.FacultyEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(FacultyContract.FacultyEntry._ID, 2);
        values.put(FacultyContract.FacultyEntry.COLUMN_NAME_NAME, "FESB");
        values.put(FacultyContract.FacultyEntry.COLUMN_NAME_NICK, "nadimak");
        test = db.insert(FacultyContract.FacultyEntry.TABLE_NAME, null, values);

        values = new ContentValues();
        values.put(StageContract.StageEntry._ID, 0);
        values.put(StageContract.StageEntry.COLUMN_NAME_NAME, "---");
        test = db.insert(StageContract.StageEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(StageContract.StageEntry._ID, 1);
        values.put(StageContract.StageEntry.COLUMN_NAME_NAME, "Finale");
        test = db.insert(StageContract.StageEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(StageContract.StageEntry._ID, 2);
        values.put(StageContract.StageEntry.COLUMN_NAME_NAME, "Polufinale");
        test = db.insert(StageContract.StageEntry.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(StageContract.StageEntry._ID, 3);
        values.put(StageContract.StageEntry.COLUMN_NAME_NAME, "Četvrtfinale");
        test = db.insert(StageContract.StageEntry.TABLE_NAME, null, values);

        db.close();
    }

    public ArrayList<Event> getAllEvents() {
        ArrayList<Event> listOfEvents = new ArrayList<>();
        listOfEvents.addAll(getAllKnowledgeEvents());
        listOfEvents.addAll(getAllSportEvents());
        return listOfEvents;
    }

    public ArrayList<Event> getAllSportEvents() {
        ArrayList<Event> listOfSportEvents = new ArrayList<>();
        SQLiteDatabase db = null;
        try {
            db = dbHelper.getReadableDatabase();

            //uzmi sve iz duela
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
                    listOfSportEvents.add(event);
                    setResult(event);
                } while (cursor.moveToNext());
            }
            cursor.close();

            //uzmi sve iz competitiona koji su sport
            db = dbHelper.getReadableDatabase(); //inace mi javlja da sam vec zatvorio o,o
            Cursor cursor2 = db.rawQuery(
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
                            " FROM " + CompetitionContract.CompetitionEntry.TABLE_NAME + ", "
                            + CategoryContract.CategoryEntry.TABLE_NAME +
                            " WHERE " + CompetitionContract.CompetitionEntry.TABLE_NAME + "." + CompetitionContract.CompetitionEntry.COLUMN_NAME_CATEGORY_ID +
                            " = "
                            + CategoryContract.CategoryEntry.TABLE_NAME + "." + CategoryContract.CategoryEntry._ID +
                            " AND " + CategoryContract.CategoryEntry.TABLE_NAME + "." + CategoryContract.CategoryEntry.COLUMN_NAME_IS_SPORT + " > 0",
                    null);
            if (cursor2.moveToFirst()) {
                do {
                    listOfSportEvents.add(
                            new CompetitionEvent(
                                    cursor2.getInt(0),
                                    cursor2.getString(1),
                                    DateParserUtil.stringToDate(cursor2.getString(2)),
                                    DateParserUtil.stringToDate(cursor2.getString(3)),
                                    cursor2.getInt(4) > 0
                            ));
                } while (cursor2.moveToNext());
            }
            cursor2.close();
        } catch (Exception exc) {
            Logger.LogException(exc);
        } finally {
            if (db != null)
                db.close();
        }
        return listOfSportEvents;
    }

    public void setResult(DuelEvent event) {
        SqlDuelScoreRepository duelScoreRepo = new SqlDuelScoreRepository(context);
        event.setResult(duelScoreRepo.getScore(event.getId()));
        duelScoreRepo.close();
    }

    public ArrayList<Event> getAllKnowledgeEvents() {
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
                            + CategoryContract.CategoryEntry.TABLE_NAME + "." + CategoryContract.CategoryEntry._ID +
                            " AND " + CategoryContract.CategoryEntry.TABLE_NAME + "." + CategoryContract.CategoryEntry.COLUMN_NAME_IS_SPORT + " < 1",
                    null);
            if (cursor.moveToFirst()) {
                do {
                    listOfCompetitionEvents.add(
                            new CompetitionEvent(
                                    cursor.getInt(0),
                                    cursor.getString(1),
                                    DateParserUtil.stringToDate(cursor.getString(2)),
                                    DateParserUtil.stringToDate(cursor.getString(3)),
                                    cursor.getInt(4) > 0
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
        return listOfCompetitionEvents;
    }
}

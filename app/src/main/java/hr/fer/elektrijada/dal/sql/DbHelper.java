package hr.fer.elektrijada.dal.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import hr.fer.elektrijada.dal.sql.category.CategoryContract;
import hr.fer.elektrijada.dal.sql.competition.CompetitionContract;
import hr.fer.elektrijada.dal.sql.competitionscore.CompetitionScoreContract;
import hr.fer.elektrijada.dal.sql.competitor.CompetitorContract;
import hr.fer.elektrijada.dal.sql.duel.DuelContract;
import hr.fer.elektrijada.dal.sql.duelscore.DuelScoreContract;
import hr.fer.elektrijada.dal.sql.faculty.FacultyContract;
import hr.fer.elektrijada.dal.sql.news.NewsContract;
import hr.fer.elektrijada.dal.sql.stage.StageContract;
import hr.fer.elektrijada.dal.sql.user.UserContract;

/**
 * Created by Sebastian Glad
 */
public class DbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "elektrijada.db";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Boris: ovdje dodati sql za ostale tablice
        db.execSQL(NewsContract.SQL_CREATE_NEWS);
        db.execSQL(DuelContract.SQL_CREATE_DUEL);
        db.execSQL(DuelScoreContract.SQL_CREATE_DUEL_SCORE);
        db.execSQL(CompetitorContract.SQL_CREATE_COMPETITOR);
        db.execSQL(CategoryContract.SQL_CREATE_CATEGORY);
        db.execSQL(CompetitionContract.SQL_CREATE_COMPETITION);
        db.execSQL(CompetitionScoreContract.SQL_CREATE_COMPETITION_SCORE);
        db.execSQL(FacultyContract.SQL_CREATE_FACULTY);
        db.execSQL(StageContract.SQL_CREATE_STAGE);
        db.execSQL(UserContract.SQL_CREATE_USER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Boris: ovdje dodati sql za ostale tablice
        db.execSQL(NewsContract.SQL_DELETE_NEWS);
        db.execSQL(DuelContract.SQL_DELETE_DUEL);
        db.execSQL(DuelScoreContract.SQL_DELETE_DUEL_SCORE);
        db.execSQL(CompetitorContract.SQL_DELETE_COMPETITOR);
        db.execSQL(CategoryContract.SQL_DELETE_CATEGORY);
        db.execSQL(CompetitionContract.SQL_DELETE_COMPETITION);
        db.execSQL(CompetitionScoreContract.SQL_DELETE_COMPETITION_SCORE);
        db.execSQL(FacultyContract.SQL_DELETE_FACULTY);
        db.execSQL(StageContract.SQL_DELETE_STAGE);
        db.execSQL(UserContract.SQL_DELETE_USER);


        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}

package hr.fer.elektrijada.dal.sql.competitor;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import hr.fer.elektrijada.dal.sql.DbHelper;
import hr.fer.elektrijada.dal.sql.category.CategoryContract;
import hr.fer.elektrijada.dal.sql.competition.CompetitionFromDb;
import hr.fer.elektrijada.dal.sql.competition.SqlCompetitionRepository;
import hr.fer.elektrijada.dal.sql.faculty.FacultyFromDb;
import hr.fer.elektrijada.dal.sql.faculty.SqlFacultyRepository;
import hr.fer.elektrijada.dal.sql.stage.StageContract;
import hr.fer.elektrijada.dal.sql.stage.StageFromDb;
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

    public void fixId(CompetitorFromDb competitor) {
        if(competitor == null) return;
        SQLiteDatabase db = null;
        try {
            db = dbHelper.getReadableDatabase();

            Cursor cursor;
            if(competitor.getSureName() == null) {
                cursor = db.rawQuery("SELECT "+ CompetitorContract.CompetitorEntry._ID+" FROM " + CompetitorContract.CompetitorEntry.TABLE_NAME
                        + " WHERE " + CompetitorContract.CompetitorEntry.COLUMN_NAME_NAME +"='"+competitor.getName()+"'", null);
            } else {
                cursor = db.rawQuery("SELECT "+ CompetitorContract.CompetitorEntry._ID+" FROM " + CompetitorContract.CompetitorEntry.TABLE_NAME
                        + " WHERE " + CompetitorContract.CompetitorEntry.COLUMN_NAME_NAME +"='"+competitor.getName()+"'"
                        + " AND " + CompetitorContract.CompetitorEntry.COLUMN_NAME_SURNAME +"='"+competitor.getSureName()+"'", null);
            }

            if (cursor != null) {
                cursor.moveToFirst();
                competitor.setId(cursor.getInt(0));
                cursor.close();
            }

        } catch (Exception exc) {
            //TO DO: Call Logger.ShowError;
        } finally {
            if (db != null)
                db.close();
        }
    }

    public ArrayList<CompetitorFromDb> getAllCompetitors(){
        ArrayList<CompetitorFromDb> list = new ArrayList<>();
        SQLiteDatabase db = null;
        try {
            db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM " + CompetitorContract.CompetitorEntry.TABLE_NAME, null);
            if (cursor.moveToFirst()) {
                do {
                    list.add(new CompetitorFromDb(
                            cursor.getInt(CompetitorContract.getColumnPos(CompetitorContract.CompetitorEntry._ID)),
                            cursor.getString(CompetitorContract.getColumnPos(CompetitorContract.CompetitorEntry.COLUMN_NAME_NAME)),
                            cursor.getString(CompetitorContract.getColumnPos(CompetitorContract.CompetitorEntry.COLUMN_NAME_SURNAME)),
                            cursor.getInt(CompetitorContract.getColumnPos(CompetitorContract.CompetitorEntry.COLUMN_NAME_IS_PERSON)) > 0,
                            getCompetitor(cursor.getInt(CompetitorContract.getColumnPos(CompetitorContract.CompetitorEntry.COLUMN_NAME_GROUP_COMPETITOR_ID))),
                            getFaculty(cursor.getInt(CompetitorContract.getColumnPos(CompetitorContract.CompetitorEntry.COLUMN_NAME_FACULTY_ID))),
                            getCompetition(cursor.getInt(CompetitorContract.getColumnPos(CompetitorContract.CompetitorEntry.COLUMN_NAME_COMPETITION_ID))),
                            -1, false
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
        return list;
    }

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
            if(cursor != null) {
                cursor.close();
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

    //studenti tog fakulteta
    public List<CompetitorFromDb> getStudents(int facultyId) {
        List<CompetitorFromDb> students = new ArrayList<>();
        SQLiteDatabase db = null;
        try {
            db = dbHelper.getReadableDatabase();

            Cursor cursor = db.rawQuery("SELECT * FROM " + CompetitorContract.CompetitorEntry.TABLE_NAME
                            + " WHERE " + CompetitorContract.CompetitorEntry.COLUMN_NAME_FACULTY_ID + " = " + facultyId
                            + " AND " + CompetitorContract.CompetitorEntry.COLUMN_NAME_IS_PERSON + " > 0",
                    null);

            FacultyFromDb faculty = getFaculty(facultyId);

            if (cursor != null) {
                while(cursor.moveToNext()) {
                    students.add(new CompetitorFromDb(
                            cursor.getInt(CompetitorContract.getColumnPos(CompetitorContract.CompetitorEntry._ID)),
                            cursor.getString(CompetitorContract.getColumnPos(CompetitorContract.CompetitorEntry.COLUMN_NAME_NAME)),
                            cursor.getString(CompetitorContract.getColumnPos(CompetitorContract.CompetitorEntry.COLUMN_NAME_SURNAME)),
                            true,
                            getCompetitor(cursor.getInt(CompetitorContract.getColumnPos(CompetitorContract.CompetitorEntry.COLUMN_NAME_GROUP_COMPETITOR_ID))),
                            faculty,
                            getCompetition(cursor.getInt(CompetitorContract.getColumnPos(CompetitorContract.CompetitorEntry.COLUMN_NAME_COMPETITION_ID))),
                            cursor.getInt(CompetitorContract.getColumnPos(CompetitorContract.CompetitorEntry.COLUMN_NAME_ORDINAL_NUM)),
                            cursor.getInt(CompetitorContract.getColumnPos(CompetitorContract.CompetitorEntry.COLUMN_NAME_IS_DISQUALIFIED)) > 0)
                    );
                }
                cursor.close();
            }
        } catch (Exception exc) {
            //TO DO: Call Logger.ShowError;
        } finally {
            if (db != null)
                db.close();
        }
        return students;
    }

    //timovi si oni koji imaju isPerson = false
    public List<CompetitorFromDb> getAllTeams(int competitionId) {
        List<CompetitorFromDb> teams = new ArrayList<>();
        SQLiteDatabase db = null;
        try {
            db = dbHelper.getReadableDatabase();

            Cursor cursor = db.rawQuery("SELECT * FROM " + CompetitorContract.CompetitorEntry.TABLE_NAME
                                        + " WHERE " + CompetitorContract.CompetitorEntry.COLUMN_NAME_COMPETITION_ID + " = " + competitionId,
                    null);

            CompetitionFromDb competition = getCompetition(competitionId);

            if (cursor != null) {
                while(cursor.moveToNext()) {
                    if(cursor.getInt(CompetitorContract.getColumnPos(CompetitorContract.CompetitorEntry.COLUMN_NAME_IS_PERSON)) > 0) continue;
                    teams.add(new CompetitorFromDb(
                            cursor.getInt(CompetitorContract.getColumnPos(CompetitorContract.CompetitorEntry._ID)),
                            cursor.getString(CompetitorContract.getColumnPos(CompetitorContract.CompetitorEntry.COLUMN_NAME_NAME)),
                            cursor.getString(CompetitorContract.getColumnPos(CompetitorContract.CompetitorEntry.COLUMN_NAME_SURNAME)),
                            false,
                            null, //jer je u pitanju tim
                            getFaculty(cursor.getInt(CompetitorContract.getColumnPos(CompetitorContract.CompetitorEntry.COLUMN_NAME_FACULTY_ID))),
                            competition,
                            cursor.getInt(CompetitorContract.getColumnPos(CompetitorContract.CompetitorEntry.COLUMN_NAME_ORDINAL_NUM)),
                            cursor.getInt(CompetitorContract.getColumnPos(CompetitorContract.CompetitorEntry.COLUMN_NAME_IS_DISQUALIFIED)) > 0)
                    );
                }
                cursor.close();
            }
        } catch (Exception exc) {
            //TO DO: Call Logger.ShowError;
        } finally {
            if (db != null)
                db.close();
        }
        return teams;
    }

    //ovo su svi studenti u tom timu, to su oni koji imaju isPerson = true
    public List<CompetitorFromDb> getTeamsContestants(int groupCompetitorId, int competitionId) {
        List<CompetitorFromDb> teams = new ArrayList<>();
        SQLiteDatabase db = null;
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
                    CompetitorContract.CompetitorEntry.COLUMN_NAME_IS_DISQUALIFIED,
            };

            Cursor cursor = db.query(
                    CompetitorContract.CompetitorEntry.TABLE_NAME,
                    projection,
                    CompetitorContract.CompetitorEntry.COLUMN_NAME_COMPETITION_ID + "=? AND "
                            + CompetitorContract.CompetitorEntry.COLUMN_NAME_GROUP_COMPETITOR_ID + "=? AND "
                            + CompetitorContract.CompetitorEntry.COLUMN_NAME_IS_PERSON + "=?",
                    new String[]{String.valueOf(competitionId), String.valueOf(groupCompetitorId), String.valueOf(true)},
                    null,
                    null,
                    null
            );

            CompetitionFromDb competition = getCompetition(competitionId);
            CompetitorFromDb team = getCompetitor(groupCompetitorId);

            if (cursor != null) {
                while(cursor.moveToNext()) {
                    teams.add(new CompetitorFromDb(
                            cursor.getInt(CompetitorContract.getColumnPos(CompetitorContract.CompetitorEntry._ID)),
                            cursor.getString(CompetitorContract.getColumnPos(CompetitorContract.CompetitorEntry.COLUMN_NAME_NAME)),
                            cursor.getString(CompetitorContract.getColumnPos(CompetitorContract.CompetitorEntry.COLUMN_NAME_SURNAME)),
                            cursor.getInt(CompetitorContract.getColumnPos(CompetitorContract.CompetitorEntry.COLUMN_NAME_IS_PERSON)) > 0,
                            team,
                            getFaculty(cursor.getInt(CompetitorContract.getColumnPos(CompetitorContract.CompetitorEntry.COLUMN_NAME_FACULTY_ID))),
                            competition,
                            cursor.getInt(CompetitorContract.getColumnPos(CompetitorContract.CompetitorEntry.COLUMN_NAME_ORDINAL_NUM)),
                            cursor.getInt(CompetitorContract.getColumnPos(CompetitorContract.CompetitorEntry.COLUMN_NAME_IS_DISQUALIFIED)) > 0)
                    );
                }
                cursor.close();
            }
        } catch (Exception exc) {
            //TO DO: Call Logger.ShowError;
        } finally {
            if (db != null)
                db.close();
        }
        return teams;
    }

    /**
     * na prvu bi izgledalo bolje da je id kljuc, ali ovakva struktura mi bolje pase za spinner
     *
     * String je name, Integer je ID
     * @return  mapa u kojoj su kljucevi IMENA, a ID vrijednosti
     */
    public HashMap<String, Integer> getMapOfCompetitors() {
        HashMap<String, Integer> allCompetitors = new HashMap<>();
        SQLiteDatabase db = null;
        try {
            db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(
                    "SELECT " + CompetitorContract.CompetitorEntry._ID + ", " +
                    CompetitorContract.CompetitorEntry.COLUMN_NAME_NAME +
                    " FROM " + CompetitorContract.CompetitorEntry.TABLE_NAME +
                    " WHERE " + CompetitorContract.CompetitorEntry.COLUMN_NAME_IS_PERSON + " < 1" +
                    " AND " + CompetitorContract.CompetitorEntry.COLUMN_NAME_COMPETITION_ID + " IS NULL",
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


    public void deleteCompetitor(CompetitorFromDb competitor) {
        SQLiteDatabase db = null;
        try {
            db = dbHelper.getWritableDatabase();
            db.delete(CompetitorContract.CompetitorEntry.TABLE_NAME,
                        CompetitorContract.CompetitorEntry._ID + "=?",
                        new String[]{String.valueOf(competitor.getId())});
        } catch (Exception exc) {
            //TO DO: Call Logger.ShowError;
        } finally {
            if (db != null)
                db.close();
        }
    }

    public void updateCompetitor(CompetitorFromDb c) {
        SQLiteDatabase db = null;
        try {
            db = dbHelper.getReadableDatabase();
            ContentValues values = new ContentValues();
            values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_NAME, c.getName());
            values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_SURNAME, c.getSureName());
            values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_IS_PERSON, c.isPerson());
            if(c.getGroupCompetitor() != null) {
                values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_GROUP_COMPETITOR_ID, c.getGroupCompetitor().getId());
            }
            values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_FACULTY_ID, c.getFaculty().getId());
            if(c.getCompetition() != null) {
                values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_COMPETITION_ID, c.getCompetition().getId());
            }
            values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_ORDINAL_NUM, c.getOrdinalNum());
            values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_IS_DISQUALIFIED, c.isDisqualified());

            db.update(CompetitorContract.CompetitorEntry.TABLE_NAME,
                        values,
                        CompetitorContract.CompetitorEntry._ID + "=?",
                        new String[]{String.valueOf(c.getId())});
        } catch (Exception exc) {
            //TO DO: Call Logger.ShowError;
        } finally {
            if (db != null)
                db.close();
        }
    }

    public int createNewCompetitor(CompetitorFromDb c) {
        SQLiteDatabase db = null;
        try {
            db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_NAME, c.getName());
            values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_SURNAME, c.getSureName());
            values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_IS_PERSON, c.isPerson());
            if(c.getGroupCompetitor() != null) {
                values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_GROUP_COMPETITOR_ID, c.getGroupCompetitor().getId());
            }
            values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_FACULTY_ID, c.getFaculty().getId());
            if(c.getCompetition() != null) {
                values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_COMPETITION_ID, c.getCompetition().getId());
            }
            values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_ORDINAL_NUM, c.getOrdinalNum());
            values.put(CompetitorContract.CompetitorEntry.COLUMN_NAME_IS_DISQUALIFIED, c.isDisqualified());

            return (int) db.insert(CompetitorContract.CompetitorEntry.TABLE_NAME, null, values);
        } catch (Exception exc) {
            //TO DO: Call Logger.ShowError;
            return -1;
        } finally {
            if (db != null)
                db.close();
        }
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

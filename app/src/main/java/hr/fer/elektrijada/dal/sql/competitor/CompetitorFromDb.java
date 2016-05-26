package hr.fer.elektrijada.dal.sql.competitor;

import java.io.Serializable;

import hr.fer.elektrijada.activities.bluetooth.IComparable;
import hr.fer.elektrijada.dal.sql.competition.CompetitionFromDb;
import hr.fer.elektrijada.dal.sql.faculty.FacultyFromDb;

/**
 * Created by Ivica Brebrek
 */
public class CompetitorFromDb implements Serializable, IComparable<CompetitorFromDb> {
    private static final long serialVersionUID = 1L;
    private int id;
    private String name;
    private String sureName;
    private boolean isPerson;
    /** playing for this team, can be null */
    private CompetitorFromDb groupCompetitor;
    private FacultyFromDb faculty;
    /** competitor is in competition, can be null */
    private CompetitionFromDb competition;
    /** no idea what this is */
    private int ordinalNum;
    private boolean isDisqualified;

    public CompetitorFromDb(int id, String name, String sureName, boolean isPerson, CompetitorFromDb groupCompetitor, FacultyFromDb faculty, CompetitionFromDb competition, int ordinalNum, boolean isDisqualified) {
        if(name == null || faculty == null) {
            throw new IllegalArgumentException("Name and faculty can not be null");
        }
        this.id = id;
        this.name = name;
        this.sureName = sureName;
        this.isPerson = isPerson;
        this.groupCompetitor = groupCompetitor;
        this.faculty = faculty;
        this.competition = competition;
        this.ordinalNum = ordinalNum;
        this.isDisqualified = isDisqualified;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSureName() {
        return sureName;
    }

    public void setSureName(String sureName) {
        this.sureName = sureName;
    }

    public boolean isPerson() {
        return isPerson;
    }

    public void setPerson(boolean person) {
        isPerson = person;
    }

    public CompetitorFromDb getGroupCompetitor() {
        return groupCompetitor;
    }

    public void setGroupCompetitor(CompetitorFromDb groupCompetitor) {
        this.groupCompetitor = groupCompetitor;
    }

    public FacultyFromDb getFaculty() {
        return faculty;
    }

    public void setFaculty(FacultyFromDb faculty) {
        this.faculty = faculty;
    }

    public CompetitionFromDb getCompetition() {
        return competition;
    }

    public void setCompetition(CompetitionFromDb competition) {
        this.competition = competition;
    }

    public int getOrdinalNum() {
        return ordinalNum;
    }

    public void setOrdinalNum(int ordinalNum) {
        this.ordinalNum = ordinalNum;
    }

    public boolean isDisqualified() {
        return isDisqualified;
    }

    public void setDisqualified(boolean disqualified) {
        isDisqualified = disqualified;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CompetitorFromDb)) return false;

        CompetitorFromDb other = (CompetitorFromDb) o;

        if(name == null) {
            if(other.name != null) return false;
        } else if(!name.equals(other.name)) return false;
        if(sureName == null) {
            if(other.sureName != null) return false;
        } else if(!sureName.equals(other.sureName)) return false;
        if(groupCompetitor == null) {
            if(other.groupCompetitor != null) return false;
        } else if(!groupCompetitor.equals(other.groupCompetitor)) return false;
        if(faculty == null) {
            if(other.faculty != null) return false;
        } else if(!faculty.equals(other.faculty)) return false;
        if(competition == null) {
            if(other.competition != null) return false;
        } else if(!competition.equals(other.competition)) return false;
        return isPerson == other.isPerson;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + (sureName != null ? sureName.hashCode() : 0);
        result = 31 * result + faculty.hashCode();
        return result;
    }

    @Override
    public String toString() {
        if(sureName == null) return name;
        else return name + " " + sureName;
    }

    @Override
    public boolean detailsSame(CompetitorFromDb other) {
        return true; //jer se sve gleda u equals
    }
}

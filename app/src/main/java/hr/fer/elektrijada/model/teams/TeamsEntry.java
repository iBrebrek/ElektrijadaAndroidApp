package hr.fer.elektrijada.model.teams;

import android.net.Uri;

import java.io.Serializable;

/**
 * Created by Zvone on 14.11.2015..
 */
public class TeamsEntry implements Serializable {

    private int id;
    /**
     * Ime ekipe
     */
    private String name;
    /**
     * ??
     */
    private String section;
    /**
     * Id grupe
     */
    private int groupCompetitorId;
    /**
     * Id fakulteta
     */
    private int facultyId;
    /**
     * Id natjecanja
     */
    private int competitionId;


    public TeamsEntry(int id, String name, String section, int groupCompetitorId, int facultyId, int competitionId){
        this.id = id;
        this.name = name;
        this.section = section;
        this.groupCompetitorId = groupCompetitorId;
        this.facultyId = facultyId;
        this.competitionId = competitionId;
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

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public int getGroupCompetitorId() {
        return groupCompetitorId;
    }

    public void setGroupCompetitorId(int groupCompetitorId) {
        this.groupCompetitorId = groupCompetitorId;
    }

    public int getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(int facultyId) {
        this.facultyId = facultyId;
    }

    public int getCompetitionId() {
        return competitionId;
    }

    public void setCompetitionId(int competitionId) {
        this.competitionId = competitionId;
    }
}

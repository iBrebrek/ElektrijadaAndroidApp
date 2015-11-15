package hr.fer.elektrijada.model.competitions;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Zvone
 */
public class CompetitionsEntry implements Serializable{
    private int id;
    /**
     * Početak meča
     */
    private Date timeFrom;
    /**
     * Kraj meča
     */
    private Date timeTo;
    /**
     * Koja je kategorija
     */
    private int categoryId;
    /**
     * Mjesto gdje se održava natjecanje
     */
    private String location;
    /**
     * Jesu li podaci službeni (false ako jesu)
     */
    private boolean isAssumption;

    public CompetitionsEntry(int id, boolean isAssumption, String location, int categoryId, Date timeTo, Date timeFrom) {
        this.id = id;
        this.isAssumption = isAssumption;
        this.location = location;
        this.categoryId = categoryId;
        this.timeTo = timeTo;
        this.timeFrom = timeFrom;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean isAssumption() {
        return isAssumption;
    }

    public void setIsAssumption(boolean isAssumption) {
        this.isAssumption = isAssumption;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public Date getTimeTo() {
        return timeTo;
    }

    public void setTimeTo(Date timeTo) {
        this.timeTo = timeTo;
    }

    public Date getTimeFrom() {
        return timeFrom;
    }

    public void setTimeFrom(Date timeFrom) {
        this.timeFrom = timeFrom;
    }
}

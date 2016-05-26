package hr.fer.elektrijada.dal.sql.competition;

import java.io.Serializable;

import hr.fer.elektrijada.activities.bluetooth.IComparable;
import hr.fer.elektrijada.activities.bluetooth.IDetails;
import hr.fer.elektrijada.dal.sql.category.CategoryFromDb;
import hr.fer.elektrijada.util.DateParserUtil;

/**
 * Created by Ivica Brebrek
 */
public class CompetitionFromDb implements Serializable, IComparable<CompetitionFromDb>, IDetails {
    private static final long serialVersionUID = 1L;
    private int id;
    private String timeFrom;
    private String timeTo;
    private CategoryFromDb category;
    private String location;
    private boolean isAssumption;

    /*
    bez id-a
     */
    public CompetitionFromDb(String timeFrom, String timeTo, CategoryFromDb category, String location, boolean isAssumption) {
        if(timeTo != null && DateParserUtil.stringToDate(timeFrom)
                .compareTo(DateParserUtil.stringToDate(timeTo)) == 1) {
            throw new IllegalArgumentException("Kraj mora biti nakon početka!");
        }
        this.timeFrom = timeFrom;
        this.timeTo = timeTo;
        this.category = category;
        this.location = location;
        this.isAssumption = isAssumption;
    }

    /*
    konstruktor sa svim podatcima
     */
    public CompetitionFromDb(int id, String timeFrom, String timeTo, CategoryFromDb category, String location, boolean isAssumption) {
        this(timeFrom, timeTo, category, location, isAssumption);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTimeFrom() {
        return timeFrom;
    }

    public void setTimeFrom(String timeFrom) {
        this.timeFrom = timeFrom;
    }

    public String getTimeTo() {
        return timeTo;
    }

    public void setTimeTo(String timeTo) {
        this.timeTo = timeTo;
    }

    public CategoryFromDb getCategory() {
        return category;
    }

    public void setCategory(CategoryFromDb category) {
        this.category = category;
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

    @Override
    public boolean equals(Object o) {
        if (o instanceof CompetitionFromDb) {
            CompetitionFromDb other = (CompetitionFromDb) o;
             return category.equals(other.category);
        }
        return false;
    }

    @Override
    public boolean detailsSame(CompetitionFromDb other) {
        if(timeFrom == null) {
            if(other.timeFrom != null) return false;
        } else if(!timeFrom.equals(other.timeFrom)) return false;
        if(timeTo == null) {
            if(other.timeTo != null) return false;
        }else if(!timeTo.equals(other.timeTo)) return false;
        if(location == null) {
            if(other.location != null) return false;
        }else if(!location.equals(other.location)) return false;
        return true;
    }

    @Override
    public String info() {
        return "Kategorija: " + category.getName();
    }

    @Override
    public String details() {
        StringBuilder sb = new StringBuilder();
        sb.append("lokacija: ").append(location).append("\n")
                .append(timeFrom).append(" - ").append(timeTo);
        return sb.toString();
    }
}

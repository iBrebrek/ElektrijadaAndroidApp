package hr.fer.elektrijada.dal.sql.competition;

import hr.fer.elektrijada.dal.sql.category.CategoryFromDb;
import hr.fer.elektrijada.util.DateParserUtil;

/**
 * Created by Ivica Brebrek
 */
public class CompetitionFromDb {
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
        if (o instanceof CompetitionFromDb && ((CompetitionFromDb) o).id == id) {
            return true;
        }
        return false;
    }
}

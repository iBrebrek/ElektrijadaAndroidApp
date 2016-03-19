package hr.fer.elektrijada.dal.sql.duel;

import hr.fer.elektrijada.util.DateParserUtil;

/**
 * Created by Ivica Brebrek
 */
public class DuelFromDb {
    private int id;
    private String timeFrom;
    private String timeTo;
    private int categoryId;
    private int firstId;
    private int secondId;
    private int stageId;
    private String location;
    private boolean isAssumption;
    private String section;

    /*
    bez id, stageId, section
     */
    public DuelFromDb(String timeFrom, String timeTo, int categoryId, int firstId, int secondId, String location, boolean isAssumption) {
        if (timeTo != null && DateParserUtil.stringToDate(timeFrom)
                .compareTo(DateParserUtil.stringToDate(timeTo)) == 1) {
            throw new IllegalArgumentException("Kraj mora biti nakon početka!");
        }
        this.timeFrom = timeFrom;
        this.timeTo = timeTo;
        this.categoryId = categoryId;
        this.firstId = firstId;
        this.secondId = secondId;
        this.location = location;
        this.isAssumption = isAssumption;
    }

    /*
    bez id, section
     */
    public DuelFromDb(String timeFrom, String timeTo, int categoryId, int firstId, int secondId, int stageId, String location, boolean isAssumption) {
        this(timeFrom, timeTo, categoryId, firstId, secondId, location, isAssumption);
        this.stageId = stageId;
    }

    /*
    konstruktor sa svim podatcima
     */
    public DuelFromDb(int id, String timeFrom, String timeTo, int categoryId, int firstId,
                      int secondId, int stageId, String location, boolean isAssumption, String section) {
        this(timeFrom, timeTo, categoryId, firstId, secondId, location, isAssumption);
        this.id = id;
        this.stageId = stageId;
        this.section = section;
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

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getFirstId() {
        return firstId;
    }

    public void setFirstId(int firstId) {
        this.firstId = firstId;
    }

    public int getSecondId() {
        return secondId;
    }

    public void setSecondId(int secondId) {
        this.secondId = secondId;
    }

    public int getStageId() {
        return stageId;
    }

    public void setStageId(int stageId) {
        this.stageId = stageId;
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

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof DuelFromDb && ((DuelFromDb) o).id == id) {
            return true;
        }
        return false;
    }
}

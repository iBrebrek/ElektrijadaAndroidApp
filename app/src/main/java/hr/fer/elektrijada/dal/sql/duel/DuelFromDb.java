package hr.fer.elektrijada.dal.sql.duel;

import android.content.Context;

import hr.fer.elektrijada.dal.sql.category.CategoryFromDb;
import hr.fer.elektrijada.dal.sql.competitor.CompetitorFromDb;
import hr.fer.elektrijada.dal.sql.duelscore.SqlDuelScoreRepository;
import hr.fer.elektrijada.dal.sql.stage.StageFromDb;
import hr.fer.elektrijada.model.score.DuelScore;
import hr.fer.elektrijada.util.DateParserUtil;

/**
 * Created by Ivica Brebrek
 */
public class DuelFromDb {
    private int id;
    private String timeFrom;
    private String timeTo;
    private CategoryFromDb category;
    private CompetitorFromDb firstCompetitor;
    private CompetitorFromDb secondCompetitor;
    private StageFromDb stage;
    private String location;
    private boolean isAssumption;
    private String section;

    /*
    ova varijabla će biti inicijalizirana prvi put kada se pozove njihov geter
     */
    private DuelScore duelScore = null;

    /*
    bez id, stageId, section
     */
    public DuelFromDb(String timeFrom, String timeTo, CategoryFromDb category, CompetitorFromDb firstCompetitor, CompetitorFromDb secondCompetitor, String location, boolean isAssumption) {
        if (timeTo != null && DateParserUtil.stringToDate(timeFrom)
                .compareTo(DateParserUtil.stringToDate(timeTo)) == 1) {
            throw new IllegalArgumentException("Kraj mora biti nakon početka!");
        }
        if (category == null || firstCompetitor == null || secondCompetitor == null) {
            throw new IllegalArgumentException("Category and both competitors can not be null");
        }
        this.timeFrom = timeFrom;
        this.timeTo = timeTo;
        this.category = category;
        this.firstCompetitor = firstCompetitor;
        this.secondCompetitor = secondCompetitor;
        this.location = location;
        this.isAssumption = isAssumption;
    }

    /*
    bez id, section
     */
    public DuelFromDb(String timeFrom, String timeTo, CategoryFromDb category, CompetitorFromDb firstCompetitor, CompetitorFromDb secondCompetitor, StageFromDb stage, String location, boolean isAssumption) {
        this(timeFrom, timeTo, category, firstCompetitor, secondCompetitor, location, isAssumption);
        this.stage = stage;
    }

    /*
    konstruktor sa svim podatcima
     */
    public DuelFromDb(int id, String timeFrom, String timeTo, CategoryFromDb category, CompetitorFromDb firstCompetitor,
                      CompetitorFromDb secondCompetitor, StageFromDb stage, String location, boolean isAssumption, String section) {
        this(timeFrom, timeTo, category, firstCompetitor, secondCompetitor, stage, location, isAssumption);
        this.id = id;
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

    public CategoryFromDb getCategory() {
        return category;
    }

    public void setCategory(CategoryFromDb category) {
        this.category = category;
    }

    public CompetitorFromDb getFirstCompetitor() {
        return firstCompetitor;
    }

    public void setFirstId(CompetitorFromDb firstCompetitor) {
        this.firstCompetitor = firstCompetitor;
    }

    public CompetitorFromDb getSecondCompetitor() {
        return secondCompetitor;
    }

    public void setSecondCompetitor(CompetitorFromDb secondCompetitor) {
        this.secondCompetitor = secondCompetitor;
    }

    public StageFromDb getStage() {
        return stage;
    }

    public void setStageId(StageFromDb stage) {
        this.stage = stage;
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



    /*
    za score se vraca String jer je moguce da rezultat nema,
    u tom slucaju se vraca " - "
     */
    public String getFirstComptetitorScore(Context context) {
        if(duelScore == null) {
            SqlDuelScoreRepository duelScoreRepository = new SqlDuelScoreRepository(context);
            duelScore = duelScoreRepository.getScore(id);
            duelScoreRepository.close();
        }
        if(duelScore.isSet()) {
            return String.valueOf(duelScore.getFirstScore());
        } else {
            return " - ";
        }
    }

    public String getSecondComptetitorScore(Context context) {
        if(duelScore == null) {
            SqlDuelScoreRepository duelScoreRepository = new SqlDuelScoreRepository(context);
            duelScore = duelScoreRepository.getScore(id);
            duelScoreRepository.close();
        }
        if(duelScore.isSet()) {
            return String.valueOf(duelScore.getSecondScore());
        } else {
            return " - ";
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof DuelFromDb && ((DuelFromDb) o).id == id) {
            return true;
        }
        return false;
    }
}

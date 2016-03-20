package hr.fer.elektrijada.dal.sql.duel;

import android.content.Context;

import hr.fer.elektrijada.dal.sql.competitor.SqlCompetitorRepository;
import hr.fer.elektrijada.dal.sql.duelscore.SqlDuelScoreRepository;
import hr.fer.elektrijada.dal.sql.stage.SqlStageRepository;
import hr.fer.elektrijada.model.score.DuelScore;
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
    za ovih 5 varijabli uvijek su postavljene defaultne vrijednosti null tj. -1 (ovo promijeniti ako će nekad -1 postat valjan score)

    ove varijable će biti inicijalizirane prvi put kada se pozove njihov geter,
    jer ih nema smisla odma inicilajizirat posto se treba trazit pot drugim tablicama baze
     */
    private String stageName = null;
    private String firstCompetitorName = null;
    private String secondCompetitorName = null;
    private DuelScore duelScore = null;

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

    /*
    za ovih 5 sljedecih getera je potrebno predati kontekst jer pristupaju bazi,
    ako se nade nacin da se iz ovoga razreda dobi kontekst onda prepraviti
     */

    public String getStageName(Context context) {
        if(stageName == null) {
            SqlStageRepository stageRepo = new SqlStageRepository(context);
            stageName = stageRepo.getStage(stageId).getName();
            stageRepo.close();
        }
        return stageName;
    }

    public String getFirstCompetitorName(Context context) {
        if(firstCompetitorName == null) {
            SqlCompetitorRepository competitorRepository = new SqlCompetitorRepository(context);
            firstCompetitorName = competitorRepository.getCompetitorName(firstId);
            competitorRepository.close();
        }
        return firstCompetitorName;
    }

    public String getSecondCompetitorName(Context context) {
        if(secondCompetitorName == null) {
            SqlCompetitorRepository competitorRepository = new SqlCompetitorRepository(context);
            secondCompetitorName = competitorRepository.getCompetitorName(secondId);
            competitorRepository.close();
        }
        return secondCompetitorName;
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

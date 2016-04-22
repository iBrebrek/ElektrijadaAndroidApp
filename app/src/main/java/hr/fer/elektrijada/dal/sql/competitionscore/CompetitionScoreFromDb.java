package hr.fer.elektrijada.dal.sql.competitionscore;

import hr.fer.elektrijada.dal.sql.competition.CompetitionFromDb;
import hr.fer.elektrijada.dal.sql.competitor.CompetitorFromDb;
import hr.fer.elektrijada.dal.sql.user.UserFromDb;

/**
 * Created by Ivica Brebrek
 */
public class CompetitionScoreFromDb {
    private int id;
    private double result;
    private String note;
    private CompetitionFromDb competition;
    private UserFromDb user;
    private CompetitorFromDb competitor;
    //private boolean isAssumption;
    private boolean isOfficial;

    public CompetitionScoreFromDb(int id, double result, String note, CompetitionFromDb competition, UserFromDb user, CompetitorFromDb competitor, boolean isOfficial) {
        if(competition == null || competitor == null || user == null) {
            throw new IllegalArgumentException("Competition, competitor and user can not be null.");
        }
        this.id = id;
        this.result = result;
        this.note = note;
        this.competition = competition;
        this.user = user;
        this.competitor = competitor;
        this.isOfficial = isOfficial;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getResult() {
        return result;
    }

    public void setResult(double result) {
        this.result = result;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public CompetitionFromDb getCompetition() {
        return competition;
    }

    public void setCompetition(CompetitionFromDb competition) {
        this.competition = competition;
    }

    public UserFromDb getUser() {
        return user;
    }

    public void setUser(UserFromDb user) {
        this.user = user;
    }

    public CompetitorFromDb getCompetitor() {
        return competitor;
    }

    public void setCompetitor(CompetitorFromDb competitor) {
        this.competitor = competitor;
    }

    public boolean isOfficial() {
        return isOfficial;
    }

    public void setOfficial(boolean official) {
        isOfficial = official;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CompetitionScoreFromDb)) return false;

        CompetitionScoreFromDb that = (CompetitionScoreFromDb) o;

        if (!competition.equals(that.competition)) return false;
        if (!user.equals(that.user)) return false;
        return competitor.equals(that.competitor);

    }

    @Override
    public int hashCode() {
        int result = competition.hashCode();
        result = 31 * result + user.hashCode();
        result = 31 * result + competitor.hashCode();
        return result;
    }
}

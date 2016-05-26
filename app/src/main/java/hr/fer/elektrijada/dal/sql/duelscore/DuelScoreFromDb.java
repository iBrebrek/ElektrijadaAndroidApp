package hr.fer.elektrijada.dal.sql.duelscore;

import java.io.Serializable;

import hr.fer.elektrijada.activities.bluetooth.IComparable;
import hr.fer.elektrijada.dal.sql.duel.DuelFromDb;
import hr.fer.elektrijada.dal.sql.user.UserFromDb;

/**
 * Created by Ivica Brebrek
 */
public class DuelScoreFromDb implements Serializable, IComparable<DuelScoreFromDb> {
    private static final long serialVersionUID = 1L;
    private int id;
    private double firstScore;
    private double secondScore;
    private DuelFromDb duel;
    private UserFromDb user;
//    private boolean isAssumption;
    private String note;
    private boolean official;

    public DuelScoreFromDb(double firstScore, double secondScore, DuelFromDb duel, UserFromDb user, boolean official) {
        if(duel == null || user == null) {
            throw new IllegalArgumentException("DuelFromDb: Duel and user can not be null.");
        }
        this.firstScore = firstScore;
        this.secondScore = secondScore;
        this.duel = duel;
        this.user = user;
        this.official = official;
    }

    public DuelScoreFromDb(int id, double firstScore, double secondScore, DuelFromDb duel, UserFromDb user, String note, boolean official) {
        this(firstScore, secondScore, duel, user, official);
        this.id = id;
        this.note = note;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getFirstScore() {
        return firstScore;
    }

    public void setFirstScore(double firstScore) {
        this.firstScore = firstScore;
    }

    public double getSecondScore() {
        return secondScore;
    }

    public void setSecondScore(double secondScore) {
        this.secondScore = secondScore;
    }

    public DuelFromDb getDuel() {
        return duel;
    }

    public void setDuel(DuelFromDb duel) {
        this.duel = duel;
    }

    public UserFromDb getUser() {
        return user;
    }

    public void setUser(UserFromDb user) {
        this.user = user;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public boolean isOfficial() {
        return official;
    }

    public void setOfficial(boolean official) {
        this.official = official;
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof DuelScoreFromDb) {
            DuelScoreFromDb other = (DuelScoreFromDb) o;
            if(duel == null) {
                if(other.duel != null) return false;
            } else if(!duel.equals(other.duel)) return false;
            if(user == null) {
                if(other.user != null) return false;
            } else if(!user.equals(other.user)) return false;
            return firstScore == other.firstScore && secondScore == other.secondScore && official == other.official;
        }
        return false;
    }

    @Override
    public boolean detailsSame(DuelScoreFromDb other) {
        return true;
    }
}

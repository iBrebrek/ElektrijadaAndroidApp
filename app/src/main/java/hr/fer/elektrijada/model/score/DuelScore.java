package hr.fer.elektrijada.model.score;

/**
 * Created by Ivica Brebrek
 */
public class DuelScore {
    private boolean isSet;
    private int firstScore;
    private int secondScore;

    //konstruktor u slucaju ako rezultat ne postoji
    public DuelScore() {
        isSet = false;
    }

    public DuelScore(int firstScore, int secondScore) {
        isSet = true;
        this.firstScore = firstScore;
        this.secondScore = secondScore;
    }

    public int getFirstScore() {
        return firstScore;
    }

    public int getSecondScore() {
        return secondScore;
    }

    public boolean isSet() {
        return isSet;
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) return false;
        if (other == this) return true;
        if (!(other instanceof DuelScore)) return false;
        DuelScore otherScore = (DuelScore) other;
        return otherScore.firstScore == this.firstScore
                && otherScore.secondScore == this.secondScore;
    }

    @Override
    public int hashCode() {
        int result = firstScore;
        result = 31 * result + secondScore;
        return result;
    }
}

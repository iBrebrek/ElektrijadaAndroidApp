package hr.fer.elektrijada.model.score;

/**
 * Created by Ivica Brebrek
 */
public class DuelScore {
    private boolean isSet;
    private double firstScore;
    private double secondScore;

    //konstruktor u slucaju ako rezultat ne postoji
    public DuelScore() {
        isSet = false;
    }

    public DuelScore(double firstScore, double secondScore) {
        isSet = true;
        this.firstScore = firstScore;
        this.secondScore = secondScore;
    }

    public double getFirstScore() {
        return firstScore;
    }

    public double getSecondScore() {
        return secondScore;
    }

    public boolean isSet() {
        return isSet;
    }

    @Override
    public String toString() {
        if(isSet()) {
            return roundIt(getFirstScore()) + " : " + roundIt(getSecondScore());
        } else {
            return " - : -";
        }
    }

    //bez decimalnih ako je rezultat cijeli broj, 4.0->4, ali 4.5->4.5 (max jedna decimala)
    private String roundIt(double number) {
        if(number % 1 == 0) {
            return String.format("%.0f", number);
        } else {
            return String.format("%.1f", number);
        }
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
        double result = firstScore;
        result = 31 * result + secondScore;
        return (int)result;
    }
}

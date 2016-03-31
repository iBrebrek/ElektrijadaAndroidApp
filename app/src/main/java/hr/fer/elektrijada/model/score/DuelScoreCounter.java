package hr.fer.elektrijada.model.score;

/**
 * Created by Ivica Brebrek
 */
public class DuelScoreCounter extends DuelScore {

    /** Broj pojavljivanja tog rezultata u bazi */
    private int count;

    public DuelScoreCounter(double firstScore, double secondScore, int count) {
        super(firstScore, secondScore);
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}

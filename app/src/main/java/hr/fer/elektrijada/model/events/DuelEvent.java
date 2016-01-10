package hr.fer.elektrijada.model.events;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ivica Brebrek
 */
public class DuelEvent extends Event {
    private Score myResult;
    private Score result;
    private boolean hasResult = false;
    private String teamHome;
    private String teamAway;
    private HashMap<Score, Integer> allResults;

    public class Score {
        private int homeScore;
        private int awayScore;

        public Score(int homeScore, int awayScore) {
            this.homeScore = homeScore;
            this.awayScore = awayScore;
        }

        @Override
        public boolean equals(Object other) {
            if (other == null) return false;
            if (other == this) return true;
            if (!(other instanceof Score)) return false;
            Score otherScore = (Score) other;
            return otherScore.homeScore == this.homeScore
                    && otherScore.awayScore == this.awayScore;
        }
        @Override
        public int hashCode() {
            int result = homeScore;
            result = 31 * result + awayScore;
            return result;
        }
    }

    /**
     *
     * @param homeScore
     * @param awayScore
     * @param frequencyOfThisResult     koliko puta se taj rezutat pojavljuje, obicno se koristi za unos iz baze,
     *                                  npr., ako se u bazi taj rezultat pojavi 143 puta ovaj broj ce biti 143
     *                               !  ako ne unosis iz baze pisi 1
     */
    public void addPossibleResult (int homeScore, int awayScore, int frequencyOfThisResult){
        if (allResults == null) {
            allResults = new HashMap<>();
        }
        Score score = new Score(homeScore, awayScore);
        Integer numberOfScore = allResults.get(score);
        allResults.put(score, numberOfScore == null? frequencyOfThisResult : numberOfScore+frequencyOfThisResult);
    }

    public Score getMostFrequentResult () {
        Score score = null;
        if(allResults != null) {
            int max = 0;
            for (Map.Entry<Score, Integer> entry : allResults.entrySet()) {
                int current = entry.getValue();
                if (current > max) {
                    max = current;
                    score = entry.getKey();
                }
            }
        }
        return score;
    }

    public void setMyResult(int homeScore, int awayScore) {
        myResult = new Score(homeScore, awayScore);
    }

    public Score getMyResult() {
        return result;
    }


    /**
     *
     * svi podatci
     *
     * @param id            id UTAKMICE
     * @param name          ime VRSTE, npr., Nogomet(M)
     * @param timeFrom      vrijeme pocetka, ne smije biti null
     * @param timeTo        vrijeme kraja, moze biti null no postoji konsturkor bez ovoga
     * @param teamHome      ime TIMA koji je domacin (onaj koji se pise prije s lijeve strane), ne smije biti null,
     *                  !!! u slucaju ako je npr 1vs1 i gledaju se imena, onda ovdje predajte "Ime Prezime" ili koji god string koji ce predstavljati tu osobu
     * @param homeScore     bodovi DOMACINA (lijeva strana)
     * @param teamAway      ime TIMA koji je gost (onaj koji se pise s desne strane), ne smije biti null
     * @param awayScore     bodovi GOSTA (desna strana)
     */
    public DuelEvent(int id, String name, Date timeFrom, Date timeTo, String teamHome, int homeScore, String teamAway, int awayScore) {
        this(id, name, timeFrom, timeTo, teamHome, teamAway);
        setResult(homeScore, awayScore);
    }

    /**
     *  bez kraja
     */
    public DuelEvent(int id, String name, Date timeFrom, String teamHome, int homeScore, String teamAway, int awayScore) {
        this(id, name, timeFrom, null, teamHome, homeScore, teamAway, awayScore);
    }

    /**
     *  bez rezultata
     */
    public DuelEvent(int id, String name, Date timeFrom, Date timeTo, String teamHome, String teamAway) {
        super(id, name, timeFrom, timeTo);
        if(teamAway == null || teamHome == null) {
            throw new IllegalArgumentException("Sport event must have both teams");
        }
        this.teamAway = teamAway;
        this.teamHome = teamHome;
    }

    /**
     *  bez kraja i bez rezultata
     */
    public DuelEvent(int id, String name, Date timeFrom, String teamHome, String teamAway){
        this(id, name, timeFrom, null, teamHome, teamAway);
    }

    public void setResult(int homeScore, int awayScore) {
        hasResult = true;
        result = new Score(homeScore, awayScore);
    }

    /**
     *  vraca imena timova, prvi je domacin drugi je gost
     *  @return String je homeTeam:awayTeam
     */
    public String getTeams() {
        return teamHome+" - "+teamAway;
    }

    /**
     * vraca rezultata utakmice, prvi je rezultat domacina drugi je rezultat gosta
     * @return String je homeScore:awayScore ili -
     */
    public String getResult() {
        if(hasResult) {
            return result.homeScore + ":" + result.awayScore;
        } else {
            return "  -  ";
        }
    }
}

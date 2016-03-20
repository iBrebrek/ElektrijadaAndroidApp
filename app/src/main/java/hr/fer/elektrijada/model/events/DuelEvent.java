package hr.fer.elektrijada.model.events;

import java.util.Date;

import hr.fer.elektrijada.model.score.DuelScore;

/**
 * Created by Ivica Brebrek
 */
public class DuelEvent extends Event {
    private DuelScore result;
    private String teamHome;
    private String teamAway;

    //ovo je nepotrebno u ovom razredu, no zal mi brisat
//    private HashMap<DuelScore, Integer> allResults;
//
//    *
//     *
//     * @param homeScore
//     * @param awayScore
//     * @param frequencyOfThisResult     koliko puta se taj rezutat pojavljuje, obicno se koristi za unos iz baze,
//     *                                  npr., ako se u bazi taj rezultat pojavi 143 puta ovaj broj ce biti 143
//     *                               !  ako ne unosis iz baze pisi 1
//
//    public void addPossibleResult (int homeScore, int awayScore, int frequencyOfThisResult){
//        if (allResults == null) {
//            allResults = new HashMap<>();
//        }
//        DuelScore score = new DuelScore(homeScore, awayScore);
//        Integer numberOfScore = allResults.get(score);
//        allResults.put(score, numberOfScore == null? frequencyOfThisResult : numberOfScore+frequencyOfThisResult);
//    }
//
//    public DuelScore getMostFrequentResult () {
//        DuelScore score = null;
//        if(allResults != null) {
//            int max = 0;
//            for (Map.Entry<DuelScore, Integer> entry : allResults.entrySet()) {
//                int current = entry.getValue();
//                if (current > max) {
//                    max = current;
//                    score = entry.getKey();
//                }
//            }
//        }
//        return score;
//    }

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
        result = new DuelScore(homeScore, awayScore);
    }

    public void setResult(DuelScore score) {
        result = score;
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
        if(result.isSet()) {
            return result.getFirstScore() + ":" + result.getSecondScore();
        } else {
            return "  -  ";
        }
    }
}

package hr.fer.elektrijada.extras;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import hr.fer.elektrijada.dal.sql.competition.CompetitionFromDb;
import hr.fer.elektrijada.dal.sql.competition.SqlCompetitionRepository;
import hr.fer.elektrijada.dal.sql.duel.DuelFromDb;
import hr.fer.elektrijada.dal.sql.duel.SqlDuelRepository;
import hr.fer.elektrijada.dal.sql.duelscore.SqlDuelScoreRepository;
import hr.fer.elektrijada.model.events.CompetitionEvent;
import hr.fer.elektrijada.model.events.DuelEvent;
import hr.fer.elektrijada.model.events.Event;
import hr.fer.elektrijada.model.score.DuelScore;
import hr.fer.elektrijada.util.DateParserUtil;

/**
 * Created by Ivica Brebrek
 */
public class Favorites {
    public static final String APP_SHARED_PREERENCES = "ELEKTRIJADA_APP";
    public static final String FAVORITE_DUELS = "favoriteDuels";
    public static final String FAVORITE_COMPETITIONS = "favoriteCompetitions";

    public static boolean isFavorite(Context context, String eventId, String favoritesName) {
        SharedPreferences settings = context.getSharedPreferences(APP_SHARED_PREERENCES, Context.MODE_PRIVATE);

        if(favoritesName.equals(FAVORITE_DUELS)) {
            if (settings.contains(FAVORITE_DUELS)) {
                Set<String> setOfDuelIds = settings.getStringSet(FAVORITE_DUELS, new HashSet<String>());
                if (setOfDuelIds.contains(eventId)) {
                    return true;
                }
            }
        } else {
            if (settings.contains(FAVORITE_COMPETITIONS)) {
                Set<String> setOfCompIds = settings.getStringSet(FAVORITE_COMPETITIONS, new HashSet<String>());
                if (setOfCompIds.contains(eventId)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void addFavorite(Context context, String eventId, String addTo) {
        SharedPreferences settings = context
                .getSharedPreferences(APP_SHARED_PREERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();

        if(addTo.equals(FAVORITE_COMPETITIONS)) {
            Set<String> setOfCompIds = settings.getStringSet(FAVORITE_COMPETITIONS, null);
            /*
            ne smije se modificirati izvorni set,
            kada sam modificirao pamtio mi je samo jednog po setu,
            a čak i u dokumentaciji piše da ne modificiramo,
            iz tog razloga radim novi set koji je jednak izvornom
             */
            setOfCompIds = setOfCompIds == null
                    ? new HashSet<String>()
                    : new HashSet<>(setOfCompIds);
            setOfCompIds.add(eventId);
            editor.putStringSet(FAVORITE_COMPETITIONS, setOfCompIds);
        } else {
            Set<String> setOfDuelIds = settings.getStringSet(FAVORITE_DUELS, null);
            setOfDuelIds = setOfDuelIds == null
                    ? new HashSet<String>()
                    : new HashSet<>(setOfDuelIds);
            setOfDuelIds.add(eventId);
            editor.putStringSet(FAVORITE_DUELS, setOfDuelIds);
        }
        editor.apply();
    }

    public static void addFavorite(Context context, Event event) {
        String id = Integer.toString(event.getId());
        if(event instanceof CompetitionEvent) {
            addFavorite(context, id, FAVORITE_COMPETITIONS);
        } else {
            addFavorite(context, id, FAVORITE_DUELS);
        }
    }

    public static void removeFavorite(Context context, Event event) {
        String id = Integer.toString(event.getId());
        if(event instanceof DuelEvent) {
            removeFavorite(context, id, FAVORITE_DUELS);
        } else if(event instanceof CompetitionEvent) {
            removeFavorite(context, id, FAVORITE_COMPETITIONS);
        }
    }

    public static void removeFavorite(Context context, String id, String deleteFrom) {
        SharedPreferences settings = context
                .getSharedPreferences(APP_SHARED_PREERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();

        switch (deleteFrom) {
            case FAVORITE_DUELS:
                Set<String> setOfDuelIds = settings.getStringSet(FAVORITE_DUELS, null);
                if (setOfDuelIds == null) return;
                setOfDuelIds = new HashSet<>(setOfDuelIds); //isti razlog kao u addFavorite
                setOfDuelIds.remove(id);
                editor.putStringSet(FAVORITE_DUELS, setOfDuelIds);
                break;

            case FAVORITE_COMPETITIONS:
                Set<String> setOfCompIds = settings.getStringSet(FAVORITE_COMPETITIONS, null);
                if (setOfCompIds == null) return;
                setOfCompIds = new HashSet<>(setOfCompIds);
                setOfCompIds.remove(id);
                editor.putStringSet(FAVORITE_COMPETITIONS, setOfCompIds);
                break;
        }
        editor.apply();
    }

    public static ArrayList<Event> getFavorites(Context context) {
        SharedPreferences settings = context
                .getSharedPreferences(APP_SHARED_PREERENCES, Context.MODE_PRIVATE);

        ArrayList<Event> favorites = new ArrayList<>();

        if (settings.contains(FAVORITE_DUELS)) {
            Set<String> setOfDuelIds = settings.getStringSet(FAVORITE_DUELS, new HashSet<String>());
            for (String id : setOfDuelIds) {
                Event event = stringIdToDuelEvent(context, id);
                if (event == null) {
                    removeFavorite(context, id, FAVORITE_DUELS);
                } else {
                    favorites.add(event);
                }
            }
        }
        if (settings.contains(FAVORITE_COMPETITIONS)) {
            Set<String> setOfCompIds = settings.getStringSet(FAVORITE_COMPETITIONS, new HashSet<String>());
            for (String id:setOfCompIds) {
                Event event = stringIdToCompetitionEvent(context, id);
                if (event == null) {
                    removeFavorite(context, id, FAVORITE_COMPETITIONS);
                } else {
                    favorites.add(event);
                }
            }
        }
        return favorites;
    }

    private static CompetitionEvent stringIdToCompetitionEvent(Context context, String stringId) {
        SqlCompetitionRepository compRepo = null;
        try {
            int id = Integer.parseInt(stringId);
            compRepo = new SqlCompetitionRepository(context);
            CompetitionFromDb eventFromDb = compRepo.getCompetition(id);
            if(eventFromDb == null) {
                return null;
            }

            CompetitionEvent event = new CompetitionEvent(
                    eventFromDb.getId(),
                    eventFromDb.getCategory().getName(),
                    DateParserUtil.stringToDate(eventFromDb.getTimeFrom()),
                    DateParserUtil.stringToDate(eventFromDb.getTimeTo()),
                    compRepo.hasScore(id)
            );
            return event;
        } catch (Exception exc) {
            return null;
        } finally {
            if (compRepo != null) compRepo.close();
        }
    }

    private static DuelEvent stringIdToDuelEvent(Context context, String stringId) {
        SqlDuelRepository repoDuel = null;
        SqlDuelScoreRepository repoScore = null;
        try {
            int id = Integer.parseInt(stringId);
            repoDuel = new SqlDuelRepository(context);
            DuelFromDb eventFromDb = repoDuel.getDuel(id);
            if(eventFromDb == null) {
                return null;
            }

            repoScore = new SqlDuelScoreRepository(context);
            DuelScore score = repoScore.getScore(eventFromDb.getId());

            DuelEvent event = new DuelEvent(
                    eventFromDb.getId(),
                    eventFromDb.getCategory().getName(),
                    DateParserUtil.stringToDate(eventFromDb.getTimeFrom()),
                    DateParserUtil.stringToDate(eventFromDb.getTimeTo()),
                    eventFromDb.getFirstCompetitor().getName(),
                    eventFromDb.getSecondCompetitor().getName()
            );
            if(score != null) {
                event.setResult(score);
            }
            return event;
        } catch (Exception exc) {
            return null;
        } finally {
            if (repoDuel != null) repoDuel.close();
            if (repoScore != null) repoScore.close();
        }
    }
}

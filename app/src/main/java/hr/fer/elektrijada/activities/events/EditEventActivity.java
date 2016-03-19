package hr.fer.elektrijada.activities.events;

import android.os.Bundle;
import android.widget.Toast;

import hr.fer.elektrijada.dal.sql.competition.CompetitionFromDb;
import hr.fer.elektrijada.dal.sql.competition.SqlCompetitionRepository;
import hr.fer.elektrijada.dal.sql.duel.DuelFromDb;
import hr.fer.elektrijada.dal.sql.duel.SqlDuelRepository;

/**
 * Uredivanje vec postojeceg eventa
 *
 * Created by Ivica Brebrek
 */
public class EditEventActivity extends CreateNewEventActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        boolean isDuel = getIntent().getBooleanExtra("isDuel", false);
        int eventId = getIntent().getIntExtra("event_id", -1);

        if(eventId == -1) { //nebi se trebalo nikad dogoditi
            Toast.makeText(getApplicationContext(), "Greška prilikom otvaranja događaja.", Toast.LENGTH_SHORT).show();
            finish();
        }

        if(isDuel) {
            SqlDuelRepository duelRepo = new SqlDuelRepository(this);
            DuelFromDb duel = duelRepo.getDuel(eventId);
            duelRepo.close();
            setEvent(duel.getCategoryId(), duel.getStageId(), duel.getFirstId(),
                    duel.getSecondId(), duel.getLocation(), duel.getTimeFrom(), duel.getTimeTo());
        } else {
            SqlCompetitionRepository compRepo = new SqlCompetitionRepository(this);
            CompetitionFromDb comp = compRepo.getCompetition(eventId);
            compRepo.close();
            setEvent(comp.getCategoryId(), -1, -1, -1, comp.getLocation(), comp.getTimeFrom(), comp.getTimeTo());
        }
    }
}

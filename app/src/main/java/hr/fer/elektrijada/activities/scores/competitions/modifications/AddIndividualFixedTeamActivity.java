package hr.fer.elektrijada.activities.scores.competitions.modifications;

import android.os.Bundle;
import android.widget.ArrayAdapter;

import hr.fer.elektrijada.dal.sql.competitor.CompetitorFromDb;
import hr.fer.elektrijada.dal.sql.competitor.SqlCompetitorRepository;

/**
 * Created by Ivica Brebrek
 */
public class AddIndividualFixedTeamActivity extends AddIndividualActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int teamId = getIntent().getIntExtra("teamId", -1);
        SqlCompetitorRepository repo = new SqlCompetitorRepository(this);
        CompetitorFromDb team = repo.getCompetitor(teamId);
        repo.close();

        int index = ((ArrayAdapter<CompetitorFromDb>) teamSpinner.getAdapter()).getPosition(team);
        teamSpinner.setSelection(index);
        teamSpinner.setEnabled(false);
    }
}

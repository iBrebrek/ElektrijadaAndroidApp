package hr.fer.elektrijada.activities.scores.competitions.modifications;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import hr.fer.elektrijada.R;
import hr.fer.elektrijada.dal.sql.competitionscore.CompetitionScoreFromDb;
import hr.fer.elektrijada.dal.sql.competitionscore.SqlCompetitionScoreRepository;
import hr.fer.elektrijada.dal.sql.competitor.CompetitorFromDb;
import hr.fer.elektrijada.dal.sql.competitor.SqlCompetitorRepository;

/**
 * Created by Ivica Brebrek
 */
public class ViewIndividualActivity extends AddIndividualActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        findViewById(R.id.add_individual_info).setVisibility(View.GONE);

        int teamId = getIntent().getIntExtra("teamId", -1);
        int personId = getIntent().getIntExtra("personId", -1);
        SqlCompetitorRepository repo = new SqlCompetitorRepository(this);
        CompetitorFromDb team = repo.getCompetitor(teamId);
        CompetitorFromDb person = repo.getCompetitor(personId);
        repo.close();

        SqlCompetitionScoreRepository scoreRepo = new SqlCompetitionScoreRepository(this);
        Double score = scoreRepo.getMyScore(personId, person.getCompetition().getId());
        if(score != null) {
            ((EditText)findViewById(R.id.add_individual_score)).setText(score.toString());
        }
        scoreRepo.close();

        int indexTeam = ((ArrayAdapter<CompetitorFromDb>) teamSpinner.getAdapter()).getPosition(team);
        teamSpinner.setSelection(indexTeam);
        teamSpinner.setEnabled(false);
        //to remove listener
        teamSpinner.setOnItemSelectedListener(null);

        //only one element added to this spinner, no need to add all
        personSpinner = (Spinner) findViewById(R.id.add_individual_spinner_person);
        List<CompetitorFromDb> thisPerson = new ArrayList<>(); //so it can be added to spinner
        thisPerson.add(person);
        ArrayAdapter<CompetitorFromDb> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, thisPerson);
        personSpinner.setAdapter(adapter);

        int indexPerson = adapter.getPosition(person);
        personSpinner.setSelection(indexPerson);
        personSpinner.setEnabled(false);

        setTitle(person.toString());
    }

    private final static String REMOVE_INDIVIDUAL = "Obriši natjecatelja";

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_template, menu);
        menu.add(REMOVE_INDIVIDUAL);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String name = item.getTitle().toString();
        switch (name) {
            case REMOVE_INDIVIDUAL:
                SqlCompetitionScoreRepository repo = new SqlCompetitionScoreRepository(this);
                CompetitorFromDb team = (CompetitorFromDb) teamSpinner.getSelectedItem();
                CompetitorFromDb individual = (CompetitorFromDb) personSpinner.getSelectedItem();
                List<CompetitionScoreFromDb> scores = repo.getIndividualsPerTeam(team.getCompetition().getId(), team.getId());
                for(CompetitionScoreFromDb score : scores) {
                    if(score.getCompetition().getId() == individual.getCompetition().getId()) {
                        if(score.getCompetitor().getId() == individual.getId()) {
                            repo.deleteScore(score);
                            break; //jer je dovoljno naci samo jednog, deleteScore brise sve koji imaju tog natjecatelja u tom timu
                        }
                    }
                }
                repo.close();
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

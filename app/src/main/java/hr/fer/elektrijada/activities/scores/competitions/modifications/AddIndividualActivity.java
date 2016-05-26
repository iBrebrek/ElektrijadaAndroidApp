package hr.fer.elektrijada.activities.scores.competitions.modifications;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import hr.fer.elektrijada.MenuHandler;
import hr.fer.elektrijada.R;
import hr.fer.elektrijada.activities.SaveBeforeExitActivity;
import hr.fer.elektrijada.dal.sql.competition.CompetitionFromDb;
import hr.fer.elektrijada.dal.sql.competition.SqlCompetitionRepository;
import hr.fer.elektrijada.dal.sql.competitionscore.CompetitionScoreFromDb;
import hr.fer.elektrijada.dal.sql.competitionscore.SqlCompetitionScoreRepository;
import hr.fer.elektrijada.dal.sql.competitor.CompetitorFromDb;
import hr.fer.elektrijada.dal.sql.competitor.SqlCompetitorRepository;
import hr.fer.elektrijada.extras.MyInfo;

/**
 * Created by Ivica Brebrek
 */
public class AddIndividualActivity extends SaveBeforeExitActivity {

    private CompetitionFromDb competition;
    protected Spinner teamSpinner;
    protected Spinner personSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int competitionId = getIntent().getIntExtra("competitionId", -1);
        if(competitionId == -1) {
            Toast.makeText(this, "Nemoguće dodati pojedinca u natjecanje.", Toast.LENGTH_SHORT).show();
            finish();
        }
        SqlCompetitionRepository compRepo = new SqlCompetitionRepository(this);
        competition = compRepo.getCompetition(competitionId);
        compRepo.close();

        initSpinners();

        findViewById(R.id.add_individual_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(save()) finish();
            }
        });

        //remove initial keyboard...
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void initSpinners() {
        SqlCompetitorRepository repo = new SqlCompetitorRepository(this);
        List<CompetitorFromDb> teams = repo.getAllTeams(competition.getId());
        repo.close();

        teamSpinner = (Spinner) findViewById(R.id.add_individual_spinner_team);
        ArrayAdapter<CompetitorFromDb> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, teams);
        teamSpinner.setAdapter(adapter);

        teamSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SqlCompetitorRepository repo = new SqlCompetitorRepository(AddIndividualActivity.this);
                List<CompetitorFromDb> individuals = repo.getStudents(((CompetitorFromDb)teamSpinner.getSelectedItem()).getFaculty().getId());
                repo.close();
                Collections.sort(individuals, new Comparator<CompetitorFromDb>() {
                    @Override
                    public int compare(CompetitorFromDb lhs, CompetitorFromDb rhs) {
                        return lhs.getName().compareTo(rhs.getName());
                    }
                });

                personSpinner = (Spinner) findViewById(R.id.add_individual_spinner_person);

                ArrayAdapter<CompetitorFromDb> adapter = new ArrayAdapter<>(AddIndividualActivity.this, android.R.layout.simple_spinner_dropdown_item, individuals);
                personSpinner.setAdapter(adapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_add_individual_score;
    }

    @Override
    protected int belongingToMenuItemId() {
        return MenuHandler.RESULTS_ID;
    }

    @Override
    protected boolean save() {
        String resultString = ((EditText)findViewById(R.id.add_individual_score)).getText().toString();
        if(notDouble(resultString)) {
            Toast.makeText(this, "Rezultat je nepravilan.", Toast.LENGTH_SHORT).show();
            return false;
        }
        Double result = Double.parseDouble(resultString);

        CompetitorFromDb selectedPerson = (CompetitorFromDb)personSpinner.getSelectedItem();
        selectedPerson.setCompetition(competition);
        selectedPerson.setGroupCompetitor((CompetitorFromDb)teamSpinner.getSelectedItem());

        SqlCompetitorRepository comRepo = new SqlCompetitorRepository(this);
        comRepo.updateCompetitor(selectedPerson);
        comRepo.close();

        CompetitionScoreFromDb score = new CompetitionScoreFromDb(-1, result, null, competition, MyInfo.getMyUsername(this), selectedPerson, false);
        {
            SqlCompetitionScoreRepository compScoreRepo = new SqlCompetitionScoreRepository(this);
            List<CompetitionScoreFromDb> listOfscores = compScoreRepo.getIndividualsPerTeam(competition.getId(), ((CompetitorFromDb)teamSpinner.getSelectedItem()).getId());

            int index = listOfscores.indexOf(score);
            if (index > -1) {
                score.setId(listOfscores.get(index).getId());
                compScoreRepo.updateScore(score);
            }else{
                compScoreRepo.addScore(score);
            }
        }

        return true;
    }

    private boolean notDouble(String maybeDouble) {
        try {
            Double.parseDouble(maybeDouble);
            return false;
        } catch (Exception exc) {
            return true;
        }
    }
}

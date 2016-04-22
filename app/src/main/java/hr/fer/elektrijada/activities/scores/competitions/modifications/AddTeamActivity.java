package hr.fer.elektrijada.activities.scores.competitions.modifications;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
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
import hr.fer.elektrijada.dal.sql.faculty.FacultyFromDb;
import hr.fer.elektrijada.dal.sql.faculty.SqlFacultyRepository;
import hr.fer.elektrijada.extras.MyInfo;

/**
 * Created by Ivica Brebrek
 */
public class AddTeamActivity extends SaveBeforeExitActivity {

    private CompetitionFromDb competition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int competitionId = getIntent().getIntExtra("competitionId", -1);
        if(competitionId == -1) {
            Toast.makeText(this, "Nemoguće dodati tim u natjecanje.", Toast.LENGTH_SHORT).show();
            finish();
        }
        SqlCompetitionRepository compRepo = new SqlCompetitionRepository(this);
        competition = compRepo.getCompetition(competitionId);
        compRepo.close();

        ((RadioButton)findViewById(R.id.add_team_first_team)).setChecked(true);

        SqlFacultyRepository repo = new SqlFacultyRepository(this);
        final List<FacultyFromDb> faculties = repo.getFaculties();
        repo.close();
        ArrayList<String> names = new ArrayList<>();
        for(FacultyFromDb faculty : faculties) {
            names.add(faculty.getName());
        }

        Spinner dropdown = (Spinner) findViewById(R.id.add_team_spinner_faculties);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, names);
        dropdown.setAdapter(adapter);


        findViewById(R.id.add_team_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(save()) finish();
            }
        });
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_add_team_score;
    }

    @Override
    protected int belongingToMenuItemId() {
        return MenuHandler.RESULTS_ID;
    }

    @Override
    protected boolean save() {
        String resultString = ((EditText)findViewById(R.id.add_team_score)).getText().toString();
        if(notDouble(resultString)) {
            Toast.makeText(this, "Rezultat je nepravilan.", Toast.LENGTH_SHORT).show();
            return false;
        }
        Double result = Double.parseDouble(resultString);

        SqlFacultyRepository repoFaculty = new SqlFacultyRepository(this);
        FacultyFromDb faculty = repoFaculty
                                .getFaculties()
                                .get((int)((Spinner) findViewById(R.id.add_team_spinner_faculties)).getSelectedItemId());
        repoFaculty.close();
        String teamName = faculty.getName() + (((RadioButton)findViewById(R.id.add_team_first_team)).isChecked() ? " 1" : " 2");

        CompetitorFromDb thisTeam;
        {//koristim blok da se ne bi pamtila ova lista kroz cijelu metodu
            SqlCompetitorRepository repoCompetitor = new SqlCompetitorRepository(this);
            List<CompetitorFromDb> competitors = repoCompetitor.getAllTeams(competition.getId());

            thisTeam = new CompetitorFromDb(-1, teamName, null, false, null, faculty, competition, 0, false);
            int index = competitors.indexOf(thisTeam);
            if (index > -1) {
                thisTeam = competitors.get(index);
            } else {
                thisTeam.setId(repoCompetitor.createNewCompetitor(thisTeam));
            }
            repoCompetitor.close();
        }

        CompetitionScoreFromDb thisScore = new CompetitionScoreFromDb(-1, result, null, competition, MyInfo.getMyUsername(this), thisTeam, false);
        {
            SqlCompetitionScoreRepository compScoreRepo = new SqlCompetitionScoreRepository(this);
            List<CompetitionScoreFromDb> listOfscores = compScoreRepo.getTeamsScores(competition.getId());

            int index = listOfscores.indexOf(thisScore);
            if(index > -1) {
                thisScore.setId(listOfscores.get(index).getId());
                compScoreRepo.updateScore(thisScore);
            } else {
                compScoreRepo.addScore(thisScore);
            }

            compScoreRepo.close();

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

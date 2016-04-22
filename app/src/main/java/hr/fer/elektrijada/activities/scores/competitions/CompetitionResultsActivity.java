package hr.fer.elektrijada.activities.scores.competitions;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import hr.fer.elektrijada.MenuHandler;
import hr.fer.elektrijada.R;
import hr.fer.elektrijada.activities.BaseMenuActivity;
import hr.fer.elektrijada.activities.scores.competitions.adapters.IndividualListAdapter;
import hr.fer.elektrijada.activities.scores.competitions.adapters.TeamListAdapter;
import hr.fer.elektrijada.activities.scores.competitions.modifications.AddIndividualActivity;
import hr.fer.elektrijada.activities.scores.competitions.modifications.AddTeamActivity;
import hr.fer.elektrijada.activities.scores.competitions.modifications.ViewIndividualActivity;
import hr.fer.elektrijada.dal.sql.competition.CompetitionFromDb;
import hr.fer.elektrijada.dal.sql.competition.SqlCompetitionRepository;
import hr.fer.elektrijada.dal.sql.competitionscore.CompetitionScoreFromDb;
import hr.fer.elektrijada.dal.sql.competitionscore.SqlCompetitionScoreRepository;

/**
 * Created by Ivica Brebrek
 */
public class CompetitionResultsActivity extends BaseMenuActivity {
    private CompetitionFromDb competition;
    private TeamListAdapter adapter;
    private boolean showTeams = true;

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_competition_results;
    }

    @Override
    protected int belongingToMenuItemId() {
        return MenuHandler.RESULTS_ID;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SqlCompetitionRepository compRepo = new SqlCompetitionRepository(this);
        competition = compRepo.getCompetition(getIntent().getIntExtra("competitionId", -1));
        compRepo.close();

        setTitle(competition.getCategory().getName());
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(showTeams) initTeamScrollView();
        else initIndividualScrollView();
    }

    private void initTeamScrollView() {
        SqlCompetitionScoreRepository repository = new SqlCompetitionScoreRepository(getApplicationContext());
        final List<CompetitionScoreFromDb> list = repository.getTeamsScores(competition.getId());
        repository.close();
        Collections.sort(list, new Comparator<CompetitionScoreFromDb>() {
            @Override
            public int compare(CompetitionScoreFromDb lhs, CompetitionScoreFromDb rhs) {
                if(lhs.getResult() > rhs.getResult()) {
                    return -1;
                }
                return 1;
            }
        });

        final ListView listView = (ListView) findViewById(R.id.competition_results_list_of_competitors);
        adapter = new TeamListAdapter(this, list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() { //otvaranje liste pojedinaca u tom timu
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), TeamResultActivity.class);
                intent.putExtra("teamId", list.get(position).getCompetitor().getId());
                startActivity(intent);
            }
        });
    }


    private void initIndividualScrollView() {

        SqlCompetitionScoreRepository repository = new SqlCompetitionScoreRepository(getApplicationContext());
        final List<CompetitionScoreFromDb> list = repository.getIndividualScores(competition.getId());
        repository.close();
        Collections.sort(list, new Comparator<CompetitionScoreFromDb>() {
            @Override
            public int compare(CompetitionScoreFromDb lhs, CompetitionScoreFromDb rhs) {
                if(lhs.getResult() > rhs.getResult()) {
                    return -1;
                }
                return 1;
            }
        });

        final ListView listView = (ListView) findViewById(R.id.competition_results_list_of_competitors);
        IndividualListAdapter individualAdapter = new IndividualListAdapter(this, list);
        listView.setAdapter(individualAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() { //otvaranje tog pojedinca
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), ViewIndividualActivity.class);
                intent.putExtra("competitionId", list.get(position).getCompetition().getId());
                intent.putExtra("teamId", list.get(position).getCompetitor().getGroupCompetitor().getId());
                intent.putExtra("personId", list.get(position).getCompetitor().getId());
                startActivity(intent);
            }
        });
    }


    private final static String ADD_NEW_TEAM = "Dodaj tim";
    private final static String ADD_NEW_PERSON = "Dodaj osobu";
    private final static String SHOW_TEAMS_LIST = "Timski rezultati";
    private final static String SHOW_INDIVIDUALS_LIST = "Pojedinačni rezultati";

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_template, menu);
        menu.add(ADD_NEW_TEAM);
        menu.add(ADD_NEW_PERSON);
        menu.add(SHOW_TEAMS_LIST);
        menu.add(SHOW_INDIVIDUALS_LIST);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String name = item.getTitle().toString();
        switch (name) {
            case ADD_NEW_TEAM:
                Intent intentTeam = new Intent(getApplicationContext(), AddTeamActivity.class);
                intentTeam.putExtra("competitionId", competition.getId());
                startActivity(intentTeam);
                break;

            case ADD_NEW_PERSON:
                Intent intentPerson = new Intent(getApplicationContext(), AddIndividualActivity.class);
                intentPerson.putExtra("competitionId", competition.getId());
                startActivity(intentPerson);
                break;

            case SHOW_TEAMS_LIST:
                showTeams = true;
                initTeamScrollView();
                break;

            case SHOW_INDIVIDUALS_LIST:
                showTeams = false;
                initIndividualScrollView();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

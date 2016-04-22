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
import hr.fer.elektrijada.activities.scores.competitions.adapters.SingleTeamAdapter;
import hr.fer.elektrijada.activities.scores.competitions.modifications.AddIndividualFixedTeamActivity;
import hr.fer.elektrijada.activities.scores.competitions.modifications.ViewIndividualActivity;
import hr.fer.elektrijada.dal.sql.competitionscore.CompetitionScoreFromDb;
import hr.fer.elektrijada.dal.sql.competitionscore.SqlCompetitionScoreRepository;
import hr.fer.elektrijada.dal.sql.competitor.CompetitorFromDb;
import hr.fer.elektrijada.dal.sql.competitor.SqlCompetitorRepository;

/**
 * Created by Ivica Brebrek
 */
public class TeamResultActivity extends BaseMenuActivity{

    private CompetitorFromDb team;

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_competition_team_result;
    }

    @Override
    protected int belongingToMenuItemId() {
        return MenuHandler.RESULTS_ID;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SqlCompetitorRepository repo = new SqlCompetitorRepository(this);
        team = repo.getCompetitor(getIntent().getIntExtra("teamId", -1));
        repo.close();

        String title = team.toString();

        SqlCompetitionScoreRepository scoreRepo = new SqlCompetitionScoreRepository(this);
        Double score = scoreRepo.getScore(team.getId(), team.getCompetition().getId());
        if(score != null) {
            title += " [" + score.toString() + "]";
        }

        setTitle(title);
    }

    @Override
    protected void onResume() {
        super.onResume();

        initListView();
    }

    private void initListView() {
        SqlCompetitionScoreRepository repository = new SqlCompetitionScoreRepository(getApplicationContext());
        final List<CompetitionScoreFromDb> list = repository.getIndividualsPerTeam(team.getCompetition().getId(), team.getId());
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

        final ListView listView = (ListView) findViewById(R.id.team_results_list_of_individuals);
        SingleTeamAdapter adapter = new SingleTeamAdapter(this, list);
        listView.setAdapter(adapter);
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

    private final static String ADD_NEW_MEMBER = "Dodaj člana";
    private final static String REMOVE_TEAM = "Obriši cijeli tim";

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_template, menu);
        menu.add(ADD_NEW_MEMBER);
        menu.add(REMOVE_TEAM);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String name = item.getTitle().toString();
        switch (name) {
            case ADD_NEW_MEMBER:
                Intent intent = new Intent(getApplicationContext(), AddIndividualFixedTeamActivity.class);
                intent.putExtra("competitionId", team.getCompetition().getId());
                intent.putExtra("teamId", team.getId());
                startActivity(intent);
                break;

            case REMOVE_TEAM:
                SqlCompetitionScoreRepository repo = new SqlCompetitionScoreRepository(this);
                List<CompetitionScoreFromDb> scores = repo.getTeamsScores(team.getCompetition().getId());
                for(CompetitionScoreFromDb score : scores) {
                    if(team.equals(score.getCompetitor())) {
                        repo.deleteScore(score);
                    }
                }
                repo.close();
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

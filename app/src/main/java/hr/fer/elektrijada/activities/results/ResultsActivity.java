package hr.fer.elektrijada.activities.results;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

import hr.fer.elektrijada.MenuHandler;
import hr.fer.elektrijada.R;
import hr.fer.elektrijada.activities.BaseMenuActivity;
import hr.fer.elektrijada.activities.scores.competitions.CompetitionResultsActivity;
import hr.fer.elektrijada.dal.sql.category.CategoryFromDb;
import hr.fer.elektrijada.dal.sql.competition.CompetitionFromDb;
import hr.fer.elektrijada.dal.sql.competition.SqlCompetitionRepository;
import hr.fer.elektrijada.dal.sql.duel.DuelFromDb;
import hr.fer.elektrijada.dal.sql.duel.SqlDuelRepository;

/**
 * Created by Ivica Brebrek on 3.6.2016..
 */
public class ResultsActivity extends BaseMenuActivity {

    private boolean showDuels = true;
    private boolean showCompetitions = true;

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_results;
    }

    @Override
    protected int belongingToMenuItemId() {
        return MenuHandler.RESULTS_ID;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createList();
    }

    private void createList() {
        ListView listView = (ListView) findViewById(R.id.activity_results_list);
        Set<CategoryFromDb> usedCategories = new HashSet<>();
        if(showDuels) {
            SqlDuelRepository duelRepository = new SqlDuelRepository(this);
            for (DuelFromDb duel : duelRepository.getAllDuels()) {
                usedCategories.add(duel.getCategory());
            }
            duelRepository.close();
        }
        if(showCompetitions) {
            SqlCompetitionRepository competitionRepository = new SqlCompetitionRepository(this);
            for (CompetitionFromDb comp : competitionRepository.getAllCompetitions()) {
                usedCategories.add(comp.getCategory());
            }
            competitionRepository.close();
        }
        final ArrayList<CategoryFromDb> categories = new ArrayList<>(usedCategories);
        Collections.sort(categories, new Comparator<CategoryFromDb>() {
            @Override
            public int compare(CategoryFromDb lhs, CategoryFromDb rhs) {
                return lhs.getName().compareTo(rhs.getName());
            }
        });
        listView.setAdapter(new CategoriesListAdapter(this, categories));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CategoryFromDb cat = categories.get(position);
                if(cat.isDuel()) {
                    Intent intent = new Intent(getApplicationContext(), DuelsResultsActivity.class);
                    intent.putExtra("categoryId", cat.getId());
                    startActivity(intent);
                    return;
                } else {
                    SqlCompetitionRepository compRepo = new SqlCompetitionRepository(ResultsActivity.this);
                    for(CompetitionFromDb comp : compRepo.getAllCompetitions()) {
                        if(comp.getCategory().equals(cat)) {
                            compRepo.close();
                            Intent intent = new Intent(getApplicationContext(), CompetitionResultsActivity.class);
                            intent.putExtra("competitionId", comp.getId());
                            startActivity(intent);
                            return;
                        }
                    }
                    compRepo.close();
                }
            }
        });
    }

    private static final String SHOW_DUELS = "Samo dovoboji";
    private static final String SHOW_COMPETITIONS = "Samo natjecanja";
    private static final String SHOW_ALL = "Prikaži sve";

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_template, menu);
        menu.add(SHOW_ALL);
        menu.add(SHOW_DUELS);
        menu.add(SHOW_COMPETITIONS);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String name = item.getTitle().toString();
        switch (name) {
            case SHOW_DUELS:
                showDuels = true;
                showCompetitions = false;
                createList();
                break;

            case SHOW_COMPETITIONS:
                showDuels = false;
                showCompetitions = true;
                createList();
                break;

            case SHOW_ALL:
                showDuels = true;
                showCompetitions = true;
                createList();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

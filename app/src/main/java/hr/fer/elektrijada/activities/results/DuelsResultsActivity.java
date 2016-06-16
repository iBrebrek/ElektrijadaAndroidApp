package hr.fer.elektrijada.activities.results;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import hr.fer.elektrijada.MenuHandler;
import hr.fer.elektrijada.R;
import hr.fer.elektrijada.activities.BaseMenuActivity;
import hr.fer.elektrijada.activities.scores.duels.DuelScoresActivity;
import hr.fer.elektrijada.dal.sql.category.CategoryFromDb;
import hr.fer.elektrijada.dal.sql.category.SqlCategoryRepository;
import hr.fer.elektrijada.dal.sql.duel.DuelFromDb;
import hr.fer.elektrijada.dal.sql.duel.SqlDuelRepository;
import hr.fer.elektrijada.dal.sql.duelscore.DuelScoreFromDb;
import hr.fer.elektrijada.dal.sql.duelscore.SqlDuelScoreRepository;

/**
 * Created by 404 on 3.6.2016..
 */
public class DuelsResultsActivity extends BaseMenuActivity {

    CategoryFromDb cat;

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

        int catId = getIntent().getIntExtra("categoryId", -1);
        SqlCategoryRepository categoryRepository = new SqlCategoryRepository(this);
        cat = categoryRepository.getCategory(catId);
        categoryRepository.close();
        setTitle(cat.getName());
    }

    @Override
    protected void onResume() {
        super.onResume();
        final List<DuelFromDb> duels = new ArrayList<>();

        SqlDuelRepository duelRepository = new SqlDuelRepository(this);
        for(DuelFromDb duel : duelRepository.getAllDuels()) {
            if(duel.getCategory().equals(cat)) {
                duels.add(duel);
            }
        }

        ListView listView = (ListView) findViewById(R.id.activity_results_list);
        listView.setAdapter(new DuelResultsAdapter(this, duels));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), DuelScoresActivity.class);
                intent.putExtra("duelId", duels.get(position).getId());
                startActivity(intent);
            }
        });
    }
}

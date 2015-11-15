package hr.fer.elektrijada.activities.competitions;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import hr.fer.elektrijada.R;
import hr.fer.elektrijada.dal.mock.competitions.MockCompetitionsRepository;
import hr.fer.elektrijada.model.competitions.CompetitionsEntry;

public class CompetitionsActivity extends Activity {

    private static final int REQUEST_ADD = 1;
    private MockCompetitionsRepository mockCompetitionsRepository = new MockCompetitionsRepository();
    private ArrayList <String> competitionsList = new ArrayList<>();
    CompetitionsEntry competitionsEntry;
    ArrayAdapter <String> competitionsArrayAdapter;
    ListView competitionsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_competitions);
        competitionsListView = (ListView) findViewById(R.id.competitionsListView);
    }

    public void addCompetition (View view){
        Intent intent = new Intent(CompetitionsActivity.this, NewCompetitionActivity.class);
        startActivityForResult(intent, REQUEST_ADD);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            if (requestCode == REQUEST_ADD){
                competitionsEntry = (CompetitionsEntry) data.getSerializableExtra("New comp");
                mockCompetitionsRepository.createCompetitionsEntry(competitionsEntry);
                competitionsList.add( competitionsEntry.getLocation());
                competitionsArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, competitionsList);
                competitionsListView.setAdapter(competitionsArrayAdapter);
            }
        }
    }
}

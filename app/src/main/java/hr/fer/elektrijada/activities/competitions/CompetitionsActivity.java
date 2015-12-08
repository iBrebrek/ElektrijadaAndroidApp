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
import hr.fer.elektrijada.activities.BaseMenuActivity;
import hr.fer.elektrijada.dal.mock.competitions.MockCompetitionsRepository;
import hr.fer.elektrijada.model.competitions.CompetitionsEntry;

public class CompetitionsActivity extends BaseMenuActivity {

    private static final int REQUEST_ADD = 1;
    private static final String NEW_COMPETITION = "Dodaj natjecanje" ;
    private MockCompetitionsRepository mockCompetitionsRepository = new MockCompetitionsRepository();
    private ArrayList <String> competitionsList = new ArrayList<>();
    CompetitionsEntry competitionsEntry;
    ArrayAdapter <String> competitionsArrayAdapter;
    ListView competitionsListView;

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_competitions;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        competitionsListView = (ListView) findViewById(R.id.competitionsListView);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_start, menu);
        menu.add(NEW_COMPETITION);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (item.getTitle().equals(NEW_COMPETITION)) {
            Intent intent = new Intent(CompetitionsActivity.this, NewCompetitionActivity.class);
            startActivityForResult(intent, REQUEST_ADD);
        }


        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

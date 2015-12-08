package hr.fer.elektrijada.activities.teams;


import android.app.Activity;
import android.content.Intent;

import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;

import android.widget.ListView;


import java.util.ArrayList;

import hr.fer.elektrijada.R;
import hr.fer.elektrijada.activities.BaseMenuActivity;
import hr.fer.elektrijada.dal.mock.teams.MockTeamsRepository;
import hr.fer.elektrijada.model.teams.TeamsEntry;

public class TeamsActivity extends BaseMenuActivity {


    private static final String NEW_TEAM = "Dodaj ekipu";
    private static final int REQUEST_ADD = 1;
    private MockTeamsRepository mockTeamsRepository = new MockTeamsRepository();
    private ArrayList <String> teamsList = new ArrayList<>();
    ListView teamsListView;
    TeamsEntry teamsEntry;
    ArrayAdapter <String> teamsArrayAdapter;

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_teams;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        teamsListView = (ListView) findViewById(R.id.teamsListView);

    }

    public void addTeam(View view){
        Intent intent = new Intent(TeamsActivity.this, NewTeamActivity.class);
        startActivityForResult(intent, REQUEST_ADD);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_ADD) {
                teamsEntry = (TeamsEntry) data.getSerializableExtra("New team");
                mockTeamsRepository.createTeamsEntry(teamsEntry);
                teamsList.add(teamsEntry.getName());
                teamsArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, teamsList);
                teamsListView.setAdapter(teamsArrayAdapter);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_start, menu);
        menu.add(NEW_TEAM);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (item.getTitle().equals(NEW_TEAM)) {
            Intent intent = new Intent(TeamsActivity.this, NewTeamActivity.class);
            startActivityForResult(intent, REQUEST_ADD);
        }


        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

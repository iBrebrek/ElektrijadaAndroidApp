package hr.fer.elektrijada.activities.teams;


import android.app.Activity;
import android.content.Intent;

import android.os.Bundle;

import android.view.View;
import android.widget.ArrayAdapter;

import android.widget.ListView;


import java.util.ArrayList;

import hr.fer.elektrijada.R;
import hr.fer.elektrijada.dal.mock.teams.MockTeamsRepository;
import hr.fer.elektrijada.model.teams.TeamsEntry;

public class TeamsActivity extends Activity {



    private static final int REQUEST_ADD = 1;
    private MockTeamsRepository mockTeamsRepository = new MockTeamsRepository();
    private ArrayList <String> teamsList = new ArrayList<>();
    ListView teamsListView;
    TeamsEntry teamsEntry;
    ArrayAdapter <String> teamsArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teams);
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
}

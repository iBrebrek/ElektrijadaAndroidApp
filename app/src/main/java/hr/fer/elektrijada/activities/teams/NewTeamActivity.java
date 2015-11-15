package hr.fer.elektrijada.activities.teams;

import android.app.Activity;
import android.content.Intent;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;


import hr.fer.elektrijada.R;
import hr.fer.elektrijada.model.teams.TeamsEntry;

public class NewTeamActivity extends Activity {


    EditText teamName;
    Spinner facultySpinner;
    ArrayAdapter <String> facultyAdapter;
    String[] facultyNames = {"FER", "FESB", "TVZ"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_team);
        teamName = (EditText) findViewById(R.id.textEditTeamName);
        facultySpinner = (Spinner) findViewById(R.id.facultySpinner);
        facultyAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, facultyNames);
        facultyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        facultySpinner.setAdapter(facultyAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_team, menu);
        return true;
    }

    public void saveTeam(View view){
        Intent intent = new Intent();
        TeamsEntry teamsEntry = new TeamsEntry(1, teamName.getText().toString(), "", 1, 1, 1  );
        intent.putExtra("New team", teamsEntry);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

package hr.fer.elektrijada.activities.teams;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;


import hr.fer.elektrijada.R;
import hr.fer.elektrijada.activities.BaseMenuActivity;
import hr.fer.elektrijada.model.teams.TeamsEntry;

public class NewTeamActivity extends BaseMenuActivity {

    private static final String SAVE_TEAM = "Spremi";
    private static final String CANCEL = "Odustani";
    EditText teamName;
    Spinner facultySpinner;
    ArrayAdapter <String> facultyAdapter;
    String[] facultyNames = {"FER", "FESB", "TVZ"};

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_new_team;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
        menu.add(SAVE_TEAM);
        menu.add(CANCEL);
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

        if (item.getTitle().equals(SAVE_TEAM)){
            saveAndExit();
        }

        else if (item.getTitle().equals(CANCEL)){
            finish();
        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void saveAndExit (){
        Intent intent = new Intent();
        TeamsEntry teamsEntry = new TeamsEntry(1, teamName.getText().toString(), "", 1, 1, 1  );
        intent.putExtra("New team", teamsEntry);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Zatvaranje prozora")
                .setMessage("Želite li spremiti prije izlaska?")
                .setPositiveButton("Da", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        saveAndExit();
                    }

                })
                .setNegativeButton("Ne", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNeutralButton("Odustani", null)
                .show();
    }
}

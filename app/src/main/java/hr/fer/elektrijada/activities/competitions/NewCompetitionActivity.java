package hr.fer.elektrijada.activities.competitions;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import hr.fer.elektrijada.R;
import hr.fer.elektrijada.activities.BaseMenuActivity;
import hr.fer.elektrijada.model.competitions.CompetitionsEntry;

public abstract class NewCompetitionActivity extends BaseMenuActivity {

    private static final String SAVE = "Spremi";
    private static final String QUIT = "Odustani";
    String categories[] = {"Nogomet", "Košarka", "Tenis"};
    EditText location;
    Spinner categorySpinner;
    ArrayAdapter<String> categoryAdapter;
    CheckBox checkBox;

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_new_competition;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        location = (EditText) findViewById(R.id.textEditLocation);
        categorySpinner = (Spinner) findViewById(R.id.categorySpinner);
        categoryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(categoryAdapter);
        checkBox = (CheckBox) findViewById(R.id.competitionAssumptionCheckBox);
    }

    public void saveCompetition (){
        Intent intent = new Intent();
        CompetitionsEntry competitionsEntry = new CompetitionsEntry(1,checkBox.isEnabled(), location.getText().toString(),
                1, null, null);
        intent.putExtra("New comp", competitionsEntry);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_template, menu);
        menu.add(SAVE);
        menu.add(QUIT);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (item.getTitle().equals(SAVE)) {
            saveCompetition();
        }

        else if (item.getTitle().equals(QUIT)){
            finish();
        }


        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
                        saveCompetition();
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

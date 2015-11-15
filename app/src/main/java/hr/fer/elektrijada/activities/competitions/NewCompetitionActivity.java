package hr.fer.elektrijada.activities.competitions;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import hr.fer.elektrijada.R;
import hr.fer.elektrijada.model.competitions.CompetitionsEntry;

public class NewCompetitionActivity extends Activity {

    String categories[] = {"Nogomet", "Košarka", "Tenis"};
    EditText location;
    Spinner categorySpinner;
    ArrayAdapter<String> categoryAdapter;
    CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_competition);
        location = (EditText) findViewById(R.id.textEditLocation);
        categorySpinner = (Spinner) findViewById(R.id.categorySpinner);
        categoryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(categoryAdapter);
        checkBox = (CheckBox) findViewById(R.id.competitionAssumptionCheckBox);
    }

    public void saveCompetition (View view){
        Intent intent = new Intent();
        CompetitionsEntry competitionsEntry = new CompetitionsEntry(1,checkBox.isEnabled(), location.getText().toString(),
                1, null, null);
        intent.putExtra("New comp", competitionsEntry);
        setResult(RESULT_OK, intent);
        finish();
    }

}

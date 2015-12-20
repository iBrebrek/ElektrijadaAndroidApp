package hr.fer.elektrijada.activities.events;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import hr.fer.elektrijada.R;
import hr.fer.elektrijada.activities.BaseMenuActivity;
import hr.fer.elektrijada.dal.sql.category.SqlCategoryRepository;
import hr.fer.elektrijada.dal.sql.competition.SqlCompetitionRepository;
import hr.fer.elektrijada.dal.sql.competitor.SqlCompetitorRepository;
import hr.fer.elektrijada.dal.sql.duel.SqlDuelRepository;
import hr.fer.elektrijada.util.DatePicker;
import hr.fer.elektrijada.util.TimePicker;

/**
 * Created by Ivica Brebrek
 */
public class CreateNewEventActivity extends BaseMenuActivity {
    private boolean isSport;

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_create_new_event;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initCategorySpinner();
        initTeamSpinners();
        RememberPickedDetails.location = (EditText) findViewById(R.id.editTextCreateEventLocation);
        initTimeAndDate();
        RememberPickedDetails.hasEnd = (CheckBox) findViewById(R.id.createEventHasEnd);
        RememberPickedDetails.isAssumption = (CheckBox) findViewById(R.id.createEventIsAssumption);
    }

    private void initCategorySpinner() {
        SqlCategoryRepository repo = new SqlCategoryRepository(this);
        final ArrayList<SqlCategoryRepository.CategoryFromDb> categories = repo.getAllCategories();
        repo.close();
        ArrayList<String> names = new ArrayList<>();
        for(SqlCategoryRepository.CategoryFromDb category:categories) {
            names.add(category.toString());
        }

        Spinner dropdown = (Spinner) findViewById(R.id.spinnerCreateEventPickCategory);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, names);
        dropdown.setAdapter(adapter);
        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                RememberPickedDetails.categoryId = categories.get(position).getId();
                isSport = categories.get(position).isSport();
                if (isSport) {
                    findViewById(R.id.createEventPickTeams).setVisibility(View.VISIBLE);
                } else {
                    findViewById(R.id.createEventPickTeams).setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void initTeamSpinners() {
        SqlCompetitorRepository repo = new SqlCompetitorRepository(this);
        final HashMap<String, Integer> allCompetitors = repo.getMapOfCompetitors();
        repo.close();
        final ArrayList<String> listOfNames = new ArrayList<>(allCompetitors.keySet());
        Collections.sort(listOfNames);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, listOfNames);
        Spinner firstComp = (Spinner) findViewById(R.id.spinnerCreateEventFirstTeam);
        Spinner secondComp = (Spinner) findViewById(R.id.spinnerCreateEventSecondTeam);
        firstComp.setAdapter(adapter);
        secondComp.setAdapter(adapter);
        firstComp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String name = listOfNames.get(position);
                RememberPickedDetails.firstId = allCompetitors.get(name);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        secondComp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String name = listOfNames.get(position);
                RememberPickedDetails.secondId = allCompetitors.get(name);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void initTimeAndDate(){
        Button button;

        button = (Button) findViewById(R.id.btnCreateEventStartDate);
        RememberPickedDetails.startDate = new DatePicker<>(getFragmentManager(), button);

        button = (Button) findViewById(R.id.btnCreateEventStartTime);
        RememberPickedDetails.startTime = new TimePicker<>(getFragmentManager(), button);

        button = (Button) findViewById(R.id.btnCreateEventEndDate);
        RememberPickedDetails.endDate = new DatePicker<>(getFragmentManager(), button);

        button = (Button) findViewById(R.id.btnCreateEventEndTime);
        RememberPickedDetails.endTime = new TimePicker<>(getFragmentManager(), button);
    }

    private void saveAndExit() {
        //TODO: provijeriti gdje se treba spremiti, lokalno ili online

        String startTime = RememberPickedDetails.startDate.toString() + " " + RememberPickedDetails.startTime.toString()+":00"; //00 su sekunde
        String endTime = RememberPickedDetails.hasEnd.isChecked() ? RememberPickedDetails.endDate.toString() + " " + RememberPickedDetails.endTime.toString()+":00" : null;
        String location = RememberPickedDetails.location!=null ? RememberPickedDetails.location.getText().toString() : null;
        if(isSport) {
            SqlDuelRepository repoDuel = new SqlDuelRepository(getApplicationContext());
            repoDuel.createNewDuel(new SqlDuelRepository.DuelFromDb(
                    startTime,
                    endTime,
                    RememberPickedDetails.categoryId,
                    RememberPickedDetails.firstId,
                    RememberPickedDetails.secondId,
                    location,
                    RememberPickedDetails.isAssumption.isChecked()
            ));
            repoDuel.close();
        } else {
            SqlCompetitionRepository repoComp = new SqlCompetitionRepository(getApplicationContext());
            repoComp.createNewCompetition(new SqlCompetitionRepository.CompetitionFromDb(
                    startTime,
                    endTime,
                    RememberPickedDetails.categoryId,
                    location,
                    RememberPickedDetails.isAssumption.isChecked()
            ));
            repoComp.close();
        }
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

    private static class RememberPickedDetails{
        private static int categoryId;
        private static int firstId;
        private static int secondId;
        private static EditText location;
        private static CheckBox hasEnd;
        private static DatePicker startDate;
        private static DatePicker endDate;
        private static TimePicker startTime;
        private static TimePicker endTime;
        private static CheckBox isAssumption;
    }

}

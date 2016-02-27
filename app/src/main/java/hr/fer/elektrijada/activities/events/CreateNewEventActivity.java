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
import android.widget.Toast;

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
    private boolean isDuel = true; //da se zna u koju tablicu spremamo

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_create_new_event;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //initRadioButtons();
        initCategorySpinner();
        initTeamSpinners();
        RememberPickedDetails.location = (EditText) findViewById(R.id.editTextCreateEventLocation);
        initTimeAndDate();
        //RememberPickedDetails.isAssumption = (CheckBox) findViewById(R.id.createEventIsAssumption);
    }

//    private void initRadioButtons() {
//        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioCreateEventPickType);
//        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                switch (checkedId) {
//                    case R.id.radioCreateEventDuel:
//                        isDuel = true;
//                        findViewById(R.id.createEventPickTeams).setVisibility(View.VISIBLE);
//                        break;
//                    case R.id.radioCreateEventCompetition:
//                        isDuel = false;
//                        findViewById(R.id.createEventPickTeams).setVisibility(View.GONE);
//                        break;
//                }
//
//            }
//        });
//    }

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
                SqlCategoryRepository.CategoryFromDb category = categories.get(position);
                RememberPickedDetails.categoryId = category.getId();
                if (category.isDuel()) {
                    findViewById(R.id.createEventPickTeams).setVisibility(View.VISIBLE);
                    isDuel = true;
                } else {
                    findViewById(R.id.createEventPickTeams).setVisibility(View.GONE);
                    isDuel = false;
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
        RememberPickedDetails.startDate = new DatePicker<>(
                getFragmentManager(),
                (Button) findViewById(R.id.btnCreateEventStartDate)
        );
        RememberPickedDetails.startTime = new TimePicker<>(
                getFragmentManager(),
                (Button) findViewById(R.id.btnCreateEventStartTime)
        );
        RememberPickedDetails.endDate = new DatePicker<>(
                getFragmentManager(),
                (Button) findViewById(R.id.btnCreateEventEndDate)
        );
        RememberPickedDetails.endTime = new TimePicker<>(
                getFragmentManager(),
                (Button) findViewById(R.id.btnCreateEventEndTime)
        );
        RememberPickedDetails.hasEnd = (CheckBox) findViewById(R.id.createEventHasEnd);
        RememberPickedDetails.hasEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(RememberPickedDetails.hasEnd.isChecked()) {
                    findViewById(R.id.btnCreateEventEndDate).setVisibility(View.VISIBLE);
                    findViewById(R.id.btnCreateEventEndTime).setVisibility(View.VISIBLE);
                } else {
                    findViewById(R.id.btnCreateEventEndDate).setVisibility(View.INVISIBLE);
                    findViewById(R.id.btnCreateEventEndTime).setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    private void saveAndExit() {
        String startTime = RememberPickedDetails.startDate.toStringYearFirst() + " "
                           + RememberPickedDetails.startTime.toString()+":00"; //00 su sekunde
        String endTime = RememberPickedDetails.hasEnd.isChecked()
                         ? RememberPickedDetails.endDate.toStringYearFirst() + " "
                           + RememberPickedDetails.endTime.toString()+":00"
                         : null;
        String location = RememberPickedDetails.location!=null ? RememberPickedDetails.location.getText().toString() : null;

        try {
            //koristi se try jer se nesmije dozvoliti da je kraj prije pocetka
            if (isDuel) {
                SqlDuelRepository repoDuel = new SqlDuelRepository(getApplicationContext());
                repoDuel.createNewDuel(new SqlDuelRepository.DuelFromDb(
                        startTime,
                        endTime,
                        RememberPickedDetails.categoryId,
                        RememberPickedDetails.firstId,
                        RememberPickedDetails.secondId,
                        location,
                        false  //RememberPickedDetails.isAssumption.isChecked()
                ));
                repoDuel.close();
            } else {
                SqlCompetitionRepository repoComp = new SqlCompetitionRepository(getApplicationContext());
                repoComp.createNewCompetition(new SqlCompetitionRepository.CompetitionFromDb(
                        startTime,
                        endTime,
                        RememberPickedDetails.categoryId,
                        location,
                        false  //RememberPickedDetails.isAssumption.isChecked()
                ));
                repoComp.close();
            }
        } catch (IllegalArgumentException exc) {
            Toast.makeText(getApplicationContext(), exc.getMessage(), Toast.LENGTH_LONG).show();
            return;
        }
        finish();
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle(R.string.dialog_onbackpressed_title)
                .setMessage(R.string.dialog_onbackpressed_message)
                .setPositiveButton(R.string.dialog_onbackpressed_postive, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        saveAndExit();
                    }

                })
                .setNegativeButton(R.string.dialog_onbackpressed_negative, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNeutralButton(R.string.dialog_onbackpressed_cancel, null)
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
        //private static CheckBox isAssumption;
    }

}

package hr.fer.elektrijada.activities.events;

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
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.HashMap;

import hr.fer.elektrijada.MenuHandler;
import hr.fer.elektrijada.R;
import hr.fer.elektrijada.activities.SaveBeforeExitActivity;
import hr.fer.elektrijada.dal.sql.category.SqlCategoryRepository;
import hr.fer.elektrijada.dal.sql.competition.SqlCompetitionRepository;
import hr.fer.elektrijada.dal.sql.competitor.SqlCompetitorRepository;
import hr.fer.elektrijada.dal.sql.duel.SqlDuelRepository;
import hr.fer.elektrijada.dal.sql.stage.SqlStageRepository;
import hr.fer.elektrijada.util.DateParserUtil;
import hr.fer.elektrijada.util.DatePicker;
import hr.fer.elektrijada.util.TimePicker;

/**
 * Created by Ivica Brebrek
 */
public class CreateNewEventActivity extends SaveBeforeExitActivity {
    private boolean isDuel = true; //da se zna u koju tablicu spremamo
    //stage i teams su tu jer su oni prikazani samo ako je kategorija duel
    private View teams;
    private Spinner stageSpinner;
    Spinner categorySpinner;
    Spinner firstCompSpinner;
    Spinner secondCompSpinner;

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_create_new_event;
    }

    @Override
    protected int belongingToMenuItemId() {
        return MenuHandler.EVENTS_ID;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        teams = findViewById(R.id.createEventPickTeams);
        stageSpinner = (Spinner) findViewById(R.id.spinnerCreateEventPickStage);
        categorySpinner = (Spinner) findViewById(R.id.spinnerCreateEventPickCategory);
        firstCompSpinner = (Spinner) findViewById(R.id.spinnerCreateEventFirstTeam);
        secondCompSpinner = (Spinner) findViewById(R.id.spinnerCreateEventSecondTeam);

        //initRadioButtons();
        initCategorySpinner();
        initStageSpinner();
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

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, names);
        categorySpinner.setAdapter(adapter);
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SqlCategoryRepository.CategoryFromDb category = categories.get(position);
                RememberPickedDetails.categoryId = category.getId();
                if (category.isDuel()) {
                    teams.setVisibility(View.VISIBLE);
                    stageSpinner.setVisibility(View.VISIBLE);
                    isDuel = true;
                } else {
                    teams.setVisibility(View.GONE);
                    stageSpinner.setVisibility(View.GONE);
                    isDuel = false;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void initStageSpinner() {
        SqlStageRepository repo = new SqlStageRepository(this);
        final ArrayList<SqlStageRepository.StageFromDb> stages = repo.getAllStages();
        repo.close();
        ArrayList<String> names = new ArrayList<>();
        for(SqlStageRepository.StageFromDb stage:stages) {
            names.add(stage.toString());
        }

        Spinner dropdownCategory = (Spinner) findViewById(R.id.spinnerCreateEventPickStage);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, names);
        dropdownCategory.setAdapter(adapter);
        dropdownCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SqlStageRepository.StageFromDb stage = stages.get(position);
                RememberPickedDetails.stageId = stage.getId();
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
        firstCompSpinner.setAdapter(adapter);
        secondCompSpinner.setAdapter(adapter);
        firstCompSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String name = listOfNames.get(position);
                RememberPickedDetails.firstId = allCompetitors.get(name);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        secondCompSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

    @Override
    protected void save() {
        try {
            //koristi se try jer se nesmije dozvoliti da je kraj prije pocetka
            if (isDuel) {
                SqlDuelRepository.DuelFromDb duel = RememberPickedDetails.getDuel();
                SqlDuelRepository repoDuel = new SqlDuelRepository(getApplicationContext());
                if(isUpdate) {
                    duel.setId(getIntent().getIntExtra("event_id", -1));
                    repoDuel.updateDuel(duel);
                } else {
                    repoDuel.createNewDuel(duel);
                }
                repoDuel.close();
            } else {
                SqlCompetitionRepository.CompetitionFromDb competition = RememberPickedDetails.getCompetition();
                SqlCompetitionRepository repoComp = new SqlCompetitionRepository(getApplicationContext());
                if(isUpdate) {
                    competition.setId(getIntent().getIntExtra("event_id", -1));
                    repoComp.updateCompetition(competition);
                } else {
                    repoComp.createNewCompetition(competition);
                }
                repoComp.close();
            }
        } catch (IllegalArgumentException exc) {
            Toast.makeText(getApplicationContext(), exc.getMessage(), Toast.LENGTH_LONG).show();
            return;
        }
    }

    private static class RememberPickedDetails{
        private static int categoryId;
        private static int firstId;
        private static int secondId;
        private static int stageId;
        private static EditText location;
        private static CheckBox hasEnd;
        private static DatePicker startDate;
        private static DatePicker endDate;
        private static TimePicker startTime;
        private static TimePicker endTime;
        //private static CheckBox isAssumption;

        private static SqlDuelRepository.DuelFromDb getDuel() {
            String startTime = RememberPickedDetails.startDate.toStringYearFirst() + " "
                    + RememberPickedDetails.startTime.toString()+":00"; //00 su sekunde
            String endTime = RememberPickedDetails.hasEnd.isChecked()
                    ? RememberPickedDetails.endDate.toStringYearFirst() + " "
                    + RememberPickedDetails.endTime.toString()+":00"
                    : null;
            String location = RememberPickedDetails.location!=null ? RememberPickedDetails.location.getText().toString() : null;

            return new SqlDuelRepository.DuelFromDb(
                    startTime,
                    endTime,
                    RememberPickedDetails.categoryId,
                    RememberPickedDetails.firstId,
                    RememberPickedDetails.secondId,
                    RememberPickedDetails.stageId,
                    location,
                    false  //RememberPickedDetails.isAssumption.isChecked()
            );
        }

        private static SqlCompetitionRepository.CompetitionFromDb getCompetition() {
            String startTime = RememberPickedDetails.startDate.toStringYearFirst() + " "
                    + RememberPickedDetails.startTime.toString()+":00"; //00 su sekunde
            String endTime = RememberPickedDetails.hasEnd.isChecked()
                    ? RememberPickedDetails.endDate.toStringYearFirst() + " "
                    + RememberPickedDetails.endTime.toString()+":00"
                    : null;
            String location = RememberPickedDetails.location!=null ? RememberPickedDetails.location.getText().toString() : null;

            return new SqlCompetitionRepository.CompetitionFromDb(
                    startTime,
                    endTime,
                    RememberPickedDetails.categoryId,
                    location,
                    false  //RememberPickedDetails.isAssumption.isChecked()
            );
        }
    }

    private boolean isUpdate = false;

    protected void setEvent (int categoryId, int stageId, int firstId, int secondId, String location, String from, String to) {
        isUpdate = true;

        //namjesti category spinner
        SqlCategoryRepository catRepo = new SqlCategoryRepository(this);
        categorySpinner.setSelection(catRepo.getAllCategories().indexOf(catRepo.getCategory(categoryId)));
        catRepo.close();

        if(isDuel) {
            //namjesti stage spinner
            SqlStageRepository stageRepo = new SqlStageRepository(this);
            stageSpinner.setSelection(stageRepo.getAllStages().indexOf(stageRepo.getStage(stageId)));
            stageRepo.close();

            //namjesti spinner za oba tima
            SqlCompetitorRepository competitorRepository = new SqlCompetitorRepository(this);
            HashMap<String, Integer> allCompetitors = competitorRepository.getMapOfCompetitors();
            String first = competitorRepository.getCompetitorName(firstId);
            String second = competitorRepository.getCompetitorName(secondId);
            competitorRepository.close();
            ArrayList<String> listOfNames = new ArrayList<>(allCompetitors.keySet());
            Collections.sort(listOfNames);
            firstCompSpinner.setSelection(listOfNames.indexOf(first));
            secondCompSpinner.setSelection(listOfNames.indexOf(second));
        }

        //lokacija...
        RememberPickedDetails.location.setText(location);

        //pocetak dogadaja
        Calendar start = new GregorianCalendar();
        start.setTime(DateParserUtil.stringToDate(from));
        RememberPickedDetails.startDate.setDate(start.get(Calendar.DAY_OF_MONTH), start.get(Calendar.MONTH)+1, start.get(Calendar.YEAR));
        RememberPickedDetails.startTime.setTime(start.get(Calendar.HOUR_OF_DAY), start.get(Calendar.MINUTE));

        //kraj dogadaja
        if(to != null) {
            Calendar end = new GregorianCalendar();
            end.setTime(DateParserUtil.stringToDate(to));
            RememberPickedDetails.endDate.setDate(end.get(Calendar.DAY_OF_MONTH), end.get(Calendar.MONTH)+1, end.get(Calendar.YEAR));
            RememberPickedDetails.endTime.setTime(end.get(Calendar.HOUR_OF_DAY), end.get(Calendar.MINUTE));
        } else {
            RememberPickedDetails.hasEnd.setChecked(false);
            findViewById(R.id.btnCreateEventEndDate).setVisibility(View.INVISIBLE);
            findViewById(R.id.btnCreateEventEndTime).setVisibility(View.INVISIBLE);
        }
    }
}

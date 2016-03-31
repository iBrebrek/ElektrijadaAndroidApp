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
import hr.fer.elektrijada.dal.sql.category.CategoryFromDb;
import hr.fer.elektrijada.dal.sql.category.SqlCategoryRepository;
import hr.fer.elektrijada.dal.sql.competition.CompetitionFromDb;
import hr.fer.elektrijada.dal.sql.competition.SqlCompetitionRepository;
import hr.fer.elektrijada.dal.sql.competitor.CompetitorFromDb;
import hr.fer.elektrijada.dal.sql.competitor.SqlCompetitorRepository;
import hr.fer.elektrijada.dal.sql.duel.DuelFromDb;
import hr.fer.elektrijada.dal.sql.duel.SqlDuelRepository;
import hr.fer.elektrijada.dal.sql.stage.SqlStageRepository;
import hr.fer.elektrijada.dal.sql.stage.StageFromDb;
import hr.fer.elektrijada.util.DateParserUtil;
import hr.fer.elektrijada.util.DatePicker;
import hr.fer.elektrijada.util.TimePicker;

/**
 * Aktivnost u kojoj se radi novi event,
 *
 * Trenuta realizacija ovog razreda nije bas lijepa jer sam dodavao neke stvari
 * koje nemaju veze s novim vec s editom postojeceg
 * (jer EditEventActivity nasljeduje ovaj razred)
 * Jednom kad cu imat vise vremena cu izdvojit zajednicki kostur u novi apstraktni razred
 * kojeg ce onda naslijedit CreateNewEventActivity i EditEventActivity, tad ce ovo bit puno preglednije
 *
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

    RememberPickedDetails rememberPickedDetails = new RememberPickedDetails();

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
        rememberPickedDetails.location = (EditText) findViewById(R.id.editTextCreateEventLocation);
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
        final ArrayList<CategoryFromDb> categories = repo.getAllCategories();
        repo.close();
        ArrayList<String> names = new ArrayList<>();
        for(CategoryFromDb category:categories) {
            names.add(category.toString());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, names);
        categorySpinner.setAdapter(adapter);
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                CategoryFromDb category = categories.get(position);
                rememberPickedDetails.categoryId = category.getId();
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
        final ArrayList<StageFromDb> stages = repo.getAllStages();
        repo.close();
        ArrayList<String> names = new ArrayList<>();
        for(StageFromDb stage:stages) {
            names.add(stage.toString());
        }

        Spinner dropdownCategory = (Spinner) findViewById(R.id.spinnerCreateEventPickStage);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, names);
        dropdownCategory.setAdapter(adapter);
        dropdownCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                StageFromDb stage = stages.get(position);
                rememberPickedDetails.stageId = stage.getId();
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
                rememberPickedDetails.firstId = allCompetitors.get(name);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        secondCompSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String name = listOfNames.get(position);
                rememberPickedDetails.secondId = allCompetitors.get(name);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void initTimeAndDate(){
        rememberPickedDetails.startDate = new DatePicker<>(
                getFragmentManager(),
                (Button) findViewById(R.id.btnCreateEventStartDate)
        );
        rememberPickedDetails.startTime = new TimePicker<>(
                getFragmentManager(),
                (Button) findViewById(R.id.btnCreateEventStartTime)
        );
        rememberPickedDetails.endDate = new DatePicker<>(
                getFragmentManager(),
                (Button) findViewById(R.id.btnCreateEventEndDate)
        );
        rememberPickedDetails.endTime = new TimePicker<>(
                getFragmentManager(),
                (Button) findViewById(R.id.btnCreateEventEndTime)
        );
        rememberPickedDetails.hasEnd = (CheckBox) findViewById(R.id.createEventHasEnd);
        rememberPickedDetails.hasEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rememberPickedDetails.hasEnd.isChecked()) {
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
    protected boolean save() {
        try {
            //koristi se try jer se nesmije dozvoliti da je kraj prije pocetka
            if (isDuel) {
                DuelFromDb duel = rememberPickedDetails.getDuel();
                SqlDuelRepository repoDuel = new SqlDuelRepository(getApplicationContext());
                if(isUpdate) {
                    duel.setId(getIntent().getIntExtra("event_id", -1));
                    repoDuel.updateDuel(duel);
                } else {
                    repoDuel.createNewDuel(duel);
                }
                repoDuel.close();
            } else {
                CompetitionFromDb competition = rememberPickedDetails.getCompetition();
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
            return false;
        }
        return true;
    }

    private class RememberPickedDetails{
        private int categoryId;
        private int firstId;
        private int secondId;
        private int stageId;
        private EditText location;
        private CheckBox hasEnd;
        private DatePicker startDate;
        private DatePicker endDate;
        private TimePicker startTime;
        private TimePicker endTime;
        //private CheckBox isAssumption;

        private DuelFromDb getDuel() {
            String startTime = startDate.toStringYearFirst() + " "
                    + this.startTime.toString()+":00"; //00 su sekunde
            String endTime = hasEnd.isChecked()
                    ? endDate.toStringYearFirst() + " "
                    + this.endTime.toString()+":00"
                    : null;
            String location = this.location!=null ? this.location.getText().toString() : null;

            return new DuelFromDb(
                    startTime,
                    endTime,
                    getCategory(categoryId),
                    getCompetitor(firstId),
                    getCompetitor(secondId),
                    getStage(stageId),
                    location,
                    false  //isAssumption.isChecked()
            );
        }

        private CompetitionFromDb getCompetition() {
            String startTime = startDate.toStringYearFirst() + " "
                    + this.startTime.toString()+":00"; //00 su sekunde
            String endTime = hasEnd.isChecked()
                    ? endDate.toStringYearFirst() + " "
                    + this.endTime.toString()+":00"
                    : null;
            String location = this.location!=null ? this.location.getText().toString() : null;

            return new CompetitionFromDb(
                    startTime,
                    endTime,
                    getCategory(categoryId),
                    location,
                    false  //isAssumption.isChecked()
            );
        }
    }

    private boolean isUpdate = false;

    protected void setEvent (int categoryId, int stageId, int firstId, int secondId, String location, String from, String to) {
        isUpdate = true;

        categorySpinner.setEnabled(false); //TODO:
        /*
        mozda maknuti ovo kad nadem dobro rjesenje.
        Trenutno je zeznuto mjenjati kategoriju jer su to skzor druge tablice,
        a stubView zeza kad se vrati iz edita u pregled ako smo presli s duela na znanje, il obrnuto
         */

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
        rememberPickedDetails.location.setText(location);

        //pocetak dogadaja
        Calendar start = new GregorianCalendar();
        start.setTime(DateParserUtil.stringToDate(from));
        rememberPickedDetails.startDate.setDate(start.get(Calendar.DAY_OF_MONTH), start.get(Calendar.MONTH)+1, start.get(Calendar.YEAR));
        rememberPickedDetails.startTime.setTime(start.get(Calendar.HOUR_OF_DAY), start.get(Calendar.MINUTE));

        //kraj dogadaja
        if(to != null) {
            Calendar end = new GregorianCalendar();
            end.setTime(DateParserUtil.stringToDate(to));
            rememberPickedDetails.endDate.setDate(end.get(Calendar.DAY_OF_MONTH), end.get(Calendar.MONTH)+1, end.get(Calendar.YEAR));
            rememberPickedDetails.endTime.setTime(end.get(Calendar.HOUR_OF_DAY), end.get(Calendar.MINUTE));
        } else {
            rememberPickedDetails.hasEnd.setChecked(false);
            findViewById(R.id.btnCreateEventEndDate).setVisibility(View.INVISIBLE);
            findViewById(R.id.btnCreateEventEndTime).setVisibility(View.INVISIBLE);
        }
    }

    private CategoryFromDb getCategory(int id) {
        SqlCategoryRepository repo = new SqlCategoryRepository(this);
        CategoryFromDb categoryFromDb = repo.getCategory(id);
        repo.close();
        return categoryFromDb;
    }

    private CompetitorFromDb getCompetitor(int id) {
        SqlCompetitorRepository repo = new SqlCompetitorRepository(this);
        CompetitorFromDb competitorFromDb = repo.getCompetitor(id);
        repo.close();
        return competitorFromDb;
    }

    private StageFromDb getStage(int id) {
        SqlStageRepository repo = new SqlStageRepository(this);
        StageFromDb stageFromDb = repo.getStage(id);
        repo.close();
        return stageFromDb;
    }
}

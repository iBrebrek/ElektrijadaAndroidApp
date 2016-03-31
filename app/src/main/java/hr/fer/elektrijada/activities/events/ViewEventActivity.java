package hr.fer.elektrijada.activities.events;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.GregorianCalendar;

import hr.fer.elektrijada.MenuHandler;
import hr.fer.elektrijada.R;
import hr.fer.elektrijada.activities.BaseMenuActivity;
import hr.fer.elektrijada.activities.scores.duels.DuelScoresActivity;
import hr.fer.elektrijada.dal.sql.competition.CompetitionFromDb;
import hr.fer.elektrijada.dal.sql.competition.SqlCompetitionRepository;
import hr.fer.elektrijada.dal.sql.duel.DuelFromDb;
import hr.fer.elektrijada.dal.sql.duel.SqlDuelRepository;
import hr.fer.elektrijada.util.DateParserUtil;
import hr.fer.elektrijada.extras.Favorites;

/**
 * Aktivnost koja prikazuje jedan od dogadaja.
 * Pozivanje ove aktivnosti trenutno se nalazi u oba adaptera (EventsListAdapter i SportEventsListAdapter).
 *
 * Ovo se otvara kada se klikne na jedan od dogadaja u pregledu svih dogadaja, tj., na pocetnoj aktivnosti.
 * (kada se klikne na ime natjecanja(onaj srednji dio... lijevi otvara za edit vremena, a desni za direktni prikaz rezultata)
 *
 * Created by Ivica Brebrek
 */
public class ViewEventActivity extends BaseMenuActivity {

    private int eventId;
    private boolean isDuel;
    private ImageView star;

    @Override
    protected int getContentLayoutId() {
        return R.layout.event_view_activity;
    }

    @Override
    protected int belongingToMenuItemId() {
        return MenuHandler.EVENTS_ID;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        isDuel = getIntent().getBooleanExtra("isDuel", false);
        eventId = getIntent().getIntExtra("event_id", -1);

        if(eventId == -1) { //nebi se trebalo nikad dogoditi
            Toast.makeText(getApplicationContext(), "Greška prilikom otvaranja događaja.", Toast.LENGTH_SHORT).show();
            finish();
        }

        ViewStub stub = (ViewStub) findViewById(R.id.event_view_type_stub);
        if(isDuel) {
            stub.setLayoutResource(R.layout.event_view_stub_duel);
        } else {
            stub.setLayoutResource(R.layout.event_view_stub_competition);
        }
        stub.inflate();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initDisplay();
    }

    private void initDisplay() {
        TextView category = (TextView) findViewById(R.id.event_view_category);
        star = (ImageView) findViewById(R.id.event_view_favorite);
        TextView location = (TextView) findViewById(R.id.event_view_location);
        TextView start = (TextView) findViewById(R.id.event_view_start);
        TextView end = (TextView) findViewById(R.id.event_view_end);
        boolean isFavorite;

        if(isDuel) { //jedina razlika su prve 4 i zadnje 2 linije; i u jednom je varijabla duel u drugom comp
            SqlDuelRepository duelRepo = new SqlDuelRepository(this);
            DuelFromDb duel = duelRepo.getDuel(eventId);
            duelRepo.close();

            duelStub(duel);

            category.setText(duel.getCategory().getName());
            String loc = duel.getLocation();
            if(loc!=null && !loc.isEmpty()) {
                location.setText(duel.getLocation());
            } else {
                location.setText("nepoznata");
            }
            start.setText(getNicerDate(duel.getTimeFrom()));
            String endTime = duel.getTimeTo();
            if(endTime!=null && !endTime.isEmpty()) {
                end.setText(getNicerDate(endTime));
            } else {
                end.setText("neodređen");
            }

            star.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    toogleFavoriteDuel();
                }
            });
            isFavorite = Favorites.isFavorite(this, String.valueOf(eventId), Favorites.FAVORITE_DUELS);

        } else {
            competitionStub();

            SqlCompetitionRepository compRepo = new SqlCompetitionRepository(this);
            CompetitionFromDb comp = compRepo.getCompetition(eventId);
            compRepo.close();

            category.setText(comp.getCategory().getName());
            String loc = comp.getLocation();
            if(loc!=null && !loc.isEmpty()) {
                location.setText(comp.getLocation());
            } else {
                location.setText("nepoznata");
            }
            start.setText(getNicerDate(comp.getTimeFrom()));
            String endTime = comp.getTimeTo();
            if(endTime!=null && !endTime.isEmpty()) {
                end.setText(getNicerDate(endTime));
            } else {
                end.setText("neodređen");
            }

            star.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    toogleFavoriteCompetition();
                }
            });
            isFavorite = Favorites.isFavorite(this, String.valueOf(eventId), Favorites.FAVORITE_COMPETITIONS);
        }

        if(isFavorite) {
            star.setImageResource(R.drawable.filled_star);
        } else {
            star.setImageResource(R.drawable.empty_star);
        }
    }

    //jer je u bazi spremljeno Y.M.D H:M:S, vraća H:M D.M.Y
    private String getNicerDate(String uglyDate) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(DateParserUtil.stringToDate(uglyDate));
        StringBuilder sb = new StringBuilder();

        sb.append(twoDigits(calendar.get(Calendar.HOUR_OF_DAY))).append(":")
                .append(twoDigits(calendar.get(Calendar.MINUTE))).append("  ")
                .append(calendar.get(Calendar.DAY_OF_MONTH)).append(".")
                .append(calendar.get(Calendar.MONTH)+1).append(".")
                .append(calendar.get(Calendar.YEAR)).append(".");

        return sb.toString();
    }

    private static String twoDigits(int number) {
        if (number >= 10) {
            return String.valueOf(number);
        } else {
            return "0" + String.valueOf(number);
        }
    }

    private void duelStub(DuelFromDb duel) {
        TextView stage = (TextView) findViewById(R.id.event_view_stub_duel_stage);
        TextView firstTeam = (TextView) findViewById(R.id.event_view_stub_duel_left_team);
        TextView firstScore = (TextView) findViewById(R.id.event_view_stub_duel_left_score);
        TextView secondTeam = (TextView) findViewById(R.id.event_view_stub_duel__right_team);
        TextView secondScore = (TextView) findViewById(R.id.event_view_stub_duel_right_score);

        //TODO: Uzmi timove i rezultat (Duel+DuelScore), i stage!

        stage.setText(duel.getStage().getName());
        firstTeam.setText(duel.getFirstCompetitor().getName());
        firstScore.setText(duel.getFirstComptetitorScore(this));
        secondTeam.setText(duel.getSecondCompetitor().getName());
        secondScore.setText(duel.getSecondComptetitorScore(this));

        findViewById(R.id.event_view_stub_duel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DuelScoresActivity.class);
                intent.putExtra("duelId", eventId);
                startActivity(intent);
            }
        });
    }

    private void competitionStub() {
        findViewById(R.id.event_view_stub_competition).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO:
                Toast.makeText(getApplicationContext(), "Otvori tablicu rezultata natijecanja" + eventId, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void toogleFavoriteDuel() {
        String eventIdString = String.valueOf(eventId);
        /*
        ako je favorit sad ga makni, ako nije favorit sad ga dodaj
         */
        if(Favorites.isFavorite(this, eventIdString, Favorites.FAVORITE_DUELS)) {
            star.setImageResource(R.drawable.empty_star);
            Favorites.removeFavorite(this, eventIdString, Favorites.FAVORITE_DUELS);
            Toast.makeText(getApplicationContext(), "Događaj maknut iz favorita.", Toast.LENGTH_SHORT).show();
        } else {
            star.setImageResource(R.drawable.filled_star);
            Favorites.addFavorite(this, eventIdString, Favorites.FAVORITE_DUELS);
            Toast.makeText(getApplicationContext(), "Događaj dodan u favorite.", Toast.LENGTH_SHORT).show();
        }
    }

    private void toogleFavoriteCompetition() {
        String eventIdString = String.valueOf(eventId);
        if(Favorites.isFavorite(this, eventIdString, Favorites.FAVORITE_COMPETITIONS)) {
            star.setImageResource(R.drawable.empty_star);
            Favorites.removeFavorite(this, eventIdString, Favorites.FAVORITE_COMPETITIONS);
            Toast.makeText(getApplicationContext(), "Događaj maknut iz favorita.", Toast.LENGTH_SHORT).show();
        } else {
            star.setImageResource(R.drawable.filled_star);
            Favorites.addFavorite(this, eventIdString, Favorites.FAVORITE_COMPETITIONS);
            Toast.makeText(getApplicationContext(), "Događaj dodan u favorite.", Toast.LENGTH_SHORT).show();
        }
    }

    //OVO NE RADI, ne znam zašto
//    private void showToastUpRight(String message) {
//        Toast toast = new Toast(getApplicationContext());
//        toast.setGravity(Gravity.TOP|Gravity.RIGHT, 16, 16);
//        toast.setText(message);
//        toast.setDuration(Toast.LENGTH_SHORT);
//        toast.show();
//    }

    private final static String EDIT_EVENT = "Uredi događaj";
    private final static String DELETE_EVENT = "Obriši događaj";

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_start, menu);
        menu.add(EDIT_EVENT);
        menu.add(DELETE_EVENT);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getTitle().toString()) {
            case EDIT_EVENT:
                Intent intent = new Intent(getApplicationContext(), EditEventActivity.class);
                intent.putExtra("event_id", eventId);
                intent.putExtra("isDuel", isDuel);
                startActivity(intent);
                break;

            case DELETE_EVENT:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                //prije sam umjesto this konstruktoru predao getApplicationContext() i crashalo je aplikaciju kod show(); razalog? idk
                builder.setMessage("Jeste li sigurni da želite izbrisati ovaj događaj?")
                        .setPositiveButton("Da", deleteEventAndExit())
                        .setNegativeButton("Ne", null).show();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    private DialogInterface.OnClickListener deleteEventAndExit() {
        return new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(which == DialogInterface.BUTTON_POSITIVE) {
                    if(isDuel) {
                        SqlDuelRepository duelRepo = new SqlDuelRepository(getApplicationContext());
                        duelRepo.deleteDuel(eventId);
                        duelRepo.close();
                    } else {
                        SqlCompetitionRepository compRepo = new SqlCompetitionRepository(getApplicationContext());
                        compRepo.deleteCompetition(eventId);
                        compRepo.close();
                    }
                    finish();
                }
            }
        };
    }
}

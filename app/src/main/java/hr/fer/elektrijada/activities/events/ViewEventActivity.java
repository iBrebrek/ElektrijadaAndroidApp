package hr.fer.elektrijada.activities.events;

import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import hr.fer.elektrijada.MenuHandler;
import hr.fer.elektrijada.R;
import hr.fer.elektrijada.activities.BaseMenuActivity;
import hr.fer.elektrijada.dal.sql.category.SqlCategoryRepository;
import hr.fer.elektrijada.dal.sql.competition.SqlCompetitionRepository;
import hr.fer.elektrijada.dal.sql.duel.SqlDuelRepository;
import hr.fer.elektrijada.util.Favorites;

/**
 * Created by Ivica Brebrek
 */
public class ViewEventActivity extends BaseMenuActivity {

    private String eventIdString;
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
        initDisplay();
    }

    private void initDisplay() {
        TextView category = (TextView) findViewById(R.id.event_view_category);
        star = (ImageView) findViewById(R.id.event_view_favorite);
        ViewStub stub = (ViewStub) findViewById(R.id.event_view_type_stub);
        TextView location = (TextView) findViewById(R.id.event_view_location);
        TextView start = (TextView) findViewById(R.id.event_view_start);
        TextView end = (TextView) findViewById(R.id.event_view_end);
        boolean isFavorite;

        boolean isDuel = getIntent().getBooleanExtra("isDuel", false);
        int eventId = getIntent().getIntExtra("event_id", -1);
        eventIdString = String.valueOf(eventId);

        if(eventId == -1) { //nebi se trebalo nikad dogoditi
            Toast.makeText(getApplicationContext(), "Greška prilikom otvaranja događaja.", Toast.LENGTH_SHORT).show();
            finish();
        }

        if(isDuel) {
            SqlDuelRepository duelRepo = new SqlDuelRepository(this);
            SqlDuelRepository.DuelFromDb duel = duelRepo.getDuel(eventId);
            duelRepo.close();

            category.setText(getCategoryName(duel.getCategoryId()));
            location.setText(duel.getLocation());
            start.setText(duel.getTimeFrom());
            if(duel.getTimeTo() != null) {
                end.setText(duel.getTimeTo());
            }

            star.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    toogleFavoriteDuel();
                }
            });
            isFavorite = Favorites.isFavorite(this, eventIdString, Favorites.FAVORITE_DUELS);

        } else {
            SqlCompetitionRepository compRepo = new SqlCompetitionRepository(this);
            SqlCompetitionRepository.CompetitionFromDb comp = compRepo.getCompetition(eventId);
            compRepo.close();

            category.setText(getCategoryName(comp.getCategoryId()));
            location.setText(comp.getLocation());
            start.setText(comp.getTimeFrom());
            if(comp.getTimeTo() != null) {
                end.setText(comp.getTimeTo());
            }

            star.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    toogleFavoriteCompetition();
                }
            });
            isFavorite = Favorites.isFavorite(this, eventIdString, Favorites.FAVORITE_COMPETITIONS);
        }

        if(isFavorite) {
            star.setImageResource(R.drawable.filled_star);
        } else {
            star.setImageResource(R.drawable.empty_star);
        }
    }

    private void toogleFavoriteDuel() {
        /*
        ako je favorit sad ga makni, ako nije favorit sad ga dodaj
         */
        if(Favorites.isFavorite(this, eventIdString, Favorites.FAVORITE_DUELS)) {
            star.setImageResource(R.drawable.empty_star);
            Favorites.removeFavorite(this, eventIdString, Favorites.FAVORITE_DUELS);
        } else {
            star.setImageResource(R.drawable.filled_star);
            Favorites.addFavorite(this, eventIdString, Favorites.FAVORITE_DUELS);
        }
    }

    private void toogleFavoriteCompetition() {
        if(Favorites.isFavorite(this, eventIdString, Favorites.FAVORITE_COMPETITIONS)) {
            star.setImageResource(R.drawable.empty_star);
            Favorites.removeFavorite(this, eventIdString, Favorites.FAVORITE_COMPETITIONS);
        } else {
            star.setImageResource(R.drawable.filled_star);
            Favorites.addFavorite(this, eventIdString, Favorites.FAVORITE_COMPETITIONS);
        }
    }

    private String getCategoryName(int categoryId) {
        SqlCategoryRepository catRepo = new SqlCategoryRepository(this);
        String name = catRepo.getCategoryName(categoryId);
        catRepo.close();
        return name;
    }
}

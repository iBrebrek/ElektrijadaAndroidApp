package hr.fer.elektrijada;

import android.content.Context;
import android.content.Intent;

import hr.fer.elektrijada.activities.events.EventsActivity;
import hr.fer.elektrijada.activities.news.NewsFeedActivity;
import hr.fer.elektrijada.activities.settings.SettingsActivity;

/**
 * Created by Boris on 17.11.2015.
 * Klasa u kojoj se na osnovu id-a poziva određene aktivnost
 * tako da se ne mora taj kod stavljati u više aktivnosti u koje se ugradi isti menu
 */
public class MenuHandler {
    public static void handle(Context context, int id) {
        Class<?> action = null;

        if (id == R.id.nav_vijesti) {
            action = NewsFeedActivity.class;
        } else if (id == R.id.nav_postavke) {
            action = SettingsActivity.class;
        } else if (id == R.id.nav_dogadjanja) {
            action = EventsActivity.class;
        }

        if (action != null){
            context.startActivity(new Intent(context, action));
        }
    }
}
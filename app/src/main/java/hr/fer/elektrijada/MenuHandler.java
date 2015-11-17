package hr.fer.elektrijada;

import android.content.Context;
import android.content.Intent;

import hr.fer.elektrijada.activities.news.NewsFeedActivity;

/**
 * Created by Boris on 17.11.2015.
 * Klasa u kojoj se na osnovu id-a poziva određene aktivnost
 * tako da se ne mora taj kod stavljati u više aktivnosti u koje se ugradi isti menu
 */
public class MenuHandler {
    public static void handle(Context context, int id) {
        //kasnije ovo riješiti preko Intenta, a ne direktnim navođenjem aktivnosti
        if (id == R.id.nav_vijesti) {
            context.startActivity(new Intent(context, NewsFeedActivity.class));
        } else if (id == R.id.nav_vijesti) {
            context.startActivity(new Intent(context, NewsFeedActivity.class));
        }
    }
}
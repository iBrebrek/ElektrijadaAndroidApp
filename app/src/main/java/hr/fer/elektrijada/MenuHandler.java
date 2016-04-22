package hr.fer.elektrijada;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.NavigationView;

import hr.fer.elektrijada.activities.events.EventsActivity;
import hr.fer.elektrijada.activities.news.NewsFeedActivity;
import hr.fer.elektrijada.activities.settings.SettingsActivity;

/**
 * Klasa u kojoj se na osnovu id-a poziva određene aktivnost
 *
 * Oznacuje potrebni meni
 */
public class MenuHandler {
    /**
     * konstanta koja oznacava da niti jedan od menia ne bi trebao biti oznacen
     */
    public static final int NO_MENU_SELECTED = -1;
    /**
     * konstanta koja oznacava id Vijesti u meniu
     */
    public static final int NEWS_ID = 0;
    /**
     * konstanta koja oznacava id Rezultata u meniu
     */
    public static final int RESULTS_ID = 1;
    /**
     * konstanta koja oznacava id Dogadaja u meniu
     */
    public static final int EVENTS_ID = 2;
    /**
     * konstanta koja oznacava id Ukupnog plasmana u meniu
     */
    public static final int RANKINGS_ID = 4;
    /**
     * konstanta koja oznacava id Postavke u meniu
     */
    public static final int SETTINGS_ID = 4;
    /**
     * id za Korisnicke Postavke
     */

    /**
     * zapocinje novi activity ovisno o odabranom meniu
     * @param context   trenutni kontekst
     * @param id        id menia, to nisu konstante ovog razreda, vec su to id iz layouta
     */
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

    /**
     * oznaci potreban meni
     * @param id        id menia, to je jedna od konstante ovog razreda
     * @param navigationView    view od navigacije
     */
    public static void selectMenu(int id, NavigationView navigationView) {
        if(id == NO_MENU_SELECTED) {
            /*
            na ovaj nacin, ako je nesto bilo oznaceno, vise nista nece biti oznaceni
            (jer SETTINGS_ID oznacava korisnicke postavke, koje su zapravo naslov podmenia, a to nikad nece biti oznaceno,
            a posto samo jedan moze biti oznacen na kraju nece biti oznacen niti jedan)
             */
            navigationView.getMenu().getItem(SETTINGS_ID).setChecked(true);
            return;
        }
        navigationView.getMenu().getItem(id).setChecked(true);
        if(id == SETTINGS_ID) {
            /*
            bilo bi logicno da se ne stavlja 2 puta checked,
            no kada se vratis s nekog glavnog menia na podemeni onda bi oba bila oznacena,
            na ovaj nacin se sa prvim setChecked odznaci onaj prvi;
            nejasno zasto se to dogada, vise proucavanja je potrebno...
            ugl, na ovaj nacin dobro radi
             */
            navigationView.getMenu().getItem(id).getSubMenu().getItem(0).setChecked(true);
        }
    }
}
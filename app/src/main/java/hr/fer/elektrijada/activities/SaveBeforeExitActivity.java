package hr.fer.elektrijada.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;

import hr.fer.elektrijada.R;

/**
 * Created by Ivica Brebrek
 */
public abstract class SaveBeforeExitActivity extends BaseMenuActivity {

    /*
    vrati true ako je spremanje obavljeno, inace false

    kada se otvori dialog na back pressed, i klikne se na "da" za spremi,
    onda ce se activity ugasiti ako ova metoda vrati true, inace ce activity ostati upaljen,

    zato u toj metodi treba javiti korisniku zasto nije uspio spremit da moze prepravit
     */
    protected abstract boolean save();

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle(R.string.dialog_onbackpressed_title)
                    .setMessage(R.string.dialog_onbackpressed_message)
                    .setPositiveButton(R.string.dialog_onbackpressed_postive, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if(save()) {
                                goBack();
                            }
                        }
                    })
                    .setNegativeButton(R.string.dialog_onbackpressed_negative, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            goBack();
                        }
                    })
                    .setNeutralButton(R.string.dialog_onbackpressed_cancel, null)
                    .show();
        }
    }

    private void goBack() { //da bi mogo pozvat super.onBackPressed u anonimnom razredu
        super.onBackPressed();
    }


    private final static String SAVE = "Spremi i izađi";
    private final static String CANCEL = "Odustani i izađi";

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_template, menu);
        menu.add(SAVE);
        menu.add(CANCEL);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getTitle().equals(SAVE)) {
            save();
            finish();
        } else if (item.getTitle().equals(CANCEL)) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}

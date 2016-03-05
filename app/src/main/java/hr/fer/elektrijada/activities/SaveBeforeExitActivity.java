package hr.fer.elektrijada.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;

import hr.fer.elektrijada.R;

/**
 * Created by Ivica Brebrek
 */
public abstract class SaveBeforeExitActivity extends BaseMenuActivity {

    protected abstract void save();

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
                            save();
                            goBack();
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
}

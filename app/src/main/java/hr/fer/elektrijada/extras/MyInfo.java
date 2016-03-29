package hr.fer.elektrijada.extras;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import hr.fer.elektrijada.dal.sql.user.SqlUserRepository;
import hr.fer.elektrijada.dal.sql.user.UserFromDb;

/**
 * Created by Ivica Brebrek
 */
public class MyInfo {

    private static final String PREFS_NAME = "MyPrefsFile";

    private static final String USERNAME = "myUsernamePrefs";

    private static final String FIRST_TIME = "myFirstTime";

    public static boolean isUnregistered(Context context) {
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        return settings.getBoolean(FIRST_TIME, true);
    }

    public static void pickUsername(final Activity activity) {

        final EditText name = new EditText(activity);
        final EditText surename = new EditText(activity);
        name.setHint("ime...");
        surename.setHint("prezime...");
        name.setInputType(InputType.TYPE_CLASS_TEXT);
        surename.setInputType(InputType.TYPE_CLASS_TEXT);

        LinearLayout input = new LinearLayout(activity);
        input.setOrientation(LinearLayout.VERTICAL);
        input.addView(name);
        input.addView(surename);

        final AlertDialog myDialog = new AlertDialog.Builder(activity)
                .setView(input)
                .setTitle("Odaberite korisničko ime.")
                .setPositiveButton(android.R.string.ok, null)
                .setCancelable(false)
                .create();

        myDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {

                Button button = myDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                button.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        boolean isCorrect = true;
                        String myName = name.getText().toString();
                        String mySurename = surename.getText().toString();
                        if (myName.trim().isEmpty()) {
                            isCorrect = false;
                            name.setHint("MORATE UNIJETI IME!");
                        }
                        if (mySurename.trim().isEmpty()) {
                            isCorrect = false;
                            surename.setHint("MORATE UNIJETI PREZIME!");
                        }

                        if (isCorrect) {
                            SqlUserRepository userRepo = new SqlUserRepository(activity);
                            long idRow = userRepo.createUser(new UserFromDb(-1, myName, mySurename));
                            userRepo.close();
                            rememberMyUsername(activity, idRow);
                            myDialog.dismiss();
                        }
                    }
                });
            }
        });

        myDialog.show();
    }

    private static void rememberMyUsername(Context context, Long usernameId) {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                    .edit()
                    .putBoolean(FIRST_TIME, false)
                    .putLong(USERNAME, usernameId)
                    .apply();
    }

    public static UserFromDb getMyUsername(Context context) {
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        SqlUserRepository userRepo = new SqlUserRepository(context);
        UserFromDb user = userRepo.getUser(settings.getLong(USERNAME, -1));
        userRepo.close();

        return user;
    }
}

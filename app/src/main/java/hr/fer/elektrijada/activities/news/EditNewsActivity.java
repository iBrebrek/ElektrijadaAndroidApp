package hr.fer.elektrijada.activities.news;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import hr.fer.elektrijada.R;
import hr.fer.elektrijada.activities.BaseMenuActivity;
import hr.fer.elektrijada.dal.sql.news.SqlNewsRepository;
import hr.fer.elektrijada.model.news.NewsEntry;

/**
 * activity za pisanje nove vijesti ili mijenjanje postojece vijesti
 * <p/>
 * Created by Ivica Brebrek
 */
public class EditNewsActivity extends BaseMenuActivity {
    /**
     * naslov vijesti
     **/
    private EditText txtTitle;
    /**
     * tekst vijesti
     **/
    private EditText txtText;
    /**
     * objekt koji se obraduje
     **/
    private NewsEntry news;
    /**
     * zastavica da znamo el dodajemo novog ili editiramo, true ako dodajemo
     **/
    private boolean adding;

    @Override
    protected int getContentLayoutId() {
        return R.layout.news_add_edit;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        increaseScrollViewSize();
        initEditTexts();
    }

    private void increaseScrollViewSize() {
        TextView increaseScroll = (TextView) findViewById(R.id.increaseScrollViewSize);
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int height = displaymetrics.heightPixels;
        increaseScroll.setHeight(height / 2);  //poveca kraj scroll viewa za pola ekrana
    }

    /**
     * inicijalizira clanske vrijable txtTitle i txtText (i news ako je edit), postavlja tekst ako je edit u pitanju
     */
    private void initEditTexts() {
        Intent intent = getIntent();
        txtTitle = (EditText) findViewById(R.id.textEditNewsTitle);
        txtText = (EditText) findViewById(R.id.textEditNewsText);

        if (intent.getBooleanExtra("add", false)) {
            //ovdje napisi sta treba podesiti kada se dodaje nova vijest...
            setTitle("Nova vijest");
            adding = true;
        } else {
            //ovdje napisi sta treba podesiti kada se editira postojeca vijest...
            setTitle("Uređivanje vijesti");
            SqlNewsRepository repo = null;
            try {
                repo = new SqlNewsRepository(getApplicationContext());
                news = repo.getNews(getIntent().getIntExtra("news_id", -1));
                if (news == null) {
                    byeBye();
                }
            } catch (Exception exc) {
                byeBye();
            } finally {
                if (repo != null) {
                    repo.close();
                }
            }
            if(news != null) {
                txtTitle.setText(news.getTitle());
                txtText.setText(news.getText());
            }
        }
    }

    private void byeBye() {
        Toast.makeText(getApplicationContext(), "Greška prilikom otvaranja vijesti", Toast.LENGTH_SHORT).show();
        finish();
    }

    //varijable postoje samo zato sto se stringovi koriste na vise mjesta (pa da se lakse mjenja naziv)
    private final static String SAVE_EDITED_NEWS = "Spremi";
    private final static String CANCEL_EDIT_NEWS = "Odustani";

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_news, menu);
        menu.add(SAVE_EDITED_NEWS);
        menu.add(CANCEL_EDIT_NEWS);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getTitle().equals(SAVE_EDITED_NEWS)) {
            saveAndExit();
        } else if (item.getTitle().equals(CANCEL_EDIT_NEWS)) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean isReadyToSave() {
        StringBuilder sb = new StringBuilder();
        if (news.getTitle().isEmpty()) {
            sb.append("Vijest mora imati naslov!\n");
        }
        if (news.getText().isEmpty()) {
            sb.append("Vijest mora imati tekst!\n");
        }
        if (!sb.toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "\n"+sb, Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private void saveAndExit() {
        fillObjectNews();
        if (!isReadyToSave()) {
            return;
        }
        //TODO: provijeriti gdje se treba spremiti, lokalno ili online
        SqlNewsRepository repo = new SqlNewsRepository(getApplicationContext());
        if (adding) {
            repo.createNewsEntry(news);
        } else {
            repo.updateNews(news);
        }
        repo.close();
        finish();
    }

    /**
     * postavlja potrebne elemente clanskoj varijabli news
     */
    private void fillObjectNews() {
        if (adding) {
            //TODO: na prvo mjesto ovog konstruktora (umjesto 1) stavi id korisnika koji trenutno koristi app
            news = new NewsEntry("", "", 1);
        }
        news.setTitle(txtTitle.getText().toString());
        news.setText(txtText.getText().toString());
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Zatvaranje prozora")
                .setMessage("Želite li spremiti prije izlaska?")
                .setPositiveButton("Da", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        saveAndExit();
                    }

                })
                .setNegativeButton("Ne", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNeutralButton("Odustani", null)
                .show();
    }
}

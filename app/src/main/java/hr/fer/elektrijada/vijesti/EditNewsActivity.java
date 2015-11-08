package hr.fer.elektrijada.vijesti;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import hr.fer.elektrijada.R;
import hr.fer.elektrijada.vijesti.db.DatabaseHandler;

/**
 * activity za pisanje nove vijesti ili mijenjanje postojece vijesti
 *
 * Created by Ivica Brebrek
 */
public class EditNewsActivity extends Activity {
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
    private News news;
    /**
     * zastavica da znamo el dodajemo novog ili editiramo
     **/
    private boolean adding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_add_edit);

        initEditTexts();
        initSaveButton();
    }

    /**
     * inicijalizira clanske vrijable txtTitle i txtText, postavlja tekst ako je edit u pitanju
     */
    private void initEditTexts() {
        Intent intent = getIntent();
        txtTitle = (EditText) findViewById(R.id.textEditNewsTitle);
        txtText = (EditText) findViewById(R.id.textEditNewsText);

        if (intent.getBooleanExtra("add", false)) {
            //ovdje napisi sta treba podesiti kada se dodaje nova/prazna vijest...
            adding = true;
        } else {
            //ovdje je postavljanje postojecih vijesti
            news = (News) intent.getSerializableExtra("object");
            txtTitle.setText(news.getTitle());
            txtText.setText(news.getText());
        }
    }

    /**
     * inicijalizira save button kojim se potvrduje izmjena/dodavanje vijesti
     * vraca se nazad u NewsFeedActivity i "javlja" da je poso obavljen
     */
    private void initSaveButton() {
        findViewById(R.id.btnSaveNews).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                fillObjectNews();
                DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                if (adding) {
                    //TODO: provijeri treba li spremati na uredaj i/ili online
                    //db.createNews(news);
                } else {
                    //TODO: provijeri treba li mijenjati na uredaju i/ili online

                    //da bi se moglo rec NewsFeedActivity-u da je doslo do promijene
                    intent.putExtra("edited", true);
                }
                db.close();

                intent.putExtra("object", news);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    /**
     * postavlja potrebne elemente clanskoj varijabli news
     */
    private void fillObjectNews(){
        if (adding) {
            //TODO: na prvo mjesto ovog konstruktora (umjesto 1) stavi id korisnika koji trenutno koristi app
            news = new News(1, "", "");
        }
        news.setTitle(txtTitle.getText().toString());
        news.setText(txtText.getText().toString());
    }
}

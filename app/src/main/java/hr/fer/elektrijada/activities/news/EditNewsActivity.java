package hr.fer.elektrijada.activities.news;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import hr.fer.elektrijada.MenuHandler;
import hr.fer.elektrijada.R;
import hr.fer.elektrijada.activities.SaveBeforeExitActivity;
import hr.fer.elektrijada.dal.sql.news.SqlNewsRepository;
import hr.fer.elektrijada.extras.MyInfo;
import hr.fer.elektrijada.model.news.NewsEntry;

/**
 * activity za pisanje nove vijesti ili mijenjanje postojece vijesti
 * <p/>
 * Created by Ivica Brebrek
 */
public class EditNewsActivity extends SaveBeforeExitActivity {
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
    protected int belongingToMenuItemId() {
        return MenuHandler.NEWS_ID;
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

    /**
     * postavlja potrebne elemente clanskoj varijabli news
     */
    private void fillObjectNews() {
        if (adding) {
            news = new NewsEntry("", "", MyInfo.getMyUsername(this).getId());
        }
        news.setTitle(txtTitle.getText().toString());
        news.setText(txtText.getText().toString());
    }

    @Override
    protected void save() {
        fillObjectNews();
        if (!isReadyToSave()) {
            return;
        }

        SqlNewsRepository repo = new SqlNewsRepository(getApplicationContext());
        if (adding) {
            repo.createNewsEntry(news);
        } else {
            repo.updateNews(news);
        }
        repo.close();
    }
}

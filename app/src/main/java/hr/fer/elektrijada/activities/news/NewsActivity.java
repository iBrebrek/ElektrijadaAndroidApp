package hr.fer.elektrijada.activities.news;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import hr.fer.elektrijada.R;
import hr.fer.elektrijada.activities.BaseMenuActivity;
import hr.fer.elektrijada.dal.sql.news.SqlNewsRepository;
import hr.fer.elektrijada.model.news.NewsEntry;

/**
 * Created by Ivica Brebrek
 */
public class NewsActivity extends BaseMenuActivity {
    /**
     * vijest koja je prikazana
     **/
    private NewsEntry news;

    @Override
    protected int getContentLayoutId() {
        return R.layout.news;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loadEverything();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadEverything();
    }

    private void loadEverything() {
        loadNews();
        if (news != null) { //null ce biti samo ako je doslo do greske u loadNews()
            initShownNews();
        }
    }

    /**
     * ucitava vijest u varijablu news
     */
    private void loadNews() {
        SqlNewsRepository repo = new SqlNewsRepository(getApplicationContext());
        try {
            news = repo.getNews(getIntent().getIntExtra("news_id", -1));
            //u slucaju ako ne postoji vijest s tim id-em, npr izbrisana milisekundu nakon sto smo mi u listi kliknuli na tu vijest
            if (news == null) {
                byeBye();
            }
        } catch (Exception exc) {
            byeBye();
        } finally {
            repo.close();
        }
    }

    private void byeBye() {
        Toast.makeText(getApplicationContext(), "Greška prilikom otvaranja vijesti", Toast.LENGTH_SHORT).show();
        finish();
    }

    /**
     * prikazuje sve potrebne podatke o vijesti
     */
    private void initShownNews() {
        TextView txtTitle = (TextView) findViewById(R.id.textNewsTitle);
        TextView txtAuthor = (TextView) findViewById(R.id.textNewsAuthor);
        TextView txtDate = (TextView) findViewById(R.id.textNewsDate);
        TextView txtText = (TextView) findViewById(R.id.textNewsText);

        txtTitle.setText(news.getTitle());
        txtAuthor.setText(String.format("Autor: %s", news.getAuthorId())); //TODO: getAuthorName umjesto getAuthorId
        txtDate.setText(String.format("Objavljeno: %s", news.getTimeToString()));
        txtText.setText(news.getText());
    }

    private final static String EDIT_NEWS = "Promijeni";
    private final static String DELETE_NEWS = "Izbriši";

    /**
     * dodani "Promijeni" i "Izbrisi" u opcije
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_news, menu);
        //TODO prikazi ove opcije samo ako korisnik ima dopustenje, tj., ako je vijest njegova
        menu.add(EDIT_NEWS);
        menu.add(DELETE_NEWS);
        return true;
    }

    /**
     * klikom na "Promijeni" otvara prozor za edit nad tim clankom,
     * "Izbrisi" brise otvorenu vijest i vraca na popis svih vijesti
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(getApplicationContext(), EditNewsActivity.class);
        if (item.getTitle().equals(EDIT_NEWS)) {
            intent.putExtra("news_id", news.getId());
            startActivity(intent);
        } else if (item.getTitle().equals(DELETE_NEWS)) {
            SqlNewsRepository repo = new SqlNewsRepository(getApplicationContext());
            repo.deleteNews(news);
            repo.close();
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}

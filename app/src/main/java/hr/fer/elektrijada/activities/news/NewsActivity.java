package hr.fer.elektrijada.activities.news;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import hr.fer.elektrijada.R;
import hr.fer.elektrijada.model.news.NewsEntry;

/**
 * Created by Ivica Brebrek
 */
public class NewsActivity extends ActionBarActivity {

    /**
     * objekt koji je prikazan
     **/
    private NewsEntry news;
    /**
     * zastavica da znamo je li bilo promijene
     **/
    private boolean isChanged;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news);
        getSupportActionBar().hide(); //da bi maknuo labelu
        Intent intent = getIntent();
        initShownNews(intent);
    }

    private void initShownNews(Intent intent) { //dodan parametar jer pozivamo u raznim situacijama ovu metodu

        TextView txtTitle = (TextView) findViewById(R.id.textNewsTitle);
        TextView txtAuthor = (TextView) findViewById(R.id.textNewsAuthor);
        TextView txtDate = (TextView) findViewById(R.id.textNewsDate);
        TextView txtText = (TextView) findViewById(R.id.textNewsText);

        news = (NewsEntry) intent.getSerializableExtra("object");
        txtTitle.setText(news.getTitle());
        txtAuthor.setText("Autor: " + news.getAuthorId()); //TODO: getAuthorName umjesto getAuthorId
        txtDate.setText("Objavljeno: " + news.getTimeToString());
        txtText.setText(news.getText());

    }

    /**
     * dodani "Promijeni" i "Izbrisi" u opcije
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_news, menu);
        menu.add(menu.NONE, 1, 100, "Promijeni");
        menu.add(menu.NONE, 2, 101, "Izbriši");
        return true;
    }

    /**
     * klikom na "Promijeni"(id=1) otvara prozor za edit nad tim clankom,
     * Izbrisi(id=2) brise otvorenu vijest i vraca na popis svih vijesti
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent intent = new Intent(getApplicationContext(), EditNewsActivity.class);
        switch (id) {
            case 1: //1 je za edit (zadano u onCreateOptionsMenu)
                intent.putExtra("edited", true);
                intent.putExtra("object", news);
                startActivityForResult(intent, 3);
                break;
            case 2: //2 je za brisanje
                /*
                NewsDbHelper db = new NewsDbHelper(getApplicationContext());
                db.deleteNews(news);
                db.close();
                */
                intent.putExtra("removed", true);
                intent.putExtra("object", news);
                setResult(RESULT_OK, intent);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item); //ne znam zas je ovo tu :(
    }


    //dok se vratimo iz EditNewsActivity moramo osvijeziti tekst i updatat clansku varijablu news zbog mogucih promijena
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 3) {
                isChanged = true;
                news = (NewsEntry) data.getSerializableExtra("object");
                initShownNews(data);
            }
        }
    }

    /**
     * popisu svih vijesti javljamo izmijenu da bi se mogo azurirati
     */
    @Override
    public void onBackPressed() {
        if (isChanged) {
            Intent intent = new Intent();
            intent.putExtra("object", news);
            intent.putExtra("edited", true);
            setResult(RESULT_OK, intent);
        }
        finish();
    }

}

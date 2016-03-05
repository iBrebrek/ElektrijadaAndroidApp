package hr.fer.elektrijada.activities.news;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import hr.fer.elektrijada.MenuHandler;
import hr.fer.elektrijada.R;
import hr.fer.elektrijada.activities.BaseMenuActivity;
import hr.fer.elektrijada.dal.sql.news.SqlNewsRepository;
import hr.fer.elektrijada.model.news.NewsEntry;

/**
 * Created by Ivica Brebrek
 */
public class NewsFeedActivity extends BaseMenuActivity {

    @Override
    protected int getContentLayoutId() {
        return R.layout.news_feed;
    }

    @Override
    protected int belongingToMenuItemId() {
        return MenuHandler.NEWS_ID;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initScrollView();
    }

    /**
     * prikaz svih vijesti koji omogucuje otvaranje pojedinace vijesti
     */
    private void initScrollView() {
        SqlNewsRepository repository = new SqlNewsRepository(getApplicationContext());
        final ArrayList<NewsEntry> list = new ArrayList<>(repository.getNews());
        repository.close();
        if (list.size() == 0) {
            noNews();
        }else {
            findViewById(R.id.ifNoNews).setVisibility(View.GONE);
        }
        final ListView listView = (ListView) findViewById(R.id.listViewNewsFeed);
        NewsFeedListAdapter newsAdapter = new NewsFeedListAdapter(this, list);
        listView.setAdapter(newsAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() { //otvaranje pojedinacne vijesti
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), NewsActivity.class);
                intent.putExtra("news_id", list.get(position).getId());
                startActivity(intent);
            }
        });
    }

    private void noNews() {
        TextView text = (TextView) findViewById(R.id.ifNoNews);
        text.setText(String.format("Nema vijesti :(\n\nBudite prvi i dodajte vijest\n(gore desno -> \"Dodaj vijest\")"));
        text.setGravity(Gravity.CENTER);
        text.setVisibility(View.VISIBLE);
    }


    //npr kad se vratimo iz edita ili dodavanja nove vijesti...
    @Override
    protected void onResume() {
        super.onResume();
        initScrollView();
    }

    //ovo je ime opcije koja ce biti u popisu opcija
    //varijabla postoji samo zato sto se ime koristi na vise mjesta (pa da se lakse mjenja naziv)
    private final static String ADD_NEW_NEWS = "Dodaj vijest";

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_start, menu);
        menu.add(ADD_NEW_NEWS);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (item.getTitle().equals(ADD_NEW_NEWS)) {
            Intent intent = new Intent(getApplicationContext(), EditNewsActivity.class);
            intent.putExtra("add", true);
            startActivity(intent);
        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

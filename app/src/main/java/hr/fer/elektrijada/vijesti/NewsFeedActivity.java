package hr.fer.elektrijada.vijesti;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import hr.fer.elektrijada.R;
import hr.fer.elektrijada.dal.mock.news.MockNewsRepository;
import hr.fer.elektrijada.dal.sql.news.NewsDbHelper;
import hr.fer.elektrijada.dal.sql.news.SqlNewsRepository;
import hr.fer.elektrijada.model.news.NewsEntry;
import hr.fer.elektrijada.model.news.NewsRepository;

/**
 * Created by Ivica Brebrek
 */
public class NewsFeedActivity extends Activity {

    /**
     * tu ce biti spremljene vijesti
     **/
    private ArrayList<NewsEntry> list;
    /**
     * prikazuje sve vijesti; clanska varijabla da bi se mogla refreshat nakon izmjene
     **/
    private NewsFeedListAdapter newsAdapter;
    /**
     * index news-a koji je kliknut
     **/
    private int clickedIndex;
    /**
     * za rad s bazom
     */
    private NewsRepository repository;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_feed);
        //TEST
        repository = new MockNewsRepository();
        //DB
        //repository = new SqlNewsRepository(getApplicationContext());


        initScrollView();
        initAddButton();
    }

    /**
     * tipka za dodavanje (gore desno) otvara izradu nove vijesti
     */
    private void initAddButton() {
        findViewById(R.id.btnAddNewNews).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EditNewsActivity.class);
                intent.putExtra("add", true);
                //ocekuj rezultat, ovaj broj: this code will be returned in onActivityResult() when the activity exits
                startActivityForResult(intent, 1);
            }
        });
    }

    /**
     * prikaz svih vijesti koji omogucuje otvaranje pojedinace vijesti
     */
    private void initScrollView() {
        list = new ArrayList<>(repository.getNews());
        final ListView listView = (ListView) findViewById(R.id.listViewNewsFeed);
        newsAdapter = new NewsFeedListAdapter(this, list);
        listView.setAdapter(newsAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() { //otvaranje pojedinacne vijesti
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), NewsActivity.class);
                clickedIndex = position;
                intent.putExtra("object", list.get(position));
                startActivityForResult(intent, 2);
            }
        });
    }



    /**
     * povratak iz EditNewsActivity, dodaje(tj. mijenja) novu(tj. izmijenjenu) vijest
     *
     * @param requestCode kod koj smo zadali, a zadao sam 1 za dodavanje i 2 za edit/remove
     * @param resultCode  RESULT_OK ako je uspijesno obavljeno
     * @param data        dobiveni intent, preko toga saljemo podatke izmedu activitya
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) { //obavezna provjera jer se rusi app ako se ne obavi radnja do kraja
            NewsEntry manipulatedNews = (NewsEntry) data.getSerializableExtra("object");
            switch (requestCode) {
                case 1:  //1 je kad se stvara novi
                    Toast.makeText(getApplicationContext(), "Dodana vijest:\n" + manipulatedNews.getTitle(), Toast.LENGTH_SHORT).show();
                    list.add(manipulatedNews);
                    sortList();
                    break;
                case 2: //2 je izmijena ili brisanje
                    if (data.getBooleanExtra("removed", false)) {
                        Toast.makeText(getApplicationContext(), "Na poziciji " + clickedIndex + " obrisan:\n" + manipulatedNews.getTitle(), Toast.LENGTH_SHORT).show();
                        list.remove(clickedIndex);
                    } else {
                        if (data.getBooleanExtra("edited", false)) {
                            Toast.makeText(getApplicationContext(), "Na poziciji " + clickedIndex + " izmijenjena vijest:\n" + manipulatedNews.getTitle(), Toast.LENGTH_SHORT).show();
                            list.remove(clickedIndex);
                            list.add(clickedIndex, manipulatedNews);
                        }
                    }
                    break;
            }
            newsAdapter.notifyDataSetChanged(); //refresh list view
        }
    }

    /**
     * sortira vijesti, najmlada gore- najstarija dolje
     */
    private void sortList() {
        Collections.sort(list, new Comparator<NewsEntry>() {
            @Override
            public int compare(NewsEntry n1, NewsEntry n2) {
                return n2.getTimeOfCreation().compareTo(n1.getTimeOfCreation());
            }
        });
    }
}

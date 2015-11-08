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
import hr.fer.elektrijada.vijesti.db.DatabaseHandler;

/**
 * Created by Ivica Brebrek
 */
public class NewsFeedActivity extends Activity {

    /**
     * tu ce biti spremljene vijesti
     **/
    private ArrayList<News> list;
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
    private DatabaseHandler db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_feed);
        db = new DatabaseHandler(getApplicationContext());

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
        getNews(); //ucitavanje vijesti
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
     * TODO: ovdje ucitati iz baze
     * <p/>
     * stavlja sve vijesti u clansku varijablu list
     */
    private void getNews() {
        //list = db.getAllNews(); //dodavanje iz baze TODO: FIX, trenutno javlja da ne postoji tablica u metodi getAllNews
        if(list == null){
            list = new ArrayList<>();
        }

        //umjesto ovih add-ova u listu dodati vijesti iz baze
        list.add(
                new News(
                        7,
                        "Najstarija vijest ikad",
                        "oldy",
                        new Date(0)
                )
        );
        StringBuilder test = new StringBuilder();
        for (int i = 0; i < 100; i++) test.append(i + ". red\n");
        list.add(
                new News(
                        7,
                        "Vijest stara nekolko dana koja ima nevjerojatan dug naslov i tekst i ne znam hoće li se cijeli vidjeti, ko zna kolko reda ce prikazati, je li beskonacno????",
                        test.toString(),
                        new Date(Calendar.getInstance().getTime().getTime() - 600000000)
                )
        );
        for (int i = 0; i < 15; i++) {
            list.add(
                    new News(
                            i,
                            "Naslov br " + i,
                            "Randasdasdjfn asdf nasdflnasd \n asdfjnasa nasdf n\n sdf\n qffa \n sd\n\n aaaa",
                            new Date(Calendar.getInstance().getTime().getTime() - 350000 * i)
                    )
            );
        }
        //koristi konstruktor koji dodaje TRENUTNO vrijeme
        list.add(new News(
                        555,
                        "Isprobavam el sortira vrijeme",
                        ""
                )
        );


        sortList();
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
            News manipulatedNews = (News) data.getSerializableExtra("object");
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
        Collections.sort(list, new Comparator<News>() {
            @Override
            public int compare(News n1, News n2) {
                return n2.getTimeOfCreation().compareTo(n1.getTimeOfCreation());
            }
        });
    }
}

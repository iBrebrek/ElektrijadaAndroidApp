package hr.fer.elektrijada;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import hr.fer.elektrijada.vijesti.NewsFeedActivity;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        /*TODO: sloziti da ovo proradi, pogledati u DatabaseHandler.java za detalje
        DatabaseHandler db = new DatabaseHandler(getApplicationContext());
        News news = new News(1, "naslov Testa", "text Testa", 5);
        db.createNews(news);
        news=db.getNews(1);
        db.deleteNews(news);
        db.deleteNews(news);
        db.close();
        */



        initButtonNews();
    }

    /**
     * omogucuje odlazak na NewsFeedActivity
     */
    private void initButtonNews(){
        findViewById(R.id.btnOpenNews).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, NewsFeedActivity.class));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

package hr.fer.elektrijada.activities.events;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.elektrijada.R;
import hr.fer.elektrijada.activities.BaseMenuActivity;
import hr.fer.elektrijada.dal.sql.helper.events.SqlGetEventsInfo;
import hr.fer.elektrijada.model.events.DateStamp;
import hr.fer.elektrijada.model.events.Event;

/**
 * Created by Ivica Brebrek
 */
public class EventsActivity extends BaseMenuActivity{

    /**
     * lista u kojoj su filtirani (ili svi) dogadaji
     */
    private ArrayList<Event> listOfEvents;
    EventsListAdapter eventsListAdapter;

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_events;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Događanja");
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.sharedLayout);
        layout.setPadding(0, 0, 0, 0); //da bi donje tipke mogle biti uz rub

        initScrollView();
        initFilterButtons();

    }


    //da bi se mogli ponovo sortirati dogadaji nakon izmjene nekog pocetka
    public void refreshAndSort() {
        removeDateStamps(listOfEvents);
        Collections.sort(listOfEvents);
        insertDateStamps(listOfEvents);
        eventsListAdapter.notifyDataSetChanged();
    }

    private void initScrollView() {
        SqlGetEventsInfo repo = new SqlGetEventsInfo(getApplicationContext());
        listOfEvents = repo.getAllEvents();
        repo.close();
        adjustList();
    }

    private void adjustList() {
        Collections.sort(listOfEvents);
        insertDateStamps(listOfEvents);
        ListView listView = (ListView) findViewById(R.id.listViewEvents);
        eventsListAdapter = new EventsListAdapter(this, listOfEvents);
        listView.setAdapter(eventsListAdapter);
        eventsListAdapter.notifyDataSetChanged();
    }

    private void insertDateStamps(List<Event> list) {
        DateStamp date = null;
        if(list.size()>0) {
            date = new DateStamp(list.get(0).getTimeFrom());
            list.add(0, date);
        }
        for(int i=2; i<list.size(); i++) {
            if(!date.sameStartDate(list.get(i))){
                date = new DateStamp(list.get(i).getTimeFrom());
                list.add(i, date);
            }
        }
    }

    private void removeDateStamps(List<Event> list) {
        for(int i=0, size=list.size(); i<size; i++) {
            if(list.get(i) instanceof DateStamp) {
                list.remove(i);
                i--;
                size--;
            }
        }
    }

    private Button type;
    private void initFilterButtons() {
        Button time = (Button) findViewById(R.id.eventsFilterTime);
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SqlGetEventsInfo repo = new SqlGetEventsInfo(getApplicationContext());
                repo.addFakeEvents();
                List<Event> events;
                events = repo.getAllEvents();
                repo.close();
            }
        });
        type = (Button) findViewById(R.id.eventsFilterType);
        type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterType();
            }
        });
    }

    private void filterType() {
        AlertDialog.Builder dialogBuilder;
        final String[] listOfTypes = {"Sve", "Sport", "Znanje", "Favoriti"};
        final ArrayList<String> eventTypes = new ArrayList<>();
        eventTypes.add("Sve");
        eventTypes.add("Sport");
        eventTypes.add("Znanje");
        eventTypes.add("Favoriti");
        dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle("Odaberi prikazane događaje");
        dialogBuilder.setSingleChoiceItems(
                listOfTypes,
                eventTypes.indexOf(type.getText().toString()),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SqlGetEventsInfo repo = new SqlGetEventsInfo(getApplicationContext());
                        String typeName = eventTypes.get(which);
                        switch (typeName) {
                            case "Sve":
                                listOfEvents = repo.getAllEvents();
                                break;
                            case "Sport":
                                listOfEvents = repo.getAllSportEvents();
                                break;
                            case "Znanje":
                                listOfEvents = repo.getAllKnowledgeEvents();
                                break;
                            case "Favoriti":
                                //TODO: stavi favorite u listOfEvents
                                break;
                        }
                        type.setText(typeName);
                        repo.close();
                        adjustList();
                        dialog.cancel();
                    }
                }
        );
        AlertDialog dialog = dialogBuilder.create();
        dialog.show();
    }


    private static String ADD_NEW_EVENT = "Dodaj događaj";

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_start, menu);
        menu.add(ADD_NEW_EVENT);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getTitle().equals(ADD_NEW_EVENT)) {
            Intent intent = new Intent(getApplicationContext(), CreateNewEventActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}

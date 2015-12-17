package hr.fer.elektrijada.activities.events;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
import hr.fer.elektrijada.model.events.KnowledgeEvent;
import hr.fer.elektrijada.model.events.SportEvent;

/**
 * Created by Ivica Brebrek
 */
public class EventsActivity extends BaseMenuActivity{

    /**
     * lista u kojoj se prikazuju filtirani dogadaji
     */
    private ArrayList<Event> currentList;
    /**
     * lista u kojoj se nalaze svi dogadaji, bez DateStamp
     */
    private ArrayList<Event> listOfEverything;
    EventsListAdapter eventsListAdapter;

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_events;
    }

    private Activity thisActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        thisActivity = this;
        setTitle("Događanja");
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.sharedLayout);
        layout.setPadding(0, 0, 0, 0);
        initScrollView();
        initFilterButtons();

    }


    //da bi se mogli ponovo sortirati dogadaji nakon izmjene nekog pocetka
    public void refreshAndSort() {
        removeDateStamps(currentList);
        Collections.sort(currentList);
        insertDateStamps(currentList);
        eventsListAdapter.notifyDataSetChanged();
    }

    private void initScrollView() {
        listOfEverything = FakeEvents.getAllTheEvents();
        currentList = new ArrayList<>(listOfEverything);
        //list = FakeEvents.getAllTheEvents();
        //npr kad se ponovno ucita, pa da ne bi imali duple datume
        removeDateStamps(currentList);
        Collections.sort(currentList);
        insertDateStamps(currentList);
        ListView listView = (ListView) findViewById(R.id.listViewEvents);
        eventsListAdapter = new EventsListAdapter(this, currentList);
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
        String[] listOfTypes = {"Sve", "Sport", "Znanje", "Favoriti"};
        final ArrayList<String> eventTypes = new ArrayList<>();
        eventTypes.add("Sve");
        eventTypes.add("Sport");
        eventTypes.add("Znanje");
        eventTypes.add("Favoriti");
        dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle("Odaberi prikazane dogaÄ‘aje");
        dialogBuilder.setSingleChoiceItems(
                listOfTypes,
                eventTypes.indexOf(type.getText().toString()),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String typeName = eventTypes.get(which);
                        currentList = new ArrayList<>();
                        type.setText(typeName);
                        Collections.sort(listOfEverything);
                        if(typeName.equals("Sve")) {
                            currentList = new ArrayList<>(listOfEverything);
                        } else if (typeName.equals("Sport")) {
                            for(Event event:listOfEverything) {
                                if(event instanceof SportEvent) {
                                    currentList.add(event);
                                }
                            }} else if(typeName.equals("Znanje")) {
                            for(Event event:listOfEverything) {
                                if(event instanceof KnowledgeEvent) {
                                    currentList.add(event);
                                }
                            }
                        } else if(typeName.equals("Favoriti")) {
                            //TODO: prikaz favorita
                        }
                        insertDateStamps(currentList);
                        ListView listView = (ListView) findViewById(R.id.listViewEvents);
                        eventsListAdapter = new EventsListAdapter(thisActivity, currentList);
                        listView.setAdapter(eventsListAdapter);
                        eventsListAdapter.notifyDataSetChanged();
                        dialog.cancel();
                    }
                }
        );
        AlertDialog dialog = dialogBuilder.create();
        dialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_start, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

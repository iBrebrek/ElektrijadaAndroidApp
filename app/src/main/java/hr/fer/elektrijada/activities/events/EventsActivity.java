package hr.fer.elektrijada.activities.events;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import hr.fer.elektrijada.R;
import hr.fer.elektrijada.activities.BaseMenuActivity;
import hr.fer.elektrijada.dal.sql.helper.events.SqlGetEventsInfo;
import hr.fer.elektrijada.model.events.DateStamp;
import hr.fer.elektrijada.model.events.Event;
import hr.fer.elektrijada.model.events.SportNameLabel;

/**
 * Created by Ivica Brebrek
 */
public class EventsActivity extends BaseMenuActivity{

    /**
     * lista u kojoj su filtirani (ili svi) dogadaji
     */
    private ArrayList<Event> listOfEvents;
    private BaseAdapter eventsListAdapter;
    private Button typeButton;
    private Button dateButton;
    private ListView listView;

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
        listView = (ListView) findViewById(R.id.listViewEvents);
        initFilterButtons();
        initScrollView();

    }

    //da bi se mogli ponovo sortirati dogadaji nakon izmjene nekog pocetka
    public void refreshAndSort() {
        removeHelperTypes(listOfEvents);
        adjustList();
    }

    private void initScrollView() {
        SqlGetEventsInfo repo = new SqlGetEventsInfo(getApplicationContext());
        listOfEvents = repo.getAllEvents();
        repo.close();
        adjustList();
    }

    private void adjustList() {
        if(typeButton.getText().toString().equals("Sport")) {
            Collections.sort(listOfEvents, new Comparator<Event>() {
                @Override
                public int compare(Event lhs, Event rhs) {
                    int byName = lhs.getName().compareTo(rhs.getName());
                    if(byName != 0) return byName;
                    return lhs.compareTo(rhs);
                }
            });
            insertDateStamps(listOfEvents);
            insertSportNames(listOfEvents);
            eventsListAdapter = new SportEventsListAdapter(this, listOfEvents);
        } else {
            Collections.sort(listOfEvents);
            insertDateStamps(listOfEvents);
            eventsListAdapter = new EventsListAdapter(this, listOfEvents);
        }
        listView.setAdapter(eventsListAdapter);
       // eventsListAdapter.notifyDataSetChanged();
    }

    private void insertSportNames(List<Event> list) {
        String typeName = "";
        for(int i=0, size=list.size(); i<size; i++) {
            Event event = list.get(i);
            if(event instanceof DateStamp) event = list.get(i+1);
            String iteratedName = event.getName();
            if(!typeName.equals(iteratedName)) {
                typeName = iteratedName;
                list.add(i, new SportNameLabel(typeName));
                i++;
                size++;
            }
        }
    }

    private void insertDateStamps(List<Event> list) {
        DateStamp date = null;
        if(list.size()>0) {
            date = new DateStamp(list.get(0).getTimeFrom());
            list.add(0, date);
        }
        for(int i=2, size=list.size(); i<size; i++) {
            if(!date.sameStartDate(list.get(i))){
                date = new DateStamp(list.get(i).getTimeFrom());
                list.add(i, date);
                i++;
                size++;
            }
        }
    }

    //helperTypes: DateStamps, SportNameLabel
    private void removeHelperTypes(List<Event> list) {
        for(int i=0, size=list.size(); i<size; i++) {
            Event event = list.get(i);
            if(event instanceof DateStamp || event instanceof SportNameLabel) {
                list.remove(i);
                i--;
                size--;
            }
        }
    }

    private void initFilterButtons() {
        dateButton = (Button) findViewById(R.id.eventsFilterTime);
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initFilterDate();
            }
        });
        typeButton = (Button) findViewById(R.id.eventsFilterType);
        typeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initFilterType();
            }
        });
    }

    private void initFilterType() {
        AlertDialog.Builder dialogBuilder;
        final String[] listOfTypes = {"Svi događaji", "Sport", "Znanje", "Favoriti"};
        final ArrayList<String> eventTypes = new ArrayList<>();
        eventTypes.add("Svi događaji");
        eventTypes.add("Sport");
        eventTypes.add("Znanje");
        eventTypes.add("Favoriti");
        dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle("Odaberi prikazane događaje");
        dialogBuilder.setSingleChoiceItems(
                listOfTypes,
                eventTypes.indexOf(typeButton.getText().toString()),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String typeName = eventTypes.get(which);
                        typeButton.setText(typeName);
                        filter();
                        dialog.cancel();
                    }
                }
        );
        AlertDialog dialog = dialogBuilder.create();
        dialog.show();
    }

    private void initFilterDate() {
        SqlGetEventsInfo repo = new SqlGetEventsInfo(getApplicationContext());
        List<Event> allEvents = repo.getAllEvents();
        repo.close();
        Collections.sort(allEvents);
        insertDateStamps(allEvents);
        final List<String> allDates = new ArrayList<>();
        allDates.add("Svi datumi");
        for(Event event:allEvents) {
            if(event instanceof DateStamp) allDates.add(event.getStartDate());
        }
        AlertDialog.Builder dialogBuilder;
        dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle("Odaberi datum");
        dialogBuilder.setSingleChoiceItems(
                allDates.toArray(new String[allDates.size()]),
                allDates.indexOf(dateButton.getText().toString()),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String dateName = allDates.get(which);
                        dateButton.setText(dateName);
                        filter();
                        dialog.cancel();
                    }
                }
        );
        AlertDialog dialog = dialogBuilder.create();
        dialog.show();
    }

    private void filter() {
        removeHelperTypes(listOfEvents);
        filterByType();
        filterByDate();
        adjustList();
    }

    private void filterByType() {
        SqlGetEventsInfo repo = new SqlGetEventsInfo(getApplicationContext());
        String typeName = typeButton.getText().toString();
        switch (typeName) {
            case "Svi događaji":
                listOfEvents = repo.getAllEvents();
                break;

            case "Sport":
                listOfEvents = repo.getAllSportEvents();
                break;

            case "Znanje":
                listOfEvents = repo.getAllKnowledgeEvents();
                break;

            case "Favoriti":
                //TODO: listOfEvents=favoriti
                break;

            default:
                listOfEvents = repo.getAllEvents();
                break;
        }
        repo.close();
    }

    private void filterByDate() {
        String date = dateButton.getText().toString();
        if (!date.contains(".")) return;
        for (Iterator<Event> iterator = listOfEvents.iterator(); iterator.hasNext();) {
            Event event = iterator.next();
            if(event.getStartDate().equals(date)) continue;
            iterator.remove();
        }
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

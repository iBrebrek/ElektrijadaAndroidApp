package hr.fer.elektrijada.activities.events;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import hr.fer.elektrijada.R;
import hr.fer.elektrijada.model.events.Event;
import hr.fer.elektrijada.model.events.SportEvent;
import hr.fer.elektrijada.model.events.SportNameLabel;

import static hr.fer.elektrijada.activities.events.EventsOnTimeClickAction.openTimeDialog;

/**
 * Created by Ivica Brebrek
 */
public class SportEventsListAdapter extends BaseAdapter {
    private static final int NUMBER_OF_VIEW_TYPES = 3;
    private ArrayList<Event> listData;
    private LayoutInflater layoutInflater;
    private final Activity activity;

    public SportEventsListAdapter(Activity activity, ArrayList<Event> listData) {
        this.activity = activity;
        this.listData = listData;
        layoutInflater = LayoutInflater.from(activity);
    }

    @Override
    public int getViewTypeCount() {
        return NUMBER_OF_VIEW_TYPES;
    }

    public int getItemViewType(int position) {
        Event event = listData.get(position);
        if(event instanceof SportEvent) {
            return 0;
        }
        if(event instanceof SportNameLabel) {
            return 1;
        }
        return 2;
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        int type = getItemViewType(position);

        if (convertView == null) {
            if(type == 0) {
                convertView = layoutInflater.inflate(R.layout.events_list_sport, null);
                SportViewHolder holder = new SportViewHolder();
                holder.time = (TextView) convertView.findViewById(R.id.eventTimeSport);
                holder.teams = (TextView) convertView.findViewById(R.id.eventTeamsNameSport);
                //holder.type = (TextView) convertView.findViewById(R.id.eventDescriptionSport);
                holder.textLayout = (LinearLayout) convertView.findViewById(R.id.eventOnNameClickSport);
                holder.result = (TextView) convertView.findViewById(R.id.eventResultSport);
                convertView.setTag(holder);
                holder.adjustRow((SportEvent) listData.get(position));
            } else if (type == 1){
                convertView = layoutInflater.inflate(R.layout.events_list_sport_label, null);
                TextView name = (TextView) convertView.findViewById(R.id.eventsSportNameLabel);
                name.setText(listData.get(position).getName());
            } else {
                convertView = layoutInflater.inflate(R.layout.events_list_date, null);
                TextView date = (TextView) convertView.findViewById(R.id.eventsDate);
                date.setText(listData.get(position).getStartDate());
            }
        } else {
            if(type == 0) {
                SportViewHolder holder = (SportViewHolder) convertView.getTag();
                holder.adjustRow((SportEvent)listData.get(position));
            }else if(type ==1){
                TextView name = (TextView) convertView.findViewById(R.id.eventsSportNameLabel);
                name.setText(listData.get(position).getName());
            } else {
                TextView date = (TextView) convertView.findViewById(R.id.eventsDate);
                date.setText(listData.get(position).getStartDate());
            }
        }

        return convertView;
    }

    private void showToast(String message) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
    }


    private class SportViewHolder {
        TextView time;
        TextView teams;
        //TextView type;
        TextView result;
        LinearLayout textLayout;

        void adjustRow(final SportEvent event) {
            time.setText(event.getStartToEndHoursMinutes());
            time.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openTimeDialog(event,activity);
                }
            });
            teams.setText(event.getTeams());
            //type.setText(event.getName());
            textLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showToast("sport-naziv");
                }
            });
            result.setText(event.getResult());
            result.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showToast("sport-rezultat");
                }
            });
        }
    }
}

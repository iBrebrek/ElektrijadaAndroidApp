package hr.fer.elektrijada.activities.news;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import hr.fer.elektrijada.R;
import hr.fer.elektrijada.model.news.NewsEntry;

/**
 * razred koj omogucuje prikaz vijesti (naslova, autora i datuma) u list view
 * Created by Ivica Brebrek
 */
public class NewsFeedListAdapter extends BaseAdapter {
    private ArrayList<NewsEntry> listData;
    private LayoutInflater layoutInflater;
    private Context context;

    public NewsFeedListAdapter(Context context, ArrayList<NewsEntry> listData) {
        this.context = context;
        this.listData = listData;
        layoutInflater = LayoutInflater.from(context);
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
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.news_feed_list_row, null);
            holder = new ViewHolder();
            holder.headlineView = (TextView) convertView.findViewById(R.id.title);
            holder.reporterNameView = (TextView) convertView.findViewById(R.id.author);
            holder.reportedDateView = (TextView) convertView.findViewById(R.id.date);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.headlineView.setText(listData.get(position).getTitle());
        holder.reporterNameView.setText("Autor: " + listData.get(position).getAuthor(context));
        holder.reportedDateView.setText(listData.get(position).getTimeToString());
        return convertView;
    }

    static class ViewHolder {
        TextView headlineView;
        TextView reporterNameView;
        TextView reportedDateView;
    }
}

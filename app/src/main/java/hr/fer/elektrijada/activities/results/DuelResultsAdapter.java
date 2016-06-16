package hr.fer.elektrijada.activities.results;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import hr.fer.elektrijada.R;
import hr.fer.elektrijada.dal.sql.category.CategoryFromDb;
import hr.fer.elektrijada.dal.sql.duel.DuelFromDb;
import hr.fer.elektrijada.dal.sql.duelscore.DuelScoreFromDb;

/**
 * Created by Ivica Brebrek on 3.6.2016..
 */
public class DuelResultsAdapter extends BaseAdapter {

    private List<DuelFromDb> listData;
    private LayoutInflater layoutInflater;
    private final Context context;

    public DuelResultsAdapter(Context context, List<DuelFromDb> listData) {
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.row_duel_result, null);
            holder = new ViewHolder();
            holder.leftName = (TextView) convertView.findViewById(R.id.duel_result_row_left_name);
            holder.leftScore = (TextView) convertView.findViewById(R.id.duel_result_row_left_score);
            holder.rightName = (TextView) convertView.findViewById(R.id.duel_result_row_right_name);
            holder.rightScore = (TextView) convertView.findViewById(R.id.duel_result_row_right_score);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.leftName.setText(listData.get(position).getFirstCompetitor().getName());
        holder.rightName.setText(listData.get(position).getSecondCompetitor().getName());
        holder.leftScore.setText(String.valueOf(listData.get(position).getFirstComptetitorScore(context)));
        holder.rightScore.setText(String.valueOf(listData.get(position).getSecondComptetitorScore(context)));
        return convertView;
    }

    private static class ViewHolder {
        TextView leftName;
        TextView leftScore;
        TextView rightName;
        TextView rightScore;
    }
}

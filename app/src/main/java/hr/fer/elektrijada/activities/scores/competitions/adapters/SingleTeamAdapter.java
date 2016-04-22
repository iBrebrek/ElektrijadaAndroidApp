package hr.fer.elektrijada.activities.scores.competitions.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import hr.fer.elektrijada.R;
import hr.fer.elektrijada.dal.sql.competitionscore.CompetitionScoreFromDb;

/**
 * Created by Ivica Brebrek
 */
public class SingleTeamAdapter extends BaseAdapter {
    private List<CompetitionScoreFromDb> listData;
    private LayoutInflater layoutInflater;

    public SingleTeamAdapter(Context context, List<CompetitionScoreFromDb> listData) {
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
            convertView = layoutInflater.inflate(R.layout.competition_team_result_row, null);
            holder = new ViewHolder();
            holder.rankView = (TextView) convertView.findViewById(R.id.team_results_person_rank);
            holder.nameView = (TextView) convertView.findViewById(R.id.team_results_person_name);
            holder.resultsView = (TextView) convertView.findViewById(R.id.team_results_person_result);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.rankView.setText((position+1)+".");
        holder.nameView.setText(listData.get(position).getCompetitor().getName() + " " + listData.get(position).getCompetitor().getSureName());
        holder.resultsView.setText(String.valueOf(listData.get(position).getResult()));
        return convertView;
    }

    static class ViewHolder {
        TextView rankView;
        TextView nameView;
        TextView resultsView;
    }
}

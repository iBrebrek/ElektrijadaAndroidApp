package hr.fer.elektrijada.activities.scores.duels;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import hr.fer.elektrijada.R;
import hr.fer.elektrijada.dal.sql.duelscore.SqlDuelScoreRepository;
import hr.fer.elektrijada.dal.sql.user.UserFromDb;
import hr.fer.elektrijada.extras.MyInfo;
import hr.fer.elektrijada.model.score.DuelScore;
import hr.fer.elektrijada.model.score.DuelScoreCounter;

/**
 * Created by Ivica Brebrek
 */
class DuelScoresListAdapter extends BaseAdapter {
    private ArrayList<Object> listData = new ArrayList<>();
    private LayoutInflater layoutInflater;
    private DuelScoresActivity activity;
    private int duelId;
    private DuelScoreCounter myDuelScoreCounter;

    public DuelScoresListAdapter(DuelScoresActivity activity, ArrayList<DuelScoreCounter> listData, int duelId) {
        this.activity = activity;
        this.listData.addAll(listData);
        layoutInflater = LayoutInflater.from(activity);
        this.duelId = duelId;

        SqlDuelScoreRepository repo = new SqlDuelScoreRepository(activity);
        DuelScore duelScore = repo.getMyScore(duelId);
        if(duelScore.isSet()) {
            myDuelScoreCounter = new DuelScoreCounter(duelScore.getFirstScore(), duelScore.getSecondScore(), 1);
        }
        repo.close();
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        if(listData.get(position) instanceof DuelScoreCounter) {
            return 0;
        } else {
            return 1;
        }
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        int type = getItemViewType(position);

        if (convertView == null) {
            if(type == 0) {//score
                convertView = layoutInflater.inflate(R.layout.duel_scores_list_row, null);
                holder = new ViewHolder();
                holder.score = (TextView) convertView.findViewById(R.id.duel_scores_list_score);
                holder.countScore = (TextView) convertView.findViewById(R.id.duel_scores_list_count);
                convertView.setTag(holder);

            } else {//users
                convertView = layoutInflater.inflate(R.layout.duel_scores_list_user_list_row, null);
                holder = new ViewHolder();
                holder.userName = (TextView) convertView.findViewById(R.id.duel_scores_list_user_name);
                holder.delete = (TextView) convertView.findViewById(R.id.duel_scores_list_user_delete);
                convertView.setTag(holder);
            }
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if(type == 0) {//score
            final DuelScoreCounter duelScoreCounter = (DuelScoreCounter)listData.get(position);
            holder.score.setText(listData.get(position).toString());
            holder.countScore.setText("(" + duelScoreCounter.getCount() + ")");

            if(holder.isShown) {
                ((TextView)convertView.findViewById(R.id.duel_scores_list_triangleL)).setText("▲");
                ((TextView)convertView.findViewById(R.id.duel_scores_list_triangleR)).setText("▲");
            } else {
                ((TextView)convertView.findViewById(R.id.duel_scores_list_triangleL)).setText("▼");
                ((TextView)convertView.findViewById(R.id.duel_scores_list_triangleR)).setText("▼");
            }

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.isShown = !holder.isShown;

                    SqlDuelScoreRepository duelsScoreRepo = new SqlDuelScoreRepository(activity);
                    final ArrayList<UserFromDb> list = duelsScoreRepo.getAllUserThatPutThisScore(duelScoreCounter, duelId);
                    duelsScoreRepo.close();

                    if(holder.isShown) {
                        listData.addAll(position+1, list);
                    } else {
                        listData.removeAll(list);
                    }

                    notifyDataSetChanged();
                }
            });

        } else {//users
            final UserFromDb user = (UserFromDb)listData.get(position);
            holder.userName.setText(listData.get(position).toString());

            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SqlDuelScoreRepository scoreRepo = new SqlDuelScoreRepository(activity);
                    int duelScoreId = scoreRepo.getDuelScoreFromDb(duelId, user.getId()).getId();
                    scoreRepo.deleteDuelScore(duelScoreId);
                    scoreRepo.close();

                    if(user.getId() == MyInfo.getMyUsername(activity).getId()) {
                        ((EditText) activity.findViewById(R.id.acitivity_duel_score_my_score_first)).setText("");
                        ((EditText) activity.findViewById(R.id.acitivity_duel_score_my_score_second)).setText("");
                        Toast.makeText(activity, "Vaš rezultat je obrisan.", Toast.LENGTH_SHORT).show();
                        activity.myScore = new DuelScore();
                    } else {
                        Toast.makeText(activity, "Rezultat od korisnika " + user.toString() + " je obrisan", Toast.LENGTH_SHORT).show();
                    }

                    listData.remove(position);

                    DuelScoreCounter duelScoreCounter = getDuelScoreCounter(position);

                    duelScoreCounter.setCount(duelScoreCounter.getCount() - 1);
                    if(duelScoreCounter.getCount() == 0) {
                        listData.remove(duelScoreCounter);
                    }

                    notifyDataSetChanged();
                }
            });
        }
        return convertView;
    }

    private DuelScoreCounter getDuelScoreCounter(int startingPosition) { //ovo koristi pregled usera da zna kojem pripada
        int indexOfMyScore = startingPosition - 1;
        for (;!(listData.get(indexOfMyScore) instanceof DuelScoreCounter); indexOfMyScore--);
        return (DuelScoreCounter)listData.get(indexOfMyScore);
    }

    private static class ViewHolder {
        TextView score;
        TextView countScore;

        boolean isShown = false;
        TextView userName;
        TextView delete;
        //TextView notes;
    }

    public void addMyDuelScore(DuelScore duelScore) {
        int index = listData.indexOf(duelScore);
        if(index > -1) {
            myDuelScoreCounter = (DuelScoreCounter) listData.get(index);
            myDuelScoreCounter.setCount(myDuelScoreCounter.getCount() + 1);

            //ovo je u slucaju ako se vec pregledavaju korisnici tog rezultata
            int helpIndex = index + 1;
            if(helpIndex < listData.size() && (listData.get(helpIndex) instanceof UserFromDb)) {
                listData.add(helpIndex, MyInfo.getMyUsername(activity));
            }

        } else {
            myDuelScoreCounter = new DuelScoreCounter(duelScore.getFirstScore(), duelScore.getSecondScore(), 1);
            listData.add(myDuelScoreCounter);
        }
        notifyDataSetChanged();
    }

    public void modiyMyDuelScore(DuelScore duelScore) {
        if(duelScore.equals(myDuelScoreCounter)) {
            return;
        }

        deleteMyDuelScore();
        addMyDuelScore(duelScore);
    }

    public void deleteMyDuelScore() {
        if(myDuelScoreCounter == null) {
            return;
        }

        myDuelScoreCounter.setCount(myDuelScoreCounter.getCount() - 1);
        if(myDuelScoreCounter.getCount() == 0 ) {
            listData.remove(myDuelScoreCounter);
        }

        UserFromDb me = MyInfo.getMyUsername(activity);
        if(listData.contains(me)) {
            listData.remove(me);
        }

        notifyDataSetChanged();
    }
}

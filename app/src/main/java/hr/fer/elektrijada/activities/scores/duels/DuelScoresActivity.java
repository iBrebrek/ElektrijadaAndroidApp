package hr.fer.elektrijada.activities.scores.duels;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.method.KeyListener;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import hr.fer.elektrijada.MenuHandler;
import hr.fer.elektrijada.R;
import hr.fer.elektrijada.activities.BaseMenuActivity;
import hr.fer.elektrijada.dal.sql.duel.DuelFromDb;
import hr.fer.elektrijada.dal.sql.duel.SqlDuelRepository;
import hr.fer.elektrijada.dal.sql.duelscore.DuelScoreFromDb;
import hr.fer.elektrijada.dal.sql.duelscore.SqlDuelScoreRepository;
import hr.fer.elektrijada.extras.MyInfo;
import hr.fer.elektrijada.model.score.DuelScore;
import hr.fer.elektrijada.model.score.DuelScoreCounter;

/**
 * Created by Ivica Brebrek
 */
public class DuelScoresActivity extends BaseMenuActivity{

    private int duelId;
    private boolean isEditing; //el se ureduje moj rezultat
    private DuelScoresListAdapter listViewAdapter;
    DuelScore myScore; //nije private jer ju DuelScoresListAdapter mjenja u slucaju ako se u listi izbrisao vlastiti rezultat

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_duel_scores;
    }

    @Override
    protected int belongingToMenuItemId() {
        return MenuHandler.RESULTS_ID;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        duelId = getIntent().getIntExtra("duelId", -1);
        if(duelId == -1) {  //nebi se trebalo nikad dogodit
            Toast.makeText(this, "Nemoguće otvoriti rezultate.", Toast.LENGTH_SHORT).show();
            finish();
        }

        initTitleAndTeams();
        initMyAndOfficalResult();
        initScrollView();
    }

    private void initTitleAndTeams() {
        SqlDuelRepository duelRepo = new SqlDuelRepository(this);
        DuelFromDb duel = duelRepo.getDuel(duelId);
        duelRepo.close();

        setTitle(duel.getCategory().getName());

        ((TextView)findViewById(R.id.acitivity_duel_score_first_team)).setText(duel.getFirstCompetitor().getName());
        ((TextView)findViewById(R.id.acitivity_duel_score_second_team)).setText(duel.getSecondCompetitor().getName());
    }

    private void initMyAndOfficalResult() {
        SqlDuelScoreRepository duelScoreRepo = new SqlDuelScoreRepository(this);
        myScore = duelScoreRepo.getMyScore(duelId);
        DuelScore officailScore = duelScoreRepo.getOfficialScore(duelId);
        duelScoreRepo.close();

        if(officailScore.isSet()) {
            ((TextView)findViewById(R.id.acitivity_duel_score_official_score))
                    .setText(officailScore.toString());
        } else {
            findViewById(R.id.acitivity_duel_score_official_layout).setVisibility(View.GONE);
        }

        final EditText firstTeam = (EditText)findViewById(R.id.acitivity_duel_score_my_score_first);
        final EditText secondTeam = (EditText)findViewById(R.id.acitivity_duel_score_my_score_second);
        if(myScore.isSet()) {
            firstTeam.setText(roundIt(myScore.getFirstScore()));
            secondTeam.setText(roundIt(myScore.getSecondScore()));
        }

        firstTeam.setTag(firstTeam.getKeyListener());
        secondTeam.setTag(secondTeam.getKeyListener());
        firstTeam.setKeyListener(null);
        secondTeam.setKeyListener(null);

        setNextFocus(firstTeam, secondTeam.getId());
        setNextFocus(secondTeam, firstTeam.getId());

        final TextView editMyScore = (TextView)findViewById(R.id.duel_scores_edit_my_score);
        editMyScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isEditing) {
                    InputMethodManager inputMethodManager = (InputMethodManager)  getSystemService(Activity.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

                    //SAVE (napravi novi il update) (ili obrisi ako su oba prazna
                    SqlDuelScoreRepository duelScoreRepo = new SqlDuelScoreRepository(getApplicationContext());

                    boolean firstIsOK = firstTeam.getText().toString().trim().isEmpty();
                    boolean secondIsOK = secondTeam.getText().toString().trim().isEmpty();

                    if(firstIsOK || secondIsOK) {
                        if(!firstIsOK || !secondIsOK) {
                            Toast.makeText(getApplicationContext(),
                                    "Morate unijeti oba rezultata, ili oba izbrisati.",
                                    Toast.LENGTH_SHORT).show();
                            duelScoreRepo.close();
                            return;

                        //obrisi
                        } else {
                            if (myScore.isSet()) {
                                duelScoreRepo.deleteMyScore(duelId);
                                Toast.makeText(getApplicationContext(),
                                        "Vaš rezultat je obrisan.",
                                        Toast.LENGTH_SHORT).show();
                                myScore = new DuelScore();
                                listViewAdapter.deleteMyDuelScore();
                            }
                        }
                    } else {
                        //izmijeni
                        if(myScore.isSet()) {
                            DuelScoreFromDb myDuelScore = duelScoreRepo.getDuelScoreFromDb(duelId, MyInfo.getMyUsername(getApplication()).getId());
                            myDuelScore.setFirstScore(Double.parseDouble(firstTeam.getText().toString()));
                            myDuelScore.setSecondScore(Double.parseDouble(secondTeam.getText().toString()));
                            duelScoreRepo.updateDuelScore(myDuelScore);
                            Toast.makeText(getApplicationContext(),
                                    "Vaš rezultat je ažuriran.",
                                    Toast.LENGTH_SHORT).show();
                            myScore = new DuelScore(myDuelScore.getFirstScore(), myDuelScore.getSecondScore());
                            listViewAdapter.modiyMyDuelScore(myScore);

                        //dodaj novi
                        } else {
                            SqlDuelRepository duelRepo = new SqlDuelRepository(getApplicationContext());
                            DuelFromDb duel = duelRepo.getDuel(duelId);
                            duelRepo.close();
                            DuelScoreFromDb duelScore = new DuelScoreFromDb(
                                  Double.parseDouble(firstTeam.getText().toString()),
                                  Double.parseDouble(secondTeam.getText().toString()),
                                  duel,
                                  MyInfo.getMyUsername(getApplicationContext()),
                                  false
                            );
                            duelScoreRepo.addDuelScore(duelScore);
                            Toast.makeText(getApplicationContext(),
                                    "Vaš rezultat je dodan.",
                                    Toast.LENGTH_SHORT).show();
                            myScore = new DuelScore(duelScore.getFirstScore(), duelScore.getSecondScore());
                            listViewAdapter.addMyDuelScore(myScore);
                        }
                    }
                    duelScoreRepo.close();

                    isEditing = false;
                    editMyScore.setText("[EDIT]");
                    firstTeam.setKeyListener(null);
                    secondTeam.setKeyListener(null);

                } else {
                    isEditing = true;
                    editMyScore.setText("[SAVE]");
                    firstTeam.setKeyListener((KeyListener) firstTeam.getTag());
                    secondTeam.setKeyListener((KeyListener) secondTeam.getTag());

                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(firstTeam, InputMethodManager.SHOW_IMPLICIT);
                }
            }
        });
    }

    private void setNextFocus(EditText editText, int id) {
        editText.setNextFocusLeftId(id);
        editText.setNextFocusDownId(id);
        editText.setNextFocusUpId(id);
        editText.setNextFocusRightId(id);
    }

    //bez decimalnih ako je rezultat cijeli broj, 4.0->4, ali 4.5->4.5 (max jedna decimala)
    private String roundIt(double number) {
        if(number % 1 == 0) {
            return String.format("%.0f", number);
        } else {
            return String.format("%.1f", number);
        }
    }

    private void initScrollView() {
        SqlDuelScoreRepository duelsScoreRepo = new SqlDuelScoreRepository(this);
        final ArrayList<DuelScoreCounter> list = duelsScoreRepo.getAllResults(duelId);
        duelsScoreRepo.close();

        final ListView listView = (ListView) findViewById(R.id.list_duel_scores);
        listViewAdapter = new DuelScoresListAdapter(this, list, duelId);
        listView.setAdapter(listViewAdapter);
    }
}

package hr.fer.elektrijada.activities.events;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Calendar;
import java.util.GregorianCalendar;

import hr.fer.elektrijada.R;
import hr.fer.elektrijada.dal.sql.competition.SqlCompetitionRepository;
import hr.fer.elektrijada.dal.sql.duel.SqlDuelRepository;
import hr.fer.elektrijada.model.events.Event;
import hr.fer.elektrijada.model.events.KnowledgeEvent;
import hr.fer.elektrijada.model.events.SportEvent;
import hr.fer.elektrijada.util.DatePicker;
import hr.fer.elektrijada.util.TimePicker;

/**
 * Created by Ivica Brebrek
 */
public class EventsOnTimeClickAction {
    //ne treba nam konstruktor
    private EventsOnTimeClickAction() {
    }

    private static TimePicker startTime;
    private static TimePicker endTime;
    private static DatePicker startDate;
    private static DatePicker endDate;
    private static boolean hasEnd;
    private static Button toggleEndingTime;
    //private static Button startingTime;
    private static Button endingTime;
    private static Button startingDate;
    private static Button endingDate;
    /**
     * zastavica koja sluzi za:
     * ako nije imo kraj onda ce prvi put kad se doda kraj stavit kraj u isto vrijeme kao pocetak
     */
    private static boolean hadNoEnd;

    private static Event event;
    private static Activity activity;

    static void openTimeDialog(Event event, Activity activity) {
        EventsOnTimeClickAction.event = event;
        EventsOnTimeClickAction.activity = activity;

        final AlertDialog dialog = new AlertDialog.Builder(activity)
                .setView(getView())
                .setTitle("Odredi vrijeme")
                .setPositiveButton("Spremi", null) //null jer ce se ovo overridat
                .setNegativeButton("Odustani", null)
                .create();

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface d) {
                /*
                overridam pozitivan button da bi mogo sprijecit zatvaranje dialoga klikom na button(npr. kada pocetak>kraj)
                 */
                dialog.getButton(AlertDialog.BUTTON_POSITIVE)
                        .setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                positiveButtonFunction(dialog);
                            }
                        });
            }
        });
        dialog.show();
    }

    private static View getView() {
        LayoutInflater inflater = activity.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.events_time_dialog, null);
        initAllClassParams(dialogView);
        return dialogView;
    }

    private static void initAllClassParams(View dialogView) {
        Button startingTime = (Button) dialogView.findViewById(R.id.eventTimeDialogStartingTime);
        startTime = new TimePicker<>(activity.getFragmentManager(), startingTime, event.getTimeFrom());
        endingTime = (Button) dialogView.findViewById(R.id.eventTimeDialogEndingTime);
        endTime = new TimePicker<>(activity.getFragmentManager(), endingTime, event.getTimeTo());

        startingDate = (Button) dialogView.findViewById(R.id.eventTimeDialogStartingDate);
        startDate = new DatePicker<>(activity.getFragmentManager(), startingDate, event.getTimeFrom());
        endingDate = (Button) dialogView.findViewById(R.id.eventTimeDialogEndingDate);
        endDate = new DatePicker<>(activity.getFragmentManager(), endingDate, event.getTimeTo());

        hasEnd = event.getTimeTo() != null;
        hadNoEnd = !hasEnd;
        toggleEndingTime = (Button) dialogView.findViewById(R.id.eventTimeDialogAddOrRemoveEnd);
        toggleEndingTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hadNoEnd) {
                    hadNoEnd = false;
                    endDate.setDate(startDate.getDay(), startDate.getMonth(), startDate.getYear());
                    endTime.setTime(startTime.getHour(), startTime.getMinute());
                }
                hasEnd = !hasEnd;
                toggleEnd();
            }
        });
        toggleEnd();
    }

    private static void positiveButtonFunction(AlertDialog dialog) {
        try {
            Calendar end = null;
            if (hasEnd) {
                end = new GregorianCalendar(
                        endDate.getYear(),
                        endDate.getMonth() - 1,
                        endDate.getDay(),
                        endTime.getHour(),
                        endTime.getMinute()
                );
            }
            Calendar start = new GregorianCalendar(
                    startDate.getYear(),
                    startDate.getMonth() - 1,
                    startDate.getDay(),
                    startTime.getHour(),
                    startTime.getMinute()
            );
            event.setStartAndEnd(start.getTime(), end != null ? end.getTime() : null);
            updateEvent();
            ((EventsActivity) activity).onResume();
            dialog.dismiss();
        } catch (IllegalArgumentException exc) {
            Toast.makeText(activity, exc.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private static void updateEvent() {
        if(event instanceof SportEvent) {
            SqlDuelRepository repoDuel = new SqlDuelRepository(activity);
            SqlDuelRepository.DuelFromDb duel = repoDuel.getDuel(event.getId());
            duel.setTimeFrom(event.getStartYMDHM());
            duel.setTimeTo(event.getEndYMDHM());
            repoDuel.updateDuel(duel);
            repoDuel.close();
        } else if(event instanceof KnowledgeEvent) {
            SqlCompetitionRepository repoComp = new SqlCompetitionRepository(activity);
            SqlCompetitionRepository.CompetitionFromDb competition = repoComp.getCompetition(event.getId());
            competition.setTimeFrom(event.getStartYMDHM());
            competition.setTimeTo(event.getEndYMDHM());
            repoComp.updateCompetition(competition);
            repoComp.close();
        }
    }

    private static void toggleEnd() {
        if (hasEnd) {
            toggleEndingTime.setText("-");
            toggleEndingTime.setTextColor(Color.RED);
            endingDate.setVisibility(View.VISIBLE);
            endingTime.setVisibility(View.VISIBLE);
        } else {
            toggleEndingTime.setText("+");
            toggleEndingTime.setTextColor(Color.GREEN);
            endingDate.setVisibility(View.GONE);
            endingTime.setVisibility(View.GONE);
        }
    }
}

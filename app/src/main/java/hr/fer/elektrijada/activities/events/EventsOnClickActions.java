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
import hr.fer.elektrijada.model.events.Event;
import hr.fer.elektrijada.util.DatePicker;
import hr.fer.elektrijada.util.TimePicker;

/**
 * Created by Ivica Brebrek
 */
public class EventsOnClickActions {
    //ne treba nam konstruktor
    private EventsOnClickActions() {
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

    static void openTimeDialog(final Event event, final Activity activity) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity);
        dialogBuilder.setTitle("Odredi vrijeme");
        LayoutInflater inflater = activity.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.events_time_dialog, null);
        dialogBuilder.setView(dialogView);

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
        dialogBuilder.setPositiveButton("Spremi", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    Calendar start = new GregorianCalendar(
                            startDate.getYear(),
                            startDate.getMonth()-1,
                            startDate.getDay(),
                            startTime.getHour(),
                            startTime.getMinute()
                    );
                    Calendar end = null;
                    if (hasEnd) {
                        end = new GregorianCalendar(
                                endDate.getYear(),
                                endDate.getMonth()-1,
                                endDate.getDay(),
                                endTime.getHour(),
                                endTime.getMinute()
                        );
                    }
                    event.setStartAndEnd(start.getTime(), end!=null?end.getTime():null);
                    ((EventsActivity) activity).refreshAndSort();
                } catch (IllegalArgumentException exc) {
                    if (exc.getMessage().equals("End must be after start.")) {
                        Toast.makeText(activity, "Kraj ne može biti prije početka", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        //dialog.cancel();
                    } else {
                        throw exc;
                    }
                }
            }
        });
        dialogBuilder.setNegativeButton("Odustani", null);
        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
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

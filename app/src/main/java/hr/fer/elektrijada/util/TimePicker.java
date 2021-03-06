package hr.fer.elektrijada.util;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

/**
 *
 * Za dobar opis i upute pogledajte DatePicker, prilicno su slicni a tamo je detaljna dokumentacija
 *
 *
 * Created by Ivica Brebrek
 */
public class TimePicker <T extends TextView> {
    private T timeDisplay;
    private int hour;
    private int minute;

    /**
     * povezuje pogled s dijalogom odabira,
     * postavlja vrijeme na puni sat, npr ako je 16:37 postavlja na 16:00
     * 
     * @param manager   dovoljno je samo getFragmentManager()
     * @param textView  pogledan povezan s odabirom vremena
     */
    public TimePicker(final FragmentManager manager, T textView) {
        final TimePicker thisObject = this;
        timeDisplay = textView;
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerFragment newFragment = new TimePickerFragment();
                newFragment.setCaller(thisObject);
                newFragment.show(manager, "Odaberi vrijeme");
            }
        });
        Calendar today = Calendar.getInstance();
        setTime(today.get(Calendar.HOUR_OF_DAY), 0);  //minute izgledaju ruzno

    }

    /**
     *  postavlja pocetno vrijeme date
     */
    public TimePicker(final FragmentManager manager, T textView, Date date) {
        this(manager, textView);
        if(date != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            setTime(cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE));
        } else {
            setTime(0,0);
        }
    }


    public void setTime(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
        updateDisplay();
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    @Override
    public String toString() {
        return String.format("%s:%s", twoDigits(hour), twoDigits(minute));
    }

    private void updateDisplay() {
        timeDisplay.setText(toString());
    }

    private static String twoDigits(int c) {
        if (c >= 10) {
            return String.valueOf(c);
        } else {
            return "0" + String.valueOf(c);
        }
    }

    public static class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
        private TimePicker whoCalledMe;
        private void setCaller(TimePicker iDid) {
            if (whoCalledMe == null) {
                whoCalledMe = iDid;
            }
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            return new TimePickerDialog(getActivity(), this, whoCalledMe.hour, whoCalledMe.minute, true);
        }

        @Override
        public void onTimeSet(android.widget.TimePicker view, int hourOfDay, int minute) {
            whoCalledMe.setTime(hourOfDay, minute);
        }
    }
}

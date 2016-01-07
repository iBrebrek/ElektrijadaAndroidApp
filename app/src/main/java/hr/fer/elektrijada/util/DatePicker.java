package hr.fer.elektrijada.util;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

/**
 * Povezuje neki View (koji mora biti barem TextView, note: Button, EditText i jos neki naslijeduju TextView pa mogu biti i oni)
 * i dijalog odabira datuma.
 *
 *  * note: na hr View je pogled, da vas ne bi bunilo sto znaci pogled u ovoj dokumentaciji
 *
 * DatePicker ce sam updatat datum na tom pogledu nakon sto se obavi odabir u dijalogu.
 * Moze se i rucno namjestiti datum sa metodom setDate(dan, mjesec, godina),
 * ta metoda ce promijeniti text pogleda u taj datum.
 *
 * U dijalogu odabira pocetni datuma ce biti isti kao i datum u tekstu pogleda.
 *
 * Dodani su i getteri za dan, mjesec i godinu, pa mozete npr sa datePicker.getYear() dobiti
 * godinu (bit ce ista godini koja pise i u pogledu posto se automatski osvjezuje nakon odabira),
 * gettere sam dodao da ne bi morali bezveze pretvarati stringove pogleda u int.
 *
 * KORISTENJE OVOG RAZREDA:
 *
 * konstruktor prima 2 argumenta, jedan je FragmentManger, to mozete samo napisati getFragmentManager(),
 * drugi argument je pogled koji spajate sa dijalogom odabira datuma.
 * Konstruktor ce postaviti danasnji datum, ako zelite drugi datum koristite setDate
 *
 * primjer koristenja:
 * recimo da ste u nekom razredu koji je Activity, a Activity je povezan sa nekim layoutom koj ima
 * tipku id-a dateTextView, u toj situaciji bi ovako omogucili da se bira datum:
 *
 * TextView date = (TextView) findViewById(R.id.dateTextView);
 * DatePicker picker = new DatePicker(getFragmentManager(), date);
 *
 * to je to, samo te dvije linije i omogucili ste odabir datuma,
 * ako nakon odabira datuma zelite npr dobiti odabrani mjesec to cete dobiti sa:
 * picker.getMonth();
 *
 *
 *
 * @param <T> TIP pogleda
 *
 * Created by Ivica Brebrek
 */
public class DatePicker <T extends TextView> {
    /**
     * pogled na kojemu pise datum i koji moramo kliknut za odabir datuma
     */
    private T dateDisplay;
    /**
     * odabrana godina, pocetna godina ce biti danasnja godina
     */
    private int year;
    /**
     * odabrani mjesec, pocetni mjesec ce biti danasnji
     */
    private int month;
    /**
     * odabrani dan u mjesecu, danasnji ce biti pocetni
     */
    private int day;

    /**
     * omogucuje odabir datuma klikom na view, postavlja danasnji datum
     * @param manager ako se nalazite u nekom Activity samo upisite: getFragmentManager()
     * @param view odabrani pogled
     */
    public DatePicker(final FragmentManager manager, T view) {
        final DatePicker thisObject = this;
        dateDisplay = view;
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragment newFragment = new DatePickerFragment();
                newFragment.setCaller(thisObject);
                newFragment.show(manager, "Odaberi datum");
            }
        });
        Calendar today = Calendar.getInstance();
        setDate(today.get(Calendar.DAY_OF_MONTH), today.get(Calendar.MONTH)+1, today.get(Calendar.YEAR)); //jer mjeseci idu od 0
    }

    /**
     * kao gornji konstruktor, samo postavlja pocetni datum zadan sa date
     */
    public DatePicker(final FragmentManager manager, T view, Date date) {
        this(manager, view);
        Calendar cal = Calendar.getInstance();
        if(date != null) {
            cal.setTime(date);
        }
        setDate(cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.MONTH)+1, cal.get(Calendar.YEAR));
    }

    /**
     * postavlja zeljenu godinu (mijenja tekst pogleda, parametre ovog objekta, a i time pocetni datum dijaloga)
     */
    public void setDate(int day, int month, int year) {
        this.year = year;
        this.month = month;
        this.day = day;
        updateDisplay();
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    @Override
    public String toString() {
        return String.format("%s.%s.%s.", day, month, year);
    }

    /**
     * mjenja tekst pogleda u odabrani datum
     */
    private void updateDisplay() {
        dateDisplay.setText(toString());
    }

    /**
     * ovaj razred je zaduzen za funkciju dijaloga odabira datuma,
     * nikad nemojte sami raditi istancu ovoga razreda, pustite DatePicker da se pobrine za to
     */
    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
        /**
         * whoCalledMe je objekt (DatePicker) koji je kreirao dijalog, trebat ce nam da bi mogli vratiti odabrani datum
         */
        private DatePicker whoCalledMe;
        private void setCaller(DatePicker iDid) {
            if(whoCalledMe == null) {
                whoCalledMe = iDid;
            }
        }

        /**
         * otvara dijalog s nasim datumom,
         * baca NullPointerException (bar mislim) ako nije zadan whoCalledMe (to ce se dogoditi ako netko kreira objekt pomocu DatePicker.DataPickerFragmet, ali to nema potrebe raditi)
         */
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            return new DatePickerDialog(getActivity(), this, whoCalledMe.year, whoCalledMe.month-1, whoCalledMe.day);
        }

        /**
         * obavlja se kada je odabran datum, tj., kada smo gotovi s odabirom
         */
        @Override
        public void onDateSet(android.widget.DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            whoCalledMe.setDate(dayOfMonth, monthOfYear+1, year);
        }
    }
}

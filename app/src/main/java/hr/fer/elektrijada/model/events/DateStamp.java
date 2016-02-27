package hr.fer.elektrijada.model.events;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * ovo je event koji ce pokazivat samo datum pocetka.
 * Za sad se koristi samo za prikaz datuma u popisu svih dogadaja
 *
 * Created by Ivica Brebrek
 */
public class DateStamp extends Event{

    public DateStamp(Date date) {
        super(-1, "no name", date);
        Calendar cal = new GregorianCalendar();
        cal.setTime(getTimeFrom());
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        setTimeFrom(cal.getTime());
    }

    public boolean sameStartDate(Event event) {
        return event.getStartDate().equals(this.getStartDate());
    }

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof DateStamp)){
            return false;
        }
        return this.getTimeFrom().equals(((DateStamp) o).getTimeFrom());
    }

    @Override
    public int hashCode() {
        return this.getTimeFrom().hashCode();
    }
}

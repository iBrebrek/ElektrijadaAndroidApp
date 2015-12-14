package hr.fer.elektrijada.model.events;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Ivica Brebrek
 */
public abstract class Event implements Comparable<Event>{
    private int id;
    private String name;
    private Calendar timeFrom = null;
    private Calendar timeTo = null;

    public Event(int id, String name, Date start) {
        if(start == null || name == null) {
            throw new IllegalArgumentException("Event must have defined name and a start");
        }
        this.id = id;
        this.name = name;
        timeFrom = new GregorianCalendar();
        setTimeFrom(start);
    }

    public Event(int id, String name, Date timeFrom, Date timeTo) {
        this(id, name, timeFrom);
        if((timeTo!=null)) {
            this.timeTo = new GregorianCalendar();
            setTimeTo(timeTo);
        }
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if(name == null) {
            throw new IllegalArgumentException("Event must have a name");
        }
        this.name = name;
    }

    public Date getTimeFrom() {
        return timeFrom.getTime();
    }

    public Date getTimeTo() {
        if(timeTo == null) {
            return null;
        }
        return timeTo.getTime();
    }

    public void setTimeFrom(Date timeFrom) {
        if(timeFrom == null) {
            throw new IllegalArgumentException("Event must have a starting time");
        }
        if(timeTo != null && timeFrom.compareTo(getTimeTo())>0) {
            throw new IllegalArgumentException("End must be after start.");
        }
        this.timeFrom.setTime(timeFrom);
        secAndMilToZero(this.timeFrom);
    }

    public void setTimeTo(Date timeTo) {
        if(timeTo == null) {
            this.timeTo = null;
        }else {
            if(timeTo.compareTo(getTimeFrom())<0) {
                throw new IllegalArgumentException("End must be after start.");
            }
            if(this.timeTo == null) {
                this.timeTo = (Calendar) timeFrom.clone();
            }
            this.timeTo.setTime(timeTo);
            secAndMilToZero(this.timeTo);
        }
    }

    /**
     *
     * @param start     pocetak, nesmije biti null
     * @param end       kraj, ako je null onda ce to biti event bez kraja
     */
    public void setStartAndEnd(Date start, Date end) {
        if(start == null) {
            throw new IllegalArgumentException("Event must have a starting time");
        }
        if(end == null) {
            timeFrom.setTime(start);
            timeTo = null;
        } else {
            if(start.compareTo(end) > 0) {
                throw new IllegalArgumentException("End must be after start.");
            }
            timeTo = new GregorianCalendar(); //u slucaju ako do sad nije bilo krajja
            timeFrom.setTime(start);
            timeTo.setTime(end);
            secAndMilToZero(timeFrom);
            secAndMilToZero(timeTo);
        }
    }

    public String getStartDate() {
        return String.format(
                "%s.%s.%s", timeFrom.get(Calendar.DAY_OF_MONTH), (timeFrom.get(Calendar.MONTH)+1), timeFrom.get(Calendar.YEAR)
        );
    }

    /**
     * hh:mm.
     * ovisi postoji li definiran kraj,
     * vraca ili: "pocetak \n kraj" ili "pocetak"
     */
    public String getStartToEnd() {
        if(timeTo != null) {
            return getStart() + "\n" + getEnd();
        }
        return getStart();
    }

    public String getStart() {
        return twoDigits(timeFrom.get(Calendar.HOUR_OF_DAY)) + ":" + twoDigits(timeFrom.get(Calendar.MINUTE));
    }

    public String getEnd() {
        if(timeTo != null) {
            return twoDigits(timeTo.get(Calendar.HOUR_OF_DAY)) + ":" + twoDigits(timeTo.get(Calendar.MINUTE));
        }
        return "...";
    }

    private static String twoDigits(int c) {
        if (c >= 10) {
            return String.valueOf(c);
        } else {
            return "0" + String.valueOf(c);
        }
    }

    //moram stavljat sekunde i milisekunde na nula da bi mogo dobro usporedivat
    private void secAndMilToZero(Calendar cal) {
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
    }

    /**
     * manji je onaj koji prije pocinje
     * ako pocinju u isto vrijeme manji je onaj koji nema definiran kraj
     * ako imaju definiran kraj manji je onaj koji prije zavrsava
     */
    @Override
    public int compareTo(Event another) {
        int compareStart = timeFrom.compareTo(another.timeFrom);
        if (compareStart==0) {
            if(timeTo == null) {
                if(another.timeTo == null) {
                    //ako su skroz isti onda DateStamp mora biti prije
                    if(this instanceof DateStamp) {
                        return -1;
                    }
                    if(another instanceof DateStamp) {
                        return 1;
                    }
                }
                return -1;
            }
            if(another.timeTo == null) {
                return 1;
            }
            return timeTo.compareTo(another.timeTo);
        }
        return compareStart;
    }
}

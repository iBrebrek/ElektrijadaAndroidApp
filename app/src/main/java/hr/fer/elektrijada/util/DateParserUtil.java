package hr.fer.elektrijada.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Boris Milašinović on 10.11.2015..
 */
public class DateParserUtil {
    public static SimpleDateFormat getTimeFormat(){
        return new SimpleDateFormat("HH:mm dd.MM.yyyy.", Locale.getDefault());
    }

    public static Date stringToDate(String string){
        //Ivica Brebrek: jer mi neda da ne obradim gresku iako znam da ce uvijek biti format iz getTimeFormat....
        Date date;
        try {
            date = getTimeFormat().parse(string);
        }catch (ParseException exc){
            date = new Date(0);
        }
        return date;
    }
}

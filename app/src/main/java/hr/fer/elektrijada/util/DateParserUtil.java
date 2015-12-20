package hr.fer.elektrijada.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * mozda bi bilo bolje sve datume u bazi pohraniti kao string i pomocu ovoga uvijek spremiti/ucitvatati datume
 */
public class DateParserUtil {
    static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd. HH:mm:ss", Locale.getDefault());

    public static Date stringToDate(String string){
        if(string == null) return null;
        Date date;
        try {
            date = dateFormat.parse(string);
        }catch (ParseException exc){
            //ovo je 1.1.1970.
            date = new Date(0);
        }
        return date;
    }

    public static String dateToString(Date date) {
        return date==null ? null : dateFormat.format(date);
    }
}

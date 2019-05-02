package com.mycompany.pouloum.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DateUtil {
    
    public static Date toDate( String date )
        throws ParseException
    {
        SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
        return sf.parse(date);
    }
    
    public static Date DateNow() {
        Date now = new Date(System.currentTimeMillis());
        return now;
    }
    
    public static Date DateNew( int year, int month, int day, int hour, int minutes, int seconds ) {
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, day);
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, minutes);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        date = cal.getTime();
        return date;
    }
    
    // from: https://stackoverflow.com/questions/1555262/calculating-the-difference-between-two-java-date-instances
    
    /**
    * Get a diff between two dates
    * @param oldest the oldest date
    * @param newest the newest date
    * @param timeUnit the unit in which you want the diff
    * @return the diff value, in the provided unit
    */
   public static long DateDiff(Date oldest, Date newest, TimeUnit timeUnit) {
       long diffInMillies = newest.getTime() - oldest.getTime();
       return timeUnit.convert(diffInMillies, TimeUnit.MILLISECONDS);
   }
   
}

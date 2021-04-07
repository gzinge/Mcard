package com.tigercard.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    // format 24hre ex. 12:12 , 17:15
    private static String  HOUR_FORMAT = "HH:mm";

    private DateUtils() {    }

    public static String getCurrentHour() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdfHour = new SimpleDateFormat(HOUR_FORMAT);
        String hour = sdfHour.format(cal.getTime());
        return hour;
    }

    /**
     * @param  target  hour to check
     * @param  start   interval start
     * @param  end     interval end
     * @return true    true if the given hour is between
     */
    public static boolean isHourInInterval(String target, String start, String end) {
        return ((target.compareTo(start) >= 0)
                && (target.compareTo(end) <= 0));
    }

    /**
     * @param  start   interval start
     * @param  end     interval end
     * @return true    true if the current hour is between
     */
    public static boolean isNowInInterval(String start, String end) {
        return isHourInInterval(getCurrentHour(), start, end);
    }

    /**
     * @param  startEndTime   combine time (start - end)
     * @return true    true if the current hour is between
     */
    public static boolean isHourMatch(Date expectedTime , String startEndTime) {
        String[] start_end = startEndTime.split("-");
        String start = start_end[0].trim();
        String end = start_end[1].trim();
        SimpleDateFormat sdfHour = new SimpleDateFormat(HOUR_FORMAT);
        String hour = sdfHour.format(expectedTime.getTime());
        return isHourInInterval(hour, start, end);
    }

    public static LocalDate toLocalDate(String date){
        //convert String to LocalDate
        return LocalDate.parse(date, DateTimeFormatter.ofPattern("d-MM-yyyy"));
    }

    public static Date toDate(String date , String time) throws ParseException {
        return new SimpleDateFormat("d-MM-yyyy "+HOUR_FORMAT).parse(date+" "+time);
    }

    public static String getDay(String date){
        return
                toLocalDate(date).getDayOfWeek().toString();
    }

    public static boolean isMonday(String  date){
        return toLocalDate(date).getDayOfWeek().equals(DayOfWeek.MONDAY);
    }

    public static Date getWeekStartDate(String date) throws ParseException {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new SimpleDateFormat("d-MM-yyyy").parse(date));
        cal.set(Calendar.DAY_OF_WEEK, -1);
        return cal.getTime();
    }

    public static Date getWeekEndDate(String date) throws ParseException {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new SimpleDateFormat("d-MM-yyyy").parse(date));
        cal.add(Calendar.DATE, 6);// last day of week
        return cal.getTime();
    }
}

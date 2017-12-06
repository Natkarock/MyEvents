package com.natateam.myevents;

import android.content.Context;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import timber.log.Timber;

/**
 * Created by macbook on 07/07/ 15.
 */
public class TimeUtils {
    public static Date getDateFromString(String date){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy hh:mm");
        Date convertedDate=null;
        try {
            convertedDate = dateFormat.parse(date);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return convertedDate;
    }


    public static String getDateString (long date){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        return dateFormat.format(date);
    }

    public static String getTimeString (long date){
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        return dateFormat.format(date);
    }


    public static String getFormatDateFromCalendar(int year, int month, int day, String dateFormat){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR,year);
        calendar.set(Calendar.MONTH,month);
        calendar.set(Calendar.DAY_OF_MONTH,day);
        long longDate  = calendar.getTimeInMillis();
        return getFormatDateFromLong(longDate, dateFormat);
    }

    public  static String getFormatDateFromLong(long timeInMillis, String dateFormat){
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat(dateFormat);
        String formatDate= null;
        try {
            formatDate = simpleDateFormat.format(timeInMillis);
        }catch (NumberFormatException e){
            Timber.e("Invalid date format");
        }
        return formatDate;
    }

    public static long getLondDate(String date){
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat(Consts.DATE_FORMAT);
        long longDate = System.currentTimeMillis();
        try {
            longDate = simpleDateFormat.parse(date).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return  longDate;
    }

    public static long getBeginOfDaInMillis(long date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.HOUR,0);
        calendar.set(Calendar.MILLISECOND,0);
        calendar.set(Calendar.SECOND,0);
        return calendar.getTimeInMillis();
    }




}

package com.natateam.myevents

import android.app.AlarmManager
import android.content.Context

import java.text.ParseException
import java.text.SimpleDateFormat

import timber.log.Timber
import java.text.DateFormatSymbols
import java.util.*

/**
 * Created by macbook on 07/07/ 15.
 */
object TimeUtils {
    fun getDateFromString(date: String): Date? {
        val dateFormat = SimpleDateFormat("dd.MM.yyyy hh:mm")
        var convertedDate: Date? = null
        try {
            convertedDate = dateFormat.parse(date)
        } catch (e: Exception) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }

        return convertedDate
    }


    fun getDateString(date: Long): String {
        val dateFormat = SimpleDateFormat("dd.MM.yyyy")
        return dateFormat.format(date)
    }

    fun getTimeString(date: Long): String {
        val dateFormat = SimpleDateFormat("HH:mm")
        return dateFormat.format(date)
    }


    fun getFormatDateFromCalendar(year: Int, month: Int, day: Int, dateFormat: String): String? {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.DAY_OF_MONTH, day)
        val longDate = calendar.timeInMillis
        return getFormatDateFromLong(longDate, dateFormat)
    }

    fun getFormatDateFromLong(timeInMillis: Long, dateFormat: String): String? {
        val simpleDateFormat = SimpleDateFormat(dateFormat)
        var formatDate: String? = null
        try {
            formatDate = simpleDateFormat.format(timeInMillis)
        } catch (e: NumberFormatException) {
            Timber.e("Invalid date format")
        }

        return formatDate
    }

    fun getLondDate(date: String): Long {
        val simpleDateFormat = SimpleDateFormat(Consts.DATE_FORMAT)
        var longDate = System.currentTimeMillis()
        try {
            longDate = simpleDateFormat.parse(date).time
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return longDate
    }

    fun getBeginOfDaInMillis(date: Long): Long {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = date
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        calendar.set(Calendar.SECOND, 0)
        return calendar.timeInMillis
    }

    fun getCurrentYearDate(date:Long):Long{
        val calendar = Calendar.getInstance()
        var currentYear = calendar.get(Calendar.YEAR)
        calendar.timeInMillis = date
        calendar.set(Calendar.YEAR,currentYear)
        if (calendar.timeInMillis<=System.currentTimeMillis()){
            calendar.set(Calendar.YEAR,currentYear+1)
        }
        return calendar.timeInMillis
    }

    fun getDayOfWeekDate(dayOfWeek:Int):Long{
        if (dayOfWeek!=-1) {
            val calendar = Calendar.getInstance()
            calendar.set(Calendar.DAY_OF_WEEK, dayOfWeek)
            if (calendar.timeInMillis <= System.currentTimeMillis()) {
                calendar.timeInMillis += AlarmManager.INTERVAL_DAY * 7
            }
            return calendar.timeInMillis
        }else{
            return 0
        }
    }

    fun getDayOfWeekByString(dayString:String):Int{
        val weekdaysList = DateFormatSymbols(Locale.getDefault()).getWeekdays()
        for (i in 0..weekdaysList.size){
            if (weekdaysList[i].equals(dayString)){
                return i
            }
        }
        return -1
    }



}

package com.natateam.myevents.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import com.natateam.myevents.AlarmRecceiver
import com.natateam.myevents.Consts
import com.natateam.myevents.TimeUtils
import com.natateam.myevents.db.Contact
import com.natateam.myevents.db.Event
import io.realm.OrderedRealmCollection
import java.time.DayOfWeek
import javax.inject.Singleton

/**
 * Created by macbook on 29/11/ 15.
 */


const  val TYPE ="TYPE"
@Singleton
class AlarmHelper(private val context: Context) {
    private val am: AlarmManager

    init {
        am = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    }



    fun setEventAlarms(events: OrderedRealmCollection<Event>) {
        for (event in events) {
            setEventAlarm(event)
        }
    }

    fun setContactAlarms(contacts: OrderedRealmCollection<Contact>) {
        for (contact in contacts) {
            setContactAlarm(contact)
        }
    }

    fun setContactAlarm(contact: Contact?){
        if (contact!=null){
            val currentDate = TimeUtils.getCurrentYearDate(contact.contact_date_ms)
            setExactAlarmById(contact.id!!, currentDate, Consts.BIRTH_TYPE)
        }
    }



    fun setEventAlarm(event: Event) {
        if (event != null) {
            if (event.task_type!=null) {
                when (event.task_type) {
                    Consts.TODO_TYPE -> setExactAlarmById(event.id, event.dateMS, event.task_type!!)
                    Consts.REPEAT_TYPE -> {
                        if (event.task_repeat_days != null) {
                            for (weekday in event.getRepeatDaysArray()!!) {
                                val dayOfWeekTime = TimeUtils.getDayOfWeekDate(TimeUtils.getDayOfWeekByString(weekday))
                                setRepeatAlarmById(event.id, dayOfWeekTime, event.task_type!!)
                            }
                        }
                    }
                }
            }

        }
    }


    fun setExactAlarmById(id: Long, time: Long, type: String) {
        val appIntent = createAlarmIntent(id, type)
        if (Build.VERSION.SDK_INT < 23) {
            if (Build.VERSION.SDK_INT >= 19) {
                am.setExact(AlarmManager.RTC_WAKEUP, time, appIntent)
            } else {
                am.set(AlarmManager.RTC_WAKEUP, time, appIntent)
            }
        } else {
            am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, time, appIntent)

        }
    }

    fun setRepeatAlarmById(id: Long, time: Long, type: String) {
        val appIntent = createAlarmIntent(id, type)
        am.setInexactRepeating(AlarmManager.RTC_WAKEUP, time, AlarmManager.INTERVAL_DAY*7, appIntent)
    }

    fun createAlarmIntent(id: Long, type: String): PendingIntent {
        val intent = Intent(context, AlarmRecceiver::class.java)
        if (!type.equals(Consts.BIRTH_TYPE)) {
            intent.putExtra(Event.ID, id)
        } else {
            intent.putExtra(Contact.ID, id)
        }
        intent.putExtra(TYPE,type)
        val alarmId= getAlarmId(type,id)
        return PendingIntent.getBroadcast(context,alarmId , intent, PendingIntent.FLAG_ONE_SHOT)
    }


    fun getAlarmId(type:String,id:Long,dayOfWeek: Int=-1):Int{
        when(type){
            Consts.TODO_TYPE ->{
                return Consts.ALARM_PREFIX*Consts.TODO_ALARM_PREFIX+id.toInt()
            }
            Consts.REPEAT_TYPE->{
                return Consts.ALARM_PREFIX*dayOfWeek+id.toInt()
            }
            Consts.REPEAT_TYPE->{
                return  Consts.ALARM_PREFIX*Consts.BIRTH_ALARM_PREFIX+id.toInt()
            }
        }
        return id.toInt()
    }

    fun deleteAlarmById(id: Long, type: String) {
        val appIntent = createAlarmIntent(id,type)
        am.cancel(appIntent);
    }

}

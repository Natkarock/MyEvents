package com.natateam.myevents.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.natateam.myevents.AlarmRecceiver;
import com.natateam.myevents.app.EventApp;
import com.natateam.myevents.db.Event;
import com.natateam.myevents.db.RealmHelper;

import javax.inject.Singleton;

import io.realm.OrderedRealmCollection;

/**
 * Created by macbook on 29/11/ 15.
 */

@Singleton
public class AlarmHelper {
    private AlarmManager am;
    private Context context;
    public AlarmHelper(Context context) {
        this.context = context;
        am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    }

    public void setAlarms(OrderedRealmCollection<Event> events) {
        for (Event event : events) {
            setAlarmById(event.getId(), event.getDateMS());
        }
    }


    public void setAlarmById(long id, long time) {
        Intent intent = new Intent(context, AlarmRecceiver.class);
        intent.putExtra(Event.ID, id);
        PendingIntent appIntent = PendingIntent.getBroadcast(context, (int) id, intent, PendingIntent.FLAG_ONE_SHOT);
        if (Build.VERSION.SDK_INT < 23) {
            if (Build.VERSION.SDK_INT >= 19) {
                am.setExact(AlarmManager.RTC_WAKEUP, time, appIntent);
            } else {
                am.set(AlarmManager.RTC_WAKEUP, time, appIntent);
            }
        } else {
            am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, time, appIntent);

        }
    }

}

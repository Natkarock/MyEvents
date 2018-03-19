package com.natateam.myevents

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Typeface
import android.media.RingtoneManager
import android.os.Build
import android.support.v4.app.NotificationCompat
import android.support.v4.app.TaskStackBuilder
import android.support.v4.content.WakefulBroadcastReceiver

import com.natateam.myevents.app.EventApp
import com.natateam.myevents.db.Event
import com.natateam.myevents.db.RealmHelper

/**
 * Created by macbook on 23/08/ 15.
 */
class AlarmRecceiver : WakefulBroadcastReceiver() {
    private var realmHelper: RealmHelper? = null
    override fun onReceive(context: Context, intent: Intent) {
        realmHelper = EventApp.app!!.realmHelper
        if (intent.hasExtra(Event.ID)) {
            val event = realmHelper!!.getEventById(intent.getLongExtra(Event.ID, 0))
            if (event != null) {
                notifyEvent(event as Event, context)
            }
        }
    }


    fun notifyEvent(event: Event, context: Context) {
        val drawable: Int
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            drawable = R.mipmap.app_icon_silueth
        } else {
            drawable = R.mipmap.ic_launcher
        }
        val mBuilder = NotificationCompat.Builder(context).setColor(Color.RED)
                .setSmallIcon(drawable)
                .setContentTitle(event.title)
                .setContentText(event.desc).setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
        if (Build.VERSION.SDK_INT > +Build.VERSION_CODES.KITKAT) {
            mBuilder.setStyle(NotificationCompat.BigTextStyle().bigText(event.desc))
        }
        // Creates an explicit intent for an Activity in your app
        val resultIntent = Intent(context, MainActivity::class.java)

        // The stack builder object will contain an artificial back stack for the
        // started Activity.
        // This ensures that navigating backward from the Activity leads out of
        // your application to the Home screen.
        val stackBuilder = TaskStackBuilder.create(context)
        // Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(MainActivity::class.java!!)
        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent)
        val resultPendingIntent = stackBuilder.getPendingIntent(
                0,
                PendingIntent.FLAG_UPDATE_CURRENT
        )
        mBuilder.setContentIntent(resultPendingIntent)
        val mNotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        // mId allows you to update the notification later on.
        mNotificationManager.notify(event.id.toInt(), mBuilder.build())
    }



}

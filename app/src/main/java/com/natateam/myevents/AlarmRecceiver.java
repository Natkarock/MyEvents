package com.natateam.myevents;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.RingtoneManager;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.WakefulBroadcastReceiver;

import com.natateam.myevents.app.EventApp;
import com.natateam.myevents.db.Event;
import com.natateam.myevents.db.RealmHelper;

/**
 * Created by macbook on 23/08/ 15.
 */
public class AlarmRecceiver extends WakefulBroadcastReceiver {
    private RealmHelper realmHelper;
    @Override
    public void onReceive(Context context, Intent intent) {
        realmHelper = EventApp.getApp().getRealmHelper();
        if (intent.hasExtra(Event.ID)){
            Event event = realmHelper.getEventById(intent.getLongExtra(Event.ID,0));
            if (event!=null){
                notifyEvent(event,context);
            }
        }
    }


    public void notifyEvent(Event event,Context context){
        int drawable;
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            drawable = R.mipmap.app_icon_silueth;
        }else {
            drawable = R.mipmap.ic_launcher;
        }
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context).setColor(Color.RED)
                        .setSmallIcon(drawable)
                        .setContentTitle(event.getTitle())
                        .setContentText(event.getDesc()).
                        setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
        if (Build.VERSION.SDK_INT>+Build.VERSION_CODES.KITKAT){
            //Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),R.drawable.calendar_notification_big);
            Bitmap textBitmap = Converter.textAsBitmap(event.getTitle(),14,3,Color.RED, Typeface.DEFAULT);
            //Bitmap textBitmap = Converter.drawTextToBitmap(context,R.drawable.calendar_notification_big,event.getTitle());
            mBuilder.setStyle(new NotificationCompat.BigTextStyle().bigText(event.getDesc()));
        }
// Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(context, MainActivity.class);

// The stack builder object will contain an artificial back stack for the
// started Activity.
// This ensures that navigating backward from the Activity leads out of
// your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
// Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(MainActivity.class);
// Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
// mId allows you to update the notification later on.
        mNotificationManager.notify((int) event.getId(), mBuilder.build());
    }
}

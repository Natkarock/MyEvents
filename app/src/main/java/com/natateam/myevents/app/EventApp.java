package com.natateam.myevents.app;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.Toast;

import com.natateam.myevents.AlarmRecceiver;
import com.natateam.myevents.BuildConfig;
import com.natateam.myevents.alarm.AlarmHelper;
import com.natateam.myevents.db.Event;
import com.natateam.myevents.db.RealmHelper;

import javax.inject.Inject;

import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import io.realm.OrderedRealmCollection;
import io.realm.Realm;
import timber.log.Timber;

/**
 * Created by macbook on 07/07/ 15.
 */
public class EventApp extends Application implements HasActivityInjector {
    private static EventApp eventApp;
    private Toast toast;
    @Inject
    AlarmHelper alarmHelper;
    @Inject
    RealmHelper realmHelper;
    @Inject
    DispatchingAndroidInjector<Activity> activityDispatchingAndroidInjector;


    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        DaggerAppComponent.builder().application(this).build().inject(this);
        eventApp = this;
        alarmHelper.setAlarms(realmHelper.getAllEvents());
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }

    public static EventApp getApp(){
        return eventApp;
    }

    public void showToast(String message) {
        if (toast != null) {
            toast.cancel();
        }
        int duration = Toast.LENGTH_LONG;
        toast = Toast.makeText(this, message, duration);
        toast.show();
    }

    public AlarmHelper getAlarmHelper() {
        return alarmHelper;
    }

    public RealmHelper getRealmHelper() {
        return realmHelper;
    }

    @Override
    public DispatchingAndroidInjector<Activity> activityInjector() {
        return activityDispatchingAndroidInjector;
    }

}

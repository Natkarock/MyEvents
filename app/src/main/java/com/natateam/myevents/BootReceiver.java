package com.natateam.myevents;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;

import com.natateam.myevents.app.EventApp;

import javax.inject.Inject;

/**
 * Created by macbook on 02/09/ 15.
 */
public class BootReceiver extends WakefulBroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        EventApp.Companion.getApp().setAlarms();
    }
}

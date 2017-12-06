package com.natateam.myevents.app;

import android.app.Application;
import android.content.Context;

import com.natateam.myevents.alarm.AlarmHelper;
import com.natateam.myevents.db.RealmHelper;
import com.natateam.myevents.event.EventComponet;
import com.natateam.myevents.event.EventModule;
import com.natateam.myevents.mainlist.MainActivityComponent;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by macbook on 29/11/ 15.
 */
@Module(subcomponents = {
        MainActivityComponent.class, EventComponet.class}
)
public class AppModule {


    @Provides
    @Singleton
    Context provideApp(Application application){
        return application;
    }


    @Provides
    @Singleton
    AlarmHelper alarmHelper(Context context){
        return new AlarmHelper(context);
    }

    @Provides
    @Singleton
    RealmHelper provideRealm(AlarmHelper alarmHelper){
        return  new RealmHelper(alarmHelper);
    }




}

package com.natateam.myevents.event;

import com.natateam.myevents.EventActivity;
import com.natateam.myevents.alarm.AlarmHelper;
import com.natateam.myevents.db.RealmHelper;

import dagger.Module;
import dagger.Provides;

/**
 * Created by macbook on 30/11/ 15.
 */
@Module
public class EventModule {

    @Provides
    public EventContract.EventView provideEventView(EventActivity eventActivity){
        return eventActivity;
    }

    @Provides
    public EventContract.EventPresenter presenter (EventContract.EventView view, RealmHelper helper, AlarmHelper alarmHelper){
        return  new EventPresenerImpl(view,helper,alarmHelper);
    }

}

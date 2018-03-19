package com.natateam.myevents.event

import com.natateam.myevents.EventActivity
import com.natateam.myevents.alarm.AlarmHelper
import com.natateam.myevents.db.RealmHelper

import dagger.Module
import dagger.Provides

/**
 * Created by macbook on 30/11/ 15.
 */
@Module
class EventModule(val eventActivity: EventContract.EventView) {

    @Provides
    fun provideEventView(): EventContract.EventView = eventActivity

    @Provides
    fun presenter(view: EventContract.EventView, helper: RealmHelper, alarmHelper: AlarmHelper): EventContract.EventPresenter {
        return EventPresenerImpl(view, helper, alarmHelper)
    }

}

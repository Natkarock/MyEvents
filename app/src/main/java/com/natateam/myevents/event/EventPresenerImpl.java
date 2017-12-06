package com.natateam.myevents.event;

import com.natateam.myevents.alarm.AlarmHelper;
import com.natateam.myevents.db.Event;
import com.natateam.myevents.db.RealmHelper;

import javax.inject.Inject;

/**
 * Created by macbook on 30/11/ 15.
 */

public class EventPresenerImpl implements EventContract.EventPresenter {
    EventContract.EventView view;
    RealmHelper realmHelper;
    AlarmHelper alarmHelper;

    @Inject
    public EventPresenerImpl(EventContract.EventView view, RealmHelper realmHelper, AlarmHelper alarmHelper) {
        this.view = view;
        this.realmHelper = realmHelper;
        this.alarmHelper = alarmHelper;
    }




    @Override
    public void isViewAttached() {

    }

    @Override
    public void createOrUpdateEvent(Event event, String title, String desc, String date, String time, long dateMS) {
        realmHelper.createorUpdateEvent(event, title, desc, date, time, dateMS);

    }

    @Override
    public void setData(long id) {
        Event event = realmHelper.getEventById(id);
        view.setData(event);
    }
}

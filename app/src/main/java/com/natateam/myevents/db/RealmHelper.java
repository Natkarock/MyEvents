package com.natateam.myevents.db;

import com.natateam.myevents.alarm.AlarmHelper;
import com.natateam.myevents.app.EventApp;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.realm.OrderedRealmCollection;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by macbook on 07/07/ 15.
 */
public class RealmHelper {
    private static RealmHelper realmHelper;
    private Realm realm;
    private AlarmHelper alarmHelper;
    RealmConfiguration config;


    @Inject
    public RealmHelper(AlarmHelper alarmHelper){
        config = new RealmConfiguration.Builder()
                .name("eventrealm")
                .schemaVersion(0)
                .build();

        this.alarmHelper = alarmHelper;
    }


    public OrderedRealmCollection<Event> getAllEvents(){
        realm = Realm.getInstance(config);
        OrderedRealmCollection<Event> events = realm.where(Event.class).greaterThan("dateMS",System.currentTimeMillis()).findAll();;
        return events.sort("dateMS");
    }

    public Event getEventById(long id){
        realm = Realm.getInstance(config);
        Event event = realm.where(Event.class).equalTo("id",id).findFirst();
        return event;
    }



    public void createorUpdateEvent(final Event event, final String title, final String desc, final String date, final String time, final long dateMS){
        realm = Realm.getInstance(config);
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                try {
                    Event eventIn=event;
                    if (event==null){
                        long id = RealmAutoIncrement.getInstance(Event.class,realm).getNextIdFromModel();
                        eventIn = realm.createObject(Event.class, id);
                    }
                    eventIn.setDate(date);
                    eventIn.setDateMS(dateMS);
                    eventIn.setTime(time);
                    eventIn.setDesc(desc);
                    eventIn.setTitle(title);
                    alarmHelper.setAlarmById(eventIn.getId(),eventIn.getDateMS());
                }finally {
                    realm.close();
                }

            }
        });

    }


}

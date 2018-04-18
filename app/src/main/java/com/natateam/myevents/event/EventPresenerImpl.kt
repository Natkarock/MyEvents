package com.natateam.myevents.event

import android.support.v7.app.AppCompatActivity
import com.natateam.myevents.Consts
import com.natateam.myevents.FirebaseHelper
import com.natateam.myevents.alarm.AlarmHelper
import com.natateam.myevents.db.Contact
import com.natateam.myevents.db.Event
import com.natateam.myevents.db.RealmHelper
import com.natateam.myevents.model.ContactModel
import com.natateam.myevents.model.EventModel
import io.realm.RealmObject

import javax.inject.Inject

/**
 * Created by macbook on 30/11/ 15.
 */

class EventPresenerImpl @Inject
constructor(public var view: EventContract.EventView,public  var realmHelper: RealmHelper,public var alarmHelper: AlarmHelper) : EventContract.EventPresenter {
    override fun deleteEvent(id: String) {
        FirebaseHelper.deleteEventById(id)
    }

    override fun deleteContact(id: String) {
        FirebaseHelper.deleteContactById(id)
    }


    override fun createOrUpdateContact(contactModel: ContactModel) {
        FirebaseHelper.addContact(contactModel)
    }



    override fun isViewAttached() {

    }

    override fun createOrUpdateEvent(eventModel: EventModel ) {
        FirebaseHelper.addEvent( eventModel)

    }



    override fun setData(id: String, type: String) {
        if (type == Consts.BIRTH_TYPE){
            FirebaseHelper.getContactyId(id){contactModel -> view.setContactData(contactModel) }
        }else{
            FirebaseHelper.getEventById(id){eventModel -> view.setEventData(eventModel) }
        }
    }

}

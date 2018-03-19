package com.natateam.myevents.event

import com.natateam.myevents.Consts
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
    override fun deleteObject(model: RealmObject?) {
        if (model!=null) {
            realmHelper.deleteObject(model!!)
        }
    }

    override fun createOrUpdateContact(contact: Contact?, contactModel: ContactModel) {
        realmHelper.createOrUpdateContact(contact,contactModel)
    }



    override fun isViewAttached() {

    }

    override fun createOrUpdateEvent(event: Event?, title: String, desc: String, date: String, time: String, dateMS: Long,type:String, task_repeat_days:String? ) {
        realmHelper.createorUpdateEvent(event, EventModel(null, title = title,desc =  desc, task_date = date,
                task_time =  time,dateMS =  dateMS, task_type = type, task_repeat_days = task_repeat_days))

    }



    override fun setData(id: Long, type: String) {
        if (type!= Consts.BIRTH_TYPE){
            view.setData(realmHelper.getEventById(id) )
        }else{
            view.setData(realmHelper.getContactById(id))
        }
    }

}

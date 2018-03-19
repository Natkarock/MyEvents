package com.natateam.myevents.event

import com.natateam.myevents.BasePresenter
import com.natateam.myevents.BaseView
import com.natateam.myevents.db.Contact
import com.natateam.myevents.db.Event
import com.natateam.myevents.model.ContactModel
import io.realm.RealmModel
import io.realm.RealmObject

/**
 * Created by macbook on 30/11/ 15.
 */

interface EventContract {
    interface EventView : BaseView {
        fun setData(model:RealmObject?)
    }

    interface EventPresenter : BasePresenter<EventView> {
        fun createOrUpdateEvent(event: Event?, title: String, desc: String, date: String, time: String, dateMS: Long,type:String,task_repeat_days:String? = null)
        fun createOrUpdateContact(contact: Contact?, contactModel: ContactModel)
        fun deleteObject(model: RealmObject?)
        fun setData(id: Long, type: String)
    }

}

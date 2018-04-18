package com.natateam.myevents.event

import com.natateam.myevents.BasePresenter
import com.natateam.myevents.BaseView
import com.natateam.myevents.db.Contact
import com.natateam.myevents.db.Event
import com.natateam.myevents.model.ContactModel
import com.natateam.myevents.model.EventModel
import io.realm.RealmModel
import io.realm.RealmObject

/**
 * Created by macbook on 30/11/ 15.
 */

interface EventContract {
    interface EventView : BaseView {
        fun setContactData(model:ContactModel)
        fun setEventData(model:EventModel)
    }

    interface EventPresenter : BasePresenter<EventView> {
        fun createOrUpdateEvent( eventModel: EventModel)
        fun createOrUpdateContact(contactModel: ContactModel)
        fun deleteEvent(id: String)
        fun deleteContact(id: String)
        fun setData(id: String, type: String)
    }

}

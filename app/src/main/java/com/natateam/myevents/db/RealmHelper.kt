package com.natateam.myevents.db

import com.natateam.myevents.Consts
import com.natateam.myevents.TimeUtils
import com.natateam.myevents.alarm.AlarmHelper
import com.natateam.myevents.model.ContactModel
import com.natateam.myevents.model.EventModel
import io.realm.*

import javax.inject.Inject

/**
 * Created by macbook on 07/07/ 15.
 */
class  RealmHelper @Inject
constructor(private val alarmHelper: AlarmHelper) {
    var realm: Realm? = null
    internal var config: RealmConfiguration
    fun getAllEvents(): OrderedRealmCollection<Event> {
        realm = Realm.getInstance(config)
        val events = realm!!.where(Event::class.java).greaterThan("dateMS", System.currentTimeMillis()).findAll()
        return events.sort("dateMS")
    }

    fun getAllContacts(): OrderedRealmCollection<Contact> {
        realm = Realm.getInstance(config)
        val contacts = realm!!.where(Contact::class.java).findAll()
        return contacts
    }


    init {
        config = RealmConfiguration.Builder()
                .name("eventrealm")
                .schemaVersion(0)
                .build()
        realm = Realm.getInstance(config)
    }

    fun getEventsByType(type: String): OrderedRealmCollection<Event> {
        realm = Realm.getInstance(config)
        return realm!!.where(Event::class.java).equalTo("task_type", type).findAll();
    }

    fun getEventById(id: Long): RealmObject? {
        realm = Realm.getInstance(config)
        return realm!!.where(Event::class.java).equalTo("id", id).findFirst()
    }

    fun getContactById(id: Long): Contact? {
        realm = Realm.getInstance(config)
        return realm!!.where(Contact::class.java).equalTo("id", id).findFirst()
    }


    fun createorUpdateEvent(event: Event?, eventModel: EventModel): Long? {
        realm = Realm.getInstance(config)
        var id: Long
        if (event == null) {
            id = RealmAutoIncrement.getInstance(Event::class.java, realm).nextIdFromModel.toLong()
        } else {
            id = event.id
        }
        realm!!.executeTransaction { realm ->
            try {
                var eventIn = event
                if (event == null) {

                    eventIn = realm.createObject(Event::class.java, id)
                }
                eventIn!!.task_date = eventModel.task_date
                eventIn.dateMS = eventModel.dateMS
                eventIn.task_time = eventModel.task_time
                eventIn.desc = eventModel.desc
                eventIn.title = eventModel.title
                eventIn.task_type = eventModel.task_type
                eventIn.task_repeat_days = eventModel.task_repeat_days
                //alarmHelper.setEventAlarm(eventIn)
            } finally {
                realm.close()

            }
        }
        return id
    }


    fun createOrUpdateContact(id: Long, contactModel: ContactModel): Long? {
        val contact = getContactById(id)
        return createOrUpdateContact(contact, contactModel)
    }

    fun updateContactBirthday(contact: Contact?, birthday: String) {
        if (contact != null) {

            realm = Realm.getInstance(config)
            realm!!.executeTransaction { realm ->
                try {
                    contact.contact_birthday = birthday
                } finally {
                    realm.close()
                }

            }

        }
    }

    fun createOrUpdateContact(contact: Contact?, contactModel: ContactModel): Long? {
        realm = Realm.getInstance(config)
        var id: Long = 0
        if (contact == null) {
            id = RealmAutoIncrement.getInstance(Contact::class.java, realm).nextIdFromModel.toLong()
        } else {
            id = contact.id!!
        }
        realm!!.executeTransaction() { realm ->
            try {
                var contactIn = contact
                if (contact == null) {
                    contactIn = realm.createObject(Contact::class.java, id)

                }
                contactIn?.contact_email = contactModel.contact_email
                contactIn?.contact_google_id = contactModel.contact_google_id
                contactIn?.contact_name = contactModel.contact_name
                contactIn?.contact_group = contactModel.contact_group
                contactIn?.contact_phone = contactModel.contact_phone
                contact?.contact_birthday = contactModel.contact_birthday
                //alarmHelper.setContactAlarm(contactIn)
            } finally {
                realm.close()
            }
        }
        return id
    }

    fun deleteObject(model: RealmObject) {
        realm = Realm.getInstance(config)
        realm?.executeTransaction { realm ->
            try {
                if (model is Event) {
                    with(model as Event) {
                        //alarmHelper.deleteAlarmById(id, task_type!!)
                    }
                }else if (model is Contact){
                    with(model as Contact) {
                        //alarmHelper.deleteAlarmById(id!!, Consts.BIRTH_TYPE)
                    }
                }
                model.deleteFromRealm()
            } finally {
                realm.close()
            }
        }
    }


}

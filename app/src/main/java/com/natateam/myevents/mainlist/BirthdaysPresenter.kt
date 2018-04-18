package com.natateam.myevents.mainlist

import com.natateam.myevents.Consts
import com.natateam.myevents.FirebaseHelper
import com.natateam.myevents.db.Contact
import com.natateam.myevents.db.Event
import com.natateam.myevents.db.RealmHelper
import com.natateam.myevents.model.ContactModel
import com.natateam.myevents.model.EventModel
import io.realm.RealmChangeListener
import io.realm.RealmObjectChangeListener
import javax.inject.Inject

/**
 * Created by macbook on 09/03/ 15.
 */
class BirthdaysPresenter
@Inject
constructor( var view: MainCotractor.BirthdayView, var realmHelper: RealmHelper) :
        MainCotractor.BirthdayPresenter {

    var contact: Contact? = null
    var id: String? = null

    override fun isViewAttached() {
        //realmHelper.realm!!.addChangeListener({ showEventActivity() })
    }

    override fun loadData() {
        //view.loadData(realmHelper.getAllContacts())
    }


    fun clearCurrenContact() {
        contact = null
    }

    fun saveContact(contactModel: ContactModel) {
        id = FirebaseHelper.addContact(contactModel)
        //id = realmHelper.createOrUpdateContact(contact, contactModel)
        showEventActivity()
    }

    fun showEventActivity(){
        /*if (id!=null) {
            contact = realmHelper.getContactById(id!!)
        }*/
        if (id!=null) {
            view.showEventActivity(id!!)
        }
    }


    fun saveContactBirthday(birthday: String) {
        //realmHelper.updateContactBirthday(contact, birthday)
    }


}



package com.natateam.myevents

import com.google.firebase.auth.FirebaseAuth
import javax.inject.Singleton
import com.firebase.ui.auth.AuthUI
import android.support.v4.app.ActivityCompat.startActivityForResult
import android.util.Log
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.Query
import com.natateam.myevents.alarm.TYPE
import com.natateam.myevents.model.ContactModel
import com.natateam.myevents.model.EventModel
import com.natateam.myevents.model.TASK_TYPE_FIELD
import java.util.*
import java.util.Arrays.asList
import com.google.firebase.firestore.DocumentSnapshot
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.EventListener


/**
 * Created by macbook on 18/03/ 15.
 */
@Singleton
const val EVENTS ="events"
const val CONTACTS = "contacts"
const val USER_ID_FIELD = "user_id"
object FirebaseHelper{
    val firebaseAuth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()
    init {
        val settings = FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .build();
        db.setFirestoreSettings(settings)
    }

    fun isSignIn():Boolean{
        if (firebaseAuth!=null){
            if (firebaseAuth.currentUser!=null){
               return true
            }else
                return  false

        }
        return false
    }

    fun addEvent(eventModel: EventModel):String?{
        if (isSignIn()) {
            var eventReference:DocumentReference? = null
            if (eventModel.event_id==null) {
                eventReference = db.collection(EVENTS).document()
                eventModel.event_id = eventReference.id
                eventModel.user_id = firebaseAuth.currentUser!!.uid
            }else{
                eventReference = db.collection(EVENTS).document(eventModel.event_id!!)
            }
            eventReference.set(eventModel).addOnSuccessListener { Log.d(EVENTS,"successadd") }.addOnFailureListener { exception -> Log.d(EVENTS,exception.message) }
            return  eventModel.event_id
        }
        return null
    }

    fun addContact(contactModel: ContactModel):String?{
        if (isSignIn()) {
            var contactReference:DocumentReference? = null
            if (contactModel.contact_id==null) {
                contactReference = db.collection(CONTACTS).document()
                contactModel.contact_id = contactReference.id
                contactModel.user_id = firebaseAuth.currentUser!!.uid
            }else{
                contactReference = db.collection(CONTACTS).document(contactModel.contact_id!!)
            }
            contactReference.set(contactModel).addOnSuccessListener { Log.d(CONTACTS,"successadd") }.addOnFailureListener { exception -> Log.d(CONTACTS,exception.message) }
            return  contactModel.contact_id
        }
        return  null
    }

    fun getEventTypeQuery(type:String?):Query?{
        if (isSignIn()) {
            val query = FirebaseFirestore.getInstance()
                    .collection(EVENTS)
                    .whereEqualTo(USER_ID_FIELD, firebaseAuth.currentUser!!.uid).whereEqualTo(TASK_TYPE_FIELD, type)
            /*query.addSnapshotListener{t, e -> if (e!=null){
                Log.d(EVENTS,e.message)
            }else{
                Log.d (EVENTS, t.toString())
            } }*/
            return query
        }
        return null
    }

    fun getContactQuery():Query?{
        if (isSignIn()) {
            val query = FirebaseFirestore.getInstance()
                    .collection(CONTACTS)
                    .whereEqualTo(USER_ID_FIELD, firebaseAuth.currentUser!!.uid)
            return query
        }
        return null
    }

    fun getEventById(id:String?,eventAction:(EventModel)->Unit ){
        if (id!=null) {
            val docRef = db.collection(EVENTS).document(id)
            docRef.get().addOnSuccessListener {
                documentSnapshot -> val event = documentSnapshot.toObject(EventModel::class.java)
                eventAction(event)}
        }
    }

    fun getContactyId(id:String?,contactAction:(ContactModel)->Unit ){
        if (id!=null) {
            val docRef = db.collection(CONTACTS).document(id)
            docRef.get().addOnSuccessListener {
                documentSnapshot -> val contact = documentSnapshot.toObject(ContactModel::class.java)
                contactAction(contact)}
        }
    }


    fun deleteEventById(id: String?){
        if (id!=null) {
            db.collection(EVENTS).document(id).delete().addOnSuccessListener {  }.addOnFailureListener {  }
        }
    }

    fun deleteContactById(id: String?){
        if (id!=null) {
            db.collection(CONTACTS).document(id).delete().addOnSuccessListener {  }.addOnFailureListener {  }
        }
    }


}
package com.natateam.myevents.db

import com.natateam.myevents.Consts
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * Created by macbook on 07/03/ 15.
 */
open class Contact:RealmObject{
    @PrimaryKey
    var id: Long?= 0
    var contact_google_id: String? = null
    var contact_email:String?= null
    var contact_name: String? = null
    var contact_group: String? =null
    var contact_phone: String? = null
    var contact_birthday: String? = null
    var contact_time: String? = Consts.DEFAULT_TIME
    var contact_date_ms:Long=0
    var is_need_alarm:Boolean = true
    var user_id: String? = null
    constructor(contact_id:Long){
       this.id = contact_id
    }
    constructor(){

    }


    companion object {
        val ID = "CONTACT_ID"
    }
}

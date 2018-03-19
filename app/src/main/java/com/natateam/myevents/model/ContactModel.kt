package com.natateam.myevents.model

import com.natateam.myevents.Consts

/**
 * Created by macbook on 09/03/ 15.
 */
class ContactModel( var contact_id: Long?= null,
                    var contact_google_id: String? = null,
                    var contact_email:String?= null,
                    var contact_name: String? = null,
                    var contact_group: String? =null,
                    var contact_phone: String? = null,
                    var contact_birthday: String? =null,
                    var contact_time: String? = Consts.DEFAULT_TIME,
                    var contact_date_ms:Long=0,
                    var is_need_alarm:Boolean = true,
                    var user_id: String? = null)
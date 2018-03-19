package com.natateam.myevents.db

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * Created by macbook on 07/07/ 15.
 */
open class Event : RealmObject {
    @PrimaryKey
    var id: Long = 0
    var dateMS: Long = 0
    var task_date: String? = null
    var task_time: String? = null
    var title: String? = null
    var desc: String? = null
    var isTask_is_done: Boolean = false
    var task_type: String? = null
    var task_repeat_days: String? = null
    var task_repeat_times: String? = null
    var is_need_alarm:Boolean = true
    var user_id: String? = null

    fun getRepeatDaysArray():List<String>?{
        return task_repeat_days?.split(" ")
    }

    constructor(id: Long) {
        this.id = id
    }

    constructor() {}

    companion object {
        val ID = "EVENT_ID"
    }
}

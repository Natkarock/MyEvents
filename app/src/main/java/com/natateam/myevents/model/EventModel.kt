package com.natateam.myevents.model

/**
 * Created by macbook on 09/03/ 15.
 */
const val TASK_TYPE_FIELD = "task_type"
class EventModel(    var event_id: String? = null,
                     var dateMS: Long = 0,
                     var task_date: String? = null,
                     var task_time: String? = null,
                     var title: String? = null,
                     var desc: String? = null,
                     var isTask_is_done: Boolean = false,
                     var task_type: String? = null,
                     var task_repeat_days: String? = null,
                     var task_repeat_times: String? = null,
                     var is_need_alarm:Boolean = true,
                     var user_id: String? = null){
    fun getRepeatDaysArray():List<String>?{
        return task_repeat_days?.split(" ")
    }
}
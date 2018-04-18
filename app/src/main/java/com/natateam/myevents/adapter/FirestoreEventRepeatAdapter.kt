package com.natateam.myevents.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import android.widget.TextView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.natateam.myevents.R
import com.natateam.myevents.db.Event
import com.natateam.myevents.model.EventModel
import org.jetbrains.anko.find

/**
 * Created by macbook on 20/03/ 15.
 */
class FirestoreEventRepeatAdapter(options: FirestoreRecyclerOptions<EventModel>, val onClick:(EventModel)->Unit) : FirestoreRecyclerAdapter<EventModel, RepeatViewHolder>(options) {
    override fun onBindViewHolder(holder: RepeatViewHolder, position: Int, model: EventModel) {
        holder.setItem(model)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepeatViewHolder {
        val itemView = LayoutInflater.from(parent!!.context).inflate(R.layout.event_birthday_item, parent, false)
        return RepeatViewHolder(itemView, onClick)
    }
}


class RepeatViewHolder(val view: View, val onClick: (EventModel) -> Unit) : RecyclerView.ViewHolder(view) {
    var txtTitle: TextView? = null
    var txtType: TextView? = null
    var txtDescription: TextView? = null
    var switch: Switch?= null


    init {
        txtTitle = view.find(R.id.txt_name)
        txtType = view.find(R.id.txt_type)
        txtDescription = view.find(R.id.txt_birthday)
        switch = view.find(R.id.switch_alarm);

    }

    fun setItem(obj: EventModel) {
        with(obj) {
            txtTitle!!.text = title
            if (task_repeat_days!=null) {
                txtDescription!!.text = task_repeat_days
            }
            txtType!!.text = task_time
            view.setOnClickListener { onClick(this) }
        }
    }


}

package com.natateam.myevents.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import android.widget.TextView
import com.natateam.myevents.R
import com.natateam.myevents.db.Contact
import com.natateam.myevents.db.Event
import io.realm.OrderedRealmCollection
import org.jetbrains.anko.find

/**
 * Created by macbook on 16/03/ 15.
 */
class RepeatRecyclerViewAdapter(private val context: Context, data: OrderedRealmCollection<Event>, val onClick: (Event) -> Unit) : RealmRecyclerViewAdapter<Event, RepeatViewHolder>(context, data, true) {
    override fun onBindViewHolder(holder: RepeatViewHolder?, position: Int) {
        val obj = data!![position]
        holder!!.setItem(obj)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):RepeatViewHolder {
        val itemView = inflater.inflate(R.layout.event_birthday_item, parent, false)
        return RepeatViewHolder(itemView, onClick)
    }




}

class RepeatViewHolder(val view: View, val onClick: (Event) -> Unit) : RecyclerView.ViewHolder(view) {
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

    fun setItem(obj: Event) {
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

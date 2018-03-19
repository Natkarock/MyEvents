package com.natateam.myevents.adapter

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import android.widget.TextView
import com.natateam.myevents.Consts
import com.natateam.myevents.R
import com.natateam.myevents.TimeUtils
import com.natateam.myevents.db.Contact
import com.natateam.myevents.db.Event
import io.realm.OrderedRealmCollection
import org.jetbrains.anko.find

/**
 * Created by macbook on 06/03/ 15.
 */
class BirthdayRecyclerViewAdapter(private val context: Context, data: OrderedRealmCollection<Contact>, val onClick: (Contact) -> Unit) : RealmRecyclerViewAdapter<Contact, BirthdayViewHolder>(context, data, true) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BirthdayViewHolder {
        val itemView = inflater.inflate(R.layout.event_birthday_item, parent, false)
        return BirthdayViewHolder(itemView, onClick)
    }

    override fun onBindViewHolder(holder: BirthdayViewHolder, position: Int) {
        val obj = data!![position]
        holder.setItem(obj)
    }


}

class BirthdayViewHolder(val view: View, val onClick: (Contact) -> Unit) : RecyclerView.ViewHolder(view) {
    var txtTitle: TextView? = null
    var txtType: TextView? = null
    var txtDescription: TextView? = null
    var switch:Switch?= null


    init {
        txtTitle = view.find(R.id.txt_name)
        txtType = view.find(R.id.txt_type)
        txtDescription = view.find(R.id.txt_birthday)
        switch = view.find(R.id.switch_alarm);

    }

    fun setItem(obj: Contact) {
        with(obj) {
            txtTitle!!.text = contact_name
            if (contact_birthday!=null) {
                txtDescription!!.text = contact_birthday
            }
            txtType!!.text = contact_time
            view.setOnClickListener { onClick(this) }
        }
    }


}




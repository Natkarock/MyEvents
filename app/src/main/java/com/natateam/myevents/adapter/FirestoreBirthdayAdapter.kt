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
import com.natateam.myevents.db.Contact
import com.natateam.myevents.model.ContactModel
import org.jetbrains.anko.find

/**
 * Created by macbook on 20/03/ 15.
 */
class FirestoreBirthdayAdapter(options: FirestoreRecyclerOptions<ContactModel>,val onClick: (ContactModel) -> Unit) : FirestoreRecyclerAdapter<ContactModel, BirthdayViewHolder>(options) {
    override fun onBindViewHolder(holder: BirthdayViewHolder, position: Int, model: ContactModel) {
        holder.setItem(model)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BirthdayViewHolder {
        val itemView = LayoutInflater.from(parent!!.context).inflate(R.layout.event_birthday_item, parent, false)
        return BirthdayViewHolder(itemView, onClick)    }
}

class BirthdayViewHolder(val view: View, val onClick: (ContactModel) -> Unit) : RecyclerView.ViewHolder(view) {
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

    fun setItem(obj: ContactModel) {
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
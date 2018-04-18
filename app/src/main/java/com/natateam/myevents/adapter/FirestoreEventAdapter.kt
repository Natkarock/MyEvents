package com.natateam.myevents.adapter

import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.natateam.myevents.Consts
import com.natateam.myevents.R
import com.natateam.myevents.TimeUtils
import com.natateam.myevents.model.EventModel
import org.jetbrains.anko.find

/**
 * Created by macbook on 20/03/ 15.
 */
class FirestoreEventAdapter(options: FirestoreRecyclerOptions<EventModel>, val onClick:(EventModel)->Unit) : FirestoreRecyclerAdapter<EventModel, MyViewHolder>(options) {
    var prevEvent:EventModel? =null
    override fun onBindViewHolder(holder: MyViewHolder, position: Int, model: EventModel) {
        var isTop= true
        if (prevEvent!=null){
            if (position > 0) {
                val currentBeginOfDay = TimeUtils.getBeginOfDaInMillis(model.dateMS)
                val prevBeginOfDay = TimeUtils.getBeginOfDaInMillis(prevEvent!!.dateMS)
                if (currentBeginOfDay == prevBeginOfDay) {
                    isTop = false
                }
            }
        }
        prevEvent = model
        holder.setItem(model,isTop)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent!!.context).inflate(R.layout.event_item, parent, false)
        return MyViewHolder(itemView, onClick)
    }
}

class MyViewHolder(val view: View, val onClick: (EventModel) -> Unit) : RecyclerView.ViewHolder(view) {
    var txtTitle: TextView? = null
    var txtType: TextView? = null
    var txtDescription: TextView? = null
    var layoutTopDate: ConstraintLayout? = null
    var txtDate: TextView? = null
    var txtDateString: TextView? = null


    init {
        txtTitle = view.find(R.id.txt_name)
        txtType = view.find(R.id.txt_type)
        txtDescription = view.find(R.id.txt_birthday)
        layoutTopDate = view.find(R.id.layout_top_date)
        txtDate = view.find(R.id.txt_date)
        txtDateString = view.find(R.id.txt_date_string)
    }

    fun setItem(obj: EventModel, isTop: Boolean) {
        with(obj) {
            txtTitle!!.text = title
            txtDescription!!.text = desc
            if (isTop) {
                layoutTopDate!!.visibility = View.VISIBLE
                txtDate!!.text = TimeUtils.getFormatDateFromLong(dateMS, Consts.DATE_FORMAT)
                txtDateString!!.text = TimeUtils.getFormatDateFromLong(dateMS, Consts.DATE_STRING_FORMAT)
            } else {
                layoutTopDate!!.visibility = View.GONE
            }
            txtType!!.text = task_time
            view.setOnClickListener { onClick(this) }
        }
    }
}
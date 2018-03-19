package com.natateam.myevents.adapter

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.natateam.myevents.Consts
import com.natateam.myevents.R
import com.natateam.myevents.TimeUtils
import com.natateam.myevents.db.Event
import io.realm.OrderedRealmCollection
import org.jetbrains.anko.find

/**
 * Created by macbook on 07/07/ 15.
 */
class MyRecyclerViewAdapter(private val context: Context, data: OrderedRealmCollection<Event>, val onClick: (Event) -> Unit) : RealmRecyclerViewAdapter<Event, MyViewHolder>(context, data, true) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = inflater.inflate(R.layout.event_item, parent, false)
        return MyViewHolder(itemView, onClick)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val obj = data!![position]
        var isTop = true
        if (position > 0) {
            val prevEvent = data!![position - 1]
            val currentBeginOfDay = TimeUtils.getBeginOfDaInMillis(obj.dateMS)
            val prevBeginOfDay = TimeUtils.getBeginOfDaInMillis(prevEvent.dateMS)
            if (currentBeginOfDay == prevBeginOfDay) {
                isTop = false
            }
        }
        holder.setItem(obj, isTop)
    }


}

class MyViewHolder(val view: View, val onClick: (Event) -> Unit) : RecyclerView.ViewHolder(view) {
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

    fun setItem(obj: Event, isTop: Boolean) {
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




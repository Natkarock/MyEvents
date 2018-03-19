package com.natateam.myevents.mainlist.fragments

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.EditText
import com.natateam.myevents.Consts
import com.natateam.myevents.MainActivity
import com.natateam.myevents.R
import org.jetbrains.anko.find
import org.jetbrains.anko.toast

/**
 * Created by macbook on 09/03/ 15.
 */

open class BirthdayDialog : android.app.DialogFragment() {

    //var contact_id: Long? = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog.window!!.requestFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        return inflater.inflate(R.layout.dialog_birthday, null);
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        //contact_id = arguments.getLong(Consts.CONTACT_ID)
        val btnOk = find<Button>(R.id.btn_ok)
        val editMask = find<EditText>(R.id.edit_mask_date)
        btnOk.setOnClickListener {
            val birthday = editMask.text.toString()
            if (birthday.length.equals(getString(R.string.date_mask).length)) {
                (activity as MainActivity).saveContactBirthdayFromDialog(birthday)
                dismiss()
            } else {
                activity.toast(getString(R.string.birthday_dialog_alarm))
            }
        }
        super.onActivityCreated(savedInstanceState)
    }


    override fun onStart() {
        super.onStart()
        val window = dialog.window
        window!!.setBackgroundDrawableResource(android.R.color.transparent)
    }
}
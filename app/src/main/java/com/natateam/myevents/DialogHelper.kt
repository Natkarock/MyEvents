package com.natateam.myevents

import android.app.DialogFragment
import android.app.FragmentManager
import android.os.Bundle
import com.natateam.myevents.mainlist.fragments.BirthdayDialog


/**
 * Created by macbook on 28/09/ 15.
 */
object DialogHelper {

    fun showBirthdayDialog(fragmentManager:FragmentManager) {
        try {
            val birthdayDialog:BirthdayDialog? = BirthdayDialog()
            //val bundle = Bundle()
            //bundle.putLong(Consts.CONTACT_ID,contact_id)
            //birthdayDialog!!.setArguments(bundle)
            birthdayDialog!!.show(fragmentManager,"birthdayDialog")
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

}

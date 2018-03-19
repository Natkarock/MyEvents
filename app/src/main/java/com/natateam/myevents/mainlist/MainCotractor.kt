package com.natateam.myevents.mainlist

import com.natateam.myevents.BasePresenter
import com.natateam.myevents.BaseView
import com.natateam.myevents.Consts
import com.natateam.myevents.db.Contact
import com.natateam.myevents.db.Event

import io.realm.OrderedRealmCollection
import io.realm.RealmObject

/**
 * Created by macbook on 29/11/ 15.
 */

class MainCotractor {
    interface MainView : BaseView {
        fun loadData(list: OrderedRealmCollection<com.natateam.myevents.db.Event>)
    }


    interface MainPresenter : BasePresenter<MainView> {
        fun loadData()
    }


    interface BirthdayView : BaseView {
        fun loadData(list: OrderedRealmCollection<Contact>)
        fun showBirthdayDialog()
        fun showEventActivity(id:Long,type:String = Consts.BIRTH_TYPE)
    }


    interface BirthdayPresenter : BasePresenter<BirthdayView> {
        fun loadData()
    }

    interface AuthView:BaseView
    {
        fun checkIsSignIn()
        fun loadDataForUser()
        fun startAuthActivity()
    }

    interface AuthPresenter: BasePresenter<AuthView>{
       fun checkIsSign()
    }


}

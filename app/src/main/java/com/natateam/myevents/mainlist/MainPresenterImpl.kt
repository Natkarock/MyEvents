package com.natateam.myevents.mainlist

import com.natateam.myevents.app.EventApp
import com.natateam.myevents.db.Event
import com.natateam.myevents.db.RealmHelper

import javax.inject.Inject

import dagger.Provides
import io.realm.RealmObject

/**
 * Created by macbook on 29/11/ 15.
 */

open class MainPresenterImpl @Inject
constructor(open var view: MainCotractor.MainView, open var realmHelper: RealmHelper, open val type: String) : MainCotractor.MainPresenter {


    override fun isViewAttached() {}

    override fun loadData() {
        view.loadData(realmHelper.getEventsByType(type))
    }



}

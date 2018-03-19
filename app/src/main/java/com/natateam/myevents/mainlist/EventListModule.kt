package com.natateam.myevents.mainlist

import com.natateam.myevents.BaseView
import com.natateam.myevents.MainActivity
import com.natateam.myevents.db.Contact
import com.natateam.myevents.db.Event
import com.natateam.myevents.db.RealmHelper

import dagger.Module
import dagger.Provides

/**
 * Created by macbook on 29/11/ 15.
 */
@Module
class EventListModule(val mainView: MainCotractor.MainView, val type:String) {

    @Provides
    fun provideType():String = type

    @Provides
    fun provideMainView():MainCotractor.MainView = mainView



    @Provides
    fun provideMainPresenter(view: MainCotractor.MainView, realmHelper: RealmHelper):MainCotractor.MainPresenter{
        return MainPresenterImpl(view, realmHelper,type)
    }


}

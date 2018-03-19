package com.natateam.myevents.app

import android.app.Application
import android.content.Context
import com.natateam.myevents.FirebaseHelper

import com.natateam.myevents.alarm.AlarmHelper
import com.natateam.myevents.db.RealmHelper
import com.natateam.myevents.event.EventComponet
import com.natateam.myevents.event.EventModule
import com.natateam.myevents.mainlist.MainActivityComponent

import javax.inject.Inject
import javax.inject.Singleton

import dagger.Module
import dagger.Provides

/**
 * Created by macbook on 29/11/ 15.
 */
@Module
class AppModule (val application: Application ) {


    @Provides
    @Singleton
    fun provideApp() = application


    @Provides
    @Singleton
    fun alarmHelper() = AlarmHelper(application)

    @Provides
    @Singleton
    fun provideRealm(alarmHelper: AlarmHelper) = RealmHelper(alarmHelper);

    @Provides
    @Singleton
    fun provideFirebase() = FirebaseHelper

}

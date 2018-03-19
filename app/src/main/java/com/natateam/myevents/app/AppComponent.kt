package com.natateam.myevents.app

import com.natateam.myevents.auth.AuthComponent
import com.natateam.myevents.auth.AuthModule
import com.natateam.myevents.event.EventComponet
import com.natateam.myevents.event.EventModule
import com.natateam.myevents.mainlist.BirthdayListComponent
import com.natateam.myevents.mainlist.BirthdayModule
import com.natateam.myevents.mainlist.EventListModule
import com.natateam.myevents.mainlist.MainActivityComponent
import dagger.Component
import javax.inject.Singleton

/**
 * Created by macbook on 29/11/ 15.
 */
@Singleton
@Component(modules = arrayOf(AppModule::class))
interface AppComponent {

    fun injecTo(eventApp: EventApp)
    fun plus(eventModule:EventModule):EventComponet
    fun plus(birthdayModule: BirthdayModule):BirthdayListComponent
    fun  mainBuilder():MainActivityComponent.Builder
    fun plus(authModule: AuthModule):AuthComponent

}

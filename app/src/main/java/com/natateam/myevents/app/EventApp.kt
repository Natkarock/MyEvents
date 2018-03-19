package com.natateam.myevents.app

import android.app.Activity
import android.app.Application
import android.widget.Toast

import com.natateam.myevents.BuildConfig
import com.natateam.myevents.alarm.AlarmHelper
import com.natateam.myevents.db.RealmHelper

import javax.inject.Inject

import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import io.realm.Realm
import timber.log.Timber

/**
 * Created by macbook on 07/07/ 15.
 */
class EventApp : Application() {
    private var toast: Toast? = null
    @Inject
    lateinit var  alarmHelper: AlarmHelper
    @Inject
    lateinit var realmHelper: RealmHelper




    open val applicationComponent: AppComponent by lazy {
        DaggerAppComponent.builder()
                .appModule(AppModule(this)).build()
    }

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        app = this
        applicationComponent.injecTo(this)
        setAlarms()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    fun setAlarms() {
        alarmHelper!!.setEventAlarms(realmHelper.getAllEvents())
        alarmHelper!!.setContactAlarms(realmHelper.getAllContacts())
    }

    fun showToast(message: String) {
        if (toast != null) {
            toast!!.cancel()
        }
        val duration = Toast.LENGTH_LONG
        toast = Toast.makeText(this, message, duration)
        toast!!.show()
    }



    companion object {
        var app: EventApp? = null
            private set
    }

}

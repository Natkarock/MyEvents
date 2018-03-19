package com.natateam.myevents.event

import com.natateam.myevents.EventActivity
import com.natateam.myevents.MainActivity


import dagger.Subcomponent
import dagger.android.AndroidInjector

/**
 * Created by macbook on 30/11/ 15.
 */

@Subcomponent(modules = arrayOf(EventModule::class))
interface EventComponet {
    fun  injectTo(eventActivity: EventActivity);
}


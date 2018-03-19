package com.natateam.myevents.mainlist

import com.natateam.myevents.MainActivity
import com.natateam.myevents.mainlist.fragments.BirthdaysFragment
import com.natateam.myevents.mainlist.fragments.RepeatFragment
import com.natateam.myevents.mainlist.fragments.TodoFragment

import dagger.Component
import dagger.Subcomponent
import dagger.android.AndroidInjector

/**
 * Created by macbook on 29/11/ 15.
 */
@Subcomponent(modules = arrayOf(EventListModule::class))
interface MainActivityComponent  {
    fun injectTo(view: TodoFragment)
    fun injectTo(view: RepeatFragment)


    @Subcomponent.Builder
     interface Builder {
        fun eventListModule(eventListModule: EventListModule): Builder
        fun build(): MainActivityComponent
    }
}

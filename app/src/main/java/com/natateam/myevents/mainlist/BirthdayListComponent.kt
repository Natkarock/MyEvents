package com.natateam.myevents.mainlist

import com.natateam.myevents.mainlist.fragments.BirthdaysFragment
import dagger.Subcomponent

/**
 * Created by macbook on 13/03/ 15.
 */
@Subcomponent(modules = arrayOf(BirthdayModule::class))
interface BirthdayListComponent {
    fun injectTo(birthdaysFragment: BirthdaysFragment)

}
package com.natateam.myevents.auth

import com.natateam.myevents.MainActivity
import dagger.Subcomponent

/**
 * Created by macbook on 18/03/ 15.
 */
@Subcomponent(modules = arrayOf(AuthModule::class))
interface AuthComponent{
    fun injectTo(activity:MainActivity)
}
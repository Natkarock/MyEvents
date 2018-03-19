package com.natateam.myevents.extension

import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.natateam.myevents.app.EventApp

/**
 * Created by macbook on 13/12/ 15.
 */
val AppCompatActivity.app  :EventApp
    get() = application as EventApp


val Fragment.app :EventApp
    get() = activity?.application as EventApp



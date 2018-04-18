package com.natateam.myevents.extension

import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.firebase.ui.auth.AuthUI
import com.natateam.myevents.app.EventApp
import java.util.*

/**
 * Created by macbook on 13/12/ 15.
 */
const  val RC_SIGN_IN = 500
val AppCompatActivity.app  :EventApp
    get() = application as EventApp


val Fragment.app :EventApp
    get() = activity?.application as EventApp


fun AppCompatActivity.startAuthActivity(){
    val providers = Arrays.asList(
            AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),

            AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build())

// Create and launch sign-in intent
    startActivityForResult(
            AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setAvailableProviders(providers)
                    .build(),
            RC_SIGN_IN)    }

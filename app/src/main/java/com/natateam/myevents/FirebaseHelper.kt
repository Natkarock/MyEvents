package com.natateam.myevents

import com.google.firebase.auth.FirebaseAuth
import javax.inject.Singleton
import com.firebase.ui.auth.AuthUI
import android.support.v4.app.ActivityCompat.startActivityForResult
import java.util.*
import java.util.Arrays.asList



/**
 * Created by macbook on 18/03/ 15.
 */
@Singleton
object FirebaseHelper{
    val firebaseAuth = FirebaseAuth.getInstance()

    fun isSignIn():Boolean{
        if (firebaseAuth!=null){
            if (firebaseAuth.currentUser!=null){
               return true
            }else
                return  false

        }
        return false
    }

    fun getAllDataForUser(){
        if (firebaseAuth.currentUser!=null ){

        }
    }


}
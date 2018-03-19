package com.natateam.myevents.auth

import android.util.MonthDisplayHelper
import com.google.firebase.internal.FirebaseAppHelper
import com.natateam.myevents.FirebaseHelper
import com.natateam.myevents.mainlist.MainCotractor
import javax.inject.Inject

/**
 * Created by macbook on 18/03/ 15.
 */
class AuthPresenterImpl @Inject
constructor(val view: MainCotractor.AuthView, val firebaseHelper: FirebaseHelper):MainCotractor.AuthPresenter{

    override fun checkIsSign() {
        if (firebaseHelper!=null){
            if (!firebaseHelper.isSignIn()){
                view.startAuthActivity()
            }
        }
    }

    override fun isViewAttached() {

    }

}
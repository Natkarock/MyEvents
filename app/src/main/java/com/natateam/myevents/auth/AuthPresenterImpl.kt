package com.natateam.myevents.auth

import android.support.v7.app.AppCompatActivity
import android.util.MonthDisplayHelper
import com.firebase.ui.auth.data.model.User
import com.google.firebase.internal.FirebaseAppHelper
import com.natateam.myevents.FirebaseHelper
import com.natateam.myevents.extension.startAuthActivity
import com.natateam.myevents.mainlist.MainCotractor
import javax.inject.Inject

/**
 * Created by macbook on 18/03/ 15.
 */
class AuthPresenterImpl @Inject
constructor(val view: MainCotractor.AuthView):MainCotractor.AuthPresenter{

    override fun checkIsSign(activity:AppCompatActivity) {
            if (FirebaseHelper.isSignIn()){
                activity.startAuthActivity()
            }

    }

    override fun isViewAttached() {

    }

}
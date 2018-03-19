package com.natateam.myevents.auth

import com.natateam.myevents.FirebaseHelper
import com.natateam.myevents.mainlist.MainCotractor
import dagger.Module
import dagger.Provides
import javax.inject.Inject

/**
 * Created by macbook on 18/03/ 15.
 */
@Module
class AuthModule(val view:MainCotractor.AuthView){
    @Provides
    fun provideView():MainCotractor.AuthView = view

    @Provides
    fun providePresenter(view: MainCotractor.AuthView,firebaseHelper:FirebaseHelper):AuthPresenterImpl = AuthPresenterImpl(view,firebaseHelper)
}
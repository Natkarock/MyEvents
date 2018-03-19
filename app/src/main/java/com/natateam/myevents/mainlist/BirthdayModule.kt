package com.natateam.myevents.mainlist

import com.natateam.myevents.db.RealmHelper
import dagger.Module
import dagger.Provides

/**
 * Created by macbook on 10/03/ 15.
 */
@Module
class BirthdayModule(val birthView: MainCotractor.BirthdayView) {



    @Provides
    fun provideMainView():MainCotractor.BirthdayView= birthView



    @Provides
    fun provideBirthdayPresenter(view: MainCotractor.BirthdayView, realmHelper: RealmHelper): MainCotractor.BirthdayPresenter {
        return BirthdaysPresenter(view, realmHelper)
    }
}

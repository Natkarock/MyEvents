package com.natateam.myevents.mainlist;

import com.natateam.myevents.MainActivity;
import com.natateam.myevents.db.RealmHelper;

import dagger.Module;
import dagger.Provides;

/**
 * Created by macbook on 29/11/ 15.
 */
@Module
public class MainActivityModule {

    @Provides
    MainCotractor.MainView provideView(MainActivity mainActivity){
        return mainActivity;

    }

    @Provides
    MainCotractor.MainPresenter providePresenter(MainCotractor.MainView view, RealmHelper realmHelper){
        return new MainPresenterImpl(view, realmHelper);
    }
}

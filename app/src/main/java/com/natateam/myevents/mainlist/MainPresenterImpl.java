package com.natateam.myevents.mainlist;

import com.natateam.myevents.app.EventApp;
import com.natateam.myevents.db.RealmHelper;

import javax.inject.Inject;

import dagger.Provides;

/**
 * Created by macbook on 29/11/ 15.
 */

public class MainPresenterImpl implements MainCotractor.MainPresenter {

    MainCotractor.MainView view;
    RealmHelper realmHelper;

    @Inject
    public MainPresenterImpl(MainCotractor.MainView view, RealmHelper realmHelper) {
        this.view = view;
        this.realmHelper = realmHelper;
    }




    @Override
    public void isViewAttached() {
    }

    @Override
    public void loadData() {
        view.loadData(realmHelper.getAllEvents());
    }

    @Override
    public void isViewAtached() {

    }
}

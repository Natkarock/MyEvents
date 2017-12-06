package com.natateam.myevents.mainlist;

import com.natateam.myevents.MainActivity;

import dagger.Component;
import dagger.Subcomponent;
import dagger.android.AndroidInjector;

/**
 * Created by macbook on 29/11/ 15.
 */
@Subcomponent(modules = {MainActivityModule.class})
public interface MainActivityComponent extends AndroidInjector<MainActivity> {
    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<MainActivity>{}
}

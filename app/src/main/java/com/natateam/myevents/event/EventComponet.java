package com.natateam.myevents.event;

import com.natateam.myevents.EventActivity;
import com.natateam.myevents.MainActivity;
import com.natateam.myevents.mainlist.MainActivityModule;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

/**
 * Created by macbook on 30/11/ 15.
 */

@Subcomponent(modules = {EventModule.class})
public interface EventComponet extends AndroidInjector<EventActivity> {
    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<EventActivity>{}
}


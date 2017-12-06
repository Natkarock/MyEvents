package com.natateam.myevents.app;

import android.app.Activity;

import com.natateam.myevents.EventActivity;
import com.natateam.myevents.MainActivity;
import com.natateam.myevents.event.EventComponet;
import com.natateam.myevents.mainlist.MainActivityComponent;

import dagger.Binds;
import dagger.Module;
import dagger.android.ActivityKey;
import dagger.android.AndroidInjector;
import dagger.multibindings.IntoMap;

/**
 * Created by macbook on 29/11/ 15.
 */


@Module
public abstract class ActivityBuilder {

    @Binds
    @IntoMap
    @ActivityKey(MainActivity.class)
    abstract AndroidInjector.Factory<? extends Activity> bindMainActivity(MainActivityComponent.Builder builder);

    @Binds
    @IntoMap
    @ActivityKey(EventActivity.class)
    abstract AndroidInjector.Factory<? extends Activity> bindEventActivity(EventComponet.Builder builder);

}

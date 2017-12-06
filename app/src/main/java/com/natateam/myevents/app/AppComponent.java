package com.natateam.myevents.app;

import android.app.Application;
import android.content.Context;

import com.natateam.myevents.alarm.AlarmHelper;
import com.natateam.myevents.db.RealmHelper;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.Provides;
import dagger.android.AndroidInjectionModule;

/**
 * Created by macbook on 29/11/ 15.
 */
@Singleton
@Component(modules = {AppModule.class, AndroidInjectionModule.class,ActivityBuilder.class})
public interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);
        AppComponent build();
    }
    void inject(EventApp app);
}

package com.app.architecture.di.module;


import android.app.Application;
import android.util.Log;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/*
 * This module provide Application object throwout anywhere. Like this is use in NetModule
 */
@Module
public class AppModule {
    private final Application application;

    /*
    * @param application
    *        used to provide application object throwout app.
    * */
    public AppModule(Application application) {
        this.application = application;
    }

    /*
    * application object is used to throwout app.
    * */
    @Provides
    @Singleton
    Application providesApplication() {
        return application;
    }
}

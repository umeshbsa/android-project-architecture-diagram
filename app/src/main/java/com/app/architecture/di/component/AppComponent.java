package com.app.architecture.di.component;


import com.app.architecture.App;
import com.app.architecture.activity.FlowerActivity;
import com.app.architecture.activity.FlowerDetailActivity;
import com.app.architecture.di.module.AppModule;
import com.app.architecture.di.module.GsonModule;
import com.app.architecture.di.module.NetModule;

import javax.inject.Singleton;

import dagger.Component;


/*
 AppComponent : Insert all module here like - AppModule, GsonModule, NetModule
*/

@Singleton
@Component(modules = {AppModule.class, GsonModule.class, NetModule.class})
public interface AppComponent {

    /*
     * @param app
     *        Inject all module
     * */
    void inject(App app);

    /*
     * @param flowerActivity
     *        Inject from FlowerActivity
     * */
    void plus(FlowerActivity flowerActivity);

    /*
     * @param flowerDetailactivity
     *        Inject from FlowerDetailActivity
     * */
    void plus(FlowerDetailActivity flowerDetailActivity);
}

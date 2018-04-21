package com.app.architecture;


import android.app.Application;

import com.app.architecture.apiclient.ApiClient;
import com.app.architecture.apiclient.IApiRequest;
import com.app.architecture.di.component.AppComponent;
import com.app.architecture.di.component.DaggerAppComponent;
import com.app.architecture.di.module.AppModule;
import com.app.architecture.di.module.NetModule;
import com.app.architecture.utils.PreferenceKeeper;

/*
 * App : App class to initiate dagger2 component
 * */
public class App extends Application {

    /*
     * Used appComponent throwout all activity
     * */
    public AppComponent getAppComponent() {
        return appComponent;
    }

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        ApiClient.init(IApiRequest.class, this);
        PreferenceKeeper.setContext(this);
        /*
         * Add all module here
         * Here Gson module is not add because this module is used only in NetModule and we do not inject any activity so that it is not required to add here.
         * */
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .netModule(new NetModule("https://api.github.com/"))
                .build();
    }
}

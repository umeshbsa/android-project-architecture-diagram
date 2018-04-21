package com.app.architecture.di.module;

import android.app.Application;
import android.util.Log;

import com.app.architecture.listner.IApiService;
import com.google.gson.Gson;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/*
 * NetModule : This is used for init retrofit api service
 * */
@Module
public class NetModule {
    private final String url;

    public NetModule(String url) {
        this.url = url;
    }

    /*
     * @param application
     *        Fetch Application object from AppModule
     * */
    @Provides
    @Singleton
    Cache provideOkHttpCache(Application application) {
        int cacheSize = 10 * 1024 * 1024;
        return new Cache(application.getCacheDir(), cacheSize);
    }

    /*
     * @param cache
     *        Fetch Cache object from 'Cache provideOkHttpCache' method
     * */
    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(Cache cache) {
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newBuilder().cache(cache);
        return okHttpClient;
    }

    /*
     * @param gson
     *        Fetch Gson object from 'GsonModule' method
     *
     * @param okHttpClient
     *        Fetch OkHttpClient object from 'OkHttpClient provideOkHttpClient' method
     * */
    @Provides
    @Singleton
    Retrofit provideRetrofit(Gson gson, OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(url)
                .client(okHttpClient)
                .build();
    }

    /*
     * @param retrofit
     *        Fetch retrofit class from gradle dependency
     *        This is IApiService interface to used inject in activity class
     * */
    @Provides
    @Singleton
    IApiService provideIApiService(Retrofit retrofit) {
        IApiService service = retrofit.create(IApiService.class);
        return service;
    }

}

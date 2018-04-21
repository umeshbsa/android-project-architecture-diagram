package com.app.architecture.apiclient;

import android.content.Context;

import com.app.architecture.BuildConfig;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/*
 Set loggingInterceptor, url, gson and interceptor
 */
public class ApiClient {

    private static IApiRequest apiRequest;

    public static void init(Class<IApiRequest> requestClass, Context applicationContext) {


        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                /* .sslSocketFactory(sslContext.getSocketFactory())*/.addNetworkInterceptor(new ApiInterceptor()).addInterceptor(interceptor).build();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.HOST)
                .addConverterFactory(GsonConverterFactory.create()).client(okHttpClient)
                .build();
        apiRequest = retrofit.create(requestClass);
    }

    public static IApiRequest getRequest() {
        return apiRequest;
    }
}

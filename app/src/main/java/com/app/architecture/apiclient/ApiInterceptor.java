package com.app.architecture.apiclient;

import android.text.TextUtils;

import com.app.architecture.BuildConfig;
import com.app.architecture.utils.PreferenceKeeper;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Class is used to passing user token at central level.
 */
public class ApiInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        String token = PreferenceKeeper.getInstance().getAccessToken();
        Request originalRequest = chain.request();
        if (token == null) token = "";
        Request.Builder builder = originalRequest.newBuilder();
        Request oldReq = builder
                .addHeader("rid", token)
                .addHeader("did", token)
                .addHeader("ov", BuildConfig.VERSION_NAME)
                .addHeader("dt", "2").build();
        if (TextUtils.isEmpty(token)) {
            return chain.proceed(oldReq);
        }
        oldReq = builder
                .addHeader("authorization", token)
                .build();
        return chain.proceed(oldReq);
    }
}

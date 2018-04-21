package com.app.architecture.apiclient;

import com.app.architecture.model.base.BaseResponse;
import com.app.architecture.model.base.Errors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/*
  Api callback
 */
public abstract class ApiCallback<T> implements Callback<BaseResponse<T>> {

    @Override
    public void onResponse(Call<BaseResponse<T>> call, Response<BaseResponse<T>> response) {

        if (response.body() != null) {
            if (((BaseResponse) response.body()).getStatus() == 1) {
                onSucess(response.body().getResult());
            } else {
                if (response.body().getError() != null) {
                    Errors error = response.body().getError();
                    if (error != null) {
                        onError(error);
                    }
                }
            }
        } else {
            if (response == null || response.code() == 401) {
                Errors e = new Errors();
                e.setMsg("Session expired");
                onError(e);
            }
        }
    }

    @Override
    public void onFailure(Call<BaseResponse<T>> call, Throwable t) {
        Errors error = new Errors();
        if (t != null && t.getMessage() != null) {
            error.setMsg(t.getMessage());
        }
        onError(error);
    }


    public abstract void onSucess(T t);

    public abstract void onError(Errors er);

}

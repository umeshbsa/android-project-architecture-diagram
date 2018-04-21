package com.app.architecture.apiclient;

import com.app.architecture.model.User;
import com.app.architecture.model.base.BaseResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/*
  All api code Post, Get, Put, Delete
 */

public interface IApiRequest {

    @FormUrlEncoded
    @POST("quax/payer/api/v1/login")
    Call<BaseResponse<User>> loginAPI(
            @Field(FIELD.EMAIL) String email);

    interface FIELD {
        String EMAIL = "email";
    }
}
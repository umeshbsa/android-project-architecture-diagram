package com.app.architecture.listner;



import com.app.architecture.model.Flower;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface IApiService {


    /*
    * To get all flowers by this api
    */
    @GET(" http://services.hanselandpetal.com/feeds/flowers.json")
    Call<List<Flower>> getFlowersAPI();
}

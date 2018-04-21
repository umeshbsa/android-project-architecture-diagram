## Android Project Architecture.
This is example of android project architecture.

## Project Setup
  * Project name -
  * Package name - com.app....
  * Android Design Pattern - MVC
  * Package Init - Package is init by layer
  * Dependency Lib
   &emsp;Dagger2 for DI........<br/>
   &emsp;Glide for image loading<br/>
   &emsp;CardView - elevation for view<br/>
   &emsp;RecycleView - Show data in list/grid<br/>
   &emsp;Retrofit - Rest network api call<br/>
   &emsp;OkHttp3 - Used for rest api and it is used to retrofit, interceptor<br/>
   &emsp;PubNub - Chatting for text - https://www.pubnub.com/

## Package Setup - Layer rule<br/>
   Package name - include classes, interface<br/>
   *activity - BaseActivity, LoginActivity, HomeActivity<br/>
   *adapter - FlavorAdapter, FlowerAdapter<br/>
   *apiclient - ApiCallback, ApiClient, ApiInterceptor, IApiRequest<br/>
   *di - Create component and module for dagger2

## Naming Convention<br/>
   *activity - LoginActivity.java, activity_login.xml<br/>
   *adapter - FlavorAdapter.java, adapter_flavor.xml<br/>
   *fragment - LoginFragment.java - fragment_login.xml<br/>
   *view - CustomRatingBar - inflater_custom_rating_bar, include_header_toolbar<br/>
   *color - color_ff00ff<br/>
   *dimens - dp_10, dp_12, sp_12<br/>
   *menu - menu_home, menu_flower<br/>
   *drawable - bg_rect_white, bg_circle_blue, selector_check_box<br/>
   *drawable-hdpi - ic_login_user_logo, ic_login_user_camera, ic_notification_back

## Module Define
  * Login
  * Feed
  * Notification
  * Settings

## Project Architecture
1.  ##ApiClient<br/>
    &emsp;Used retrofit with and without dagger2<br/>
    &emsp;Without dagger2<br/>

    &emsp;ApiCallback - Get all pai call and parse response in POJO format and send success or error from call api<br/>
    &emsp;ApiClient - setup all api client with url, interceptor, gson and ssl<br/>
    &emsp;ApiInterceptor - Control header, app version name and code, and validate by access token<br/>
    &emsp;IApiRequest - This is interface. Create all method to api call<br/>
```java
    Class ApiCallback
    public abstract class ApiCallback<T> implements Callback<BaseResponse<T>> {... and so on
    Class ApiClient
    Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BuildConfig.HOST)
                    .addConverterFactory(GsonConverterFactory.create()).client(okHttpClient)
                    .build();
            apiRequest = retrofit.create(requestClass);
    Interface IApiRequest
    public interface IApiRequest {
        @FormUrlEncoded
        @POST("api/v1/login")
        Call<BaseResponse<User>> loginAPI(
                @Field(FIELD.EMAIL) String email);
    }
```
    With dagger2
    Class NetModule - Create dagger2 module for network
    NetModule module:
```java
    @Provides
    @Singleton
    Retrofit provideRetrofit(Gson gson, OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(url)
                .client(okHttpClient)
                .build();
    }

    @Provides
    @Singleton
    IApiService provideIApiService(Retrofit retrofit) {
        IApiService service = retrofit.create(IApiService.class);
        return service;
    }
}
```
2. ##Factory Pattern<br/>
   Used this pattern to manage fragment
```java
   public static FragmentFactory newInstance() {
        if (INSTANCE == null) {
            INSTANCE = new FragmentFactory();
        }
        return INSTANCE;
    }
     public BaseFragment getFragment(String TAG) {
        switch (TAG) {
            case "homeFragmentTag":
                baseFragment = new HomeFragment();
                break;

            case "loginFragmentTag":
                baseFragment = new LoginFragment();
                break;
        }
        return baseFragment;
    }
```
Used this code in any activity/fragment to get fragment instance by factory pattern
```java
   // Here this is fragment instance by tag as you pass in getFragment method
   fragment = FragmentFactory.newInstance().getFragment(FragmentFactory.HOME_FRAGMENT_TAG);
```
3. ##Database Design<br/>
     &emsp;*Used Room architecture<br/>
     &emsp;*Used Content Provider
     &emsp;*Create Db Schema.<br/>
     <img src="http://i.imgur.com/4aRRnAe.gif" width="350" />

4. ##

# Licence

    Copyright 2018 Umesh Kumar

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
          

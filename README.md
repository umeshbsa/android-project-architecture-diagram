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
Network api architecture<br/>
<img src="http://i.imgur.com/4aRRnAe.gif" width="350" />

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
Fragment Factory Pattern architecture<br/>
<img src="http://i.imgur.com/4aRRnAe.gif" width="350" />

3. ##Database Design<br/>
     &emsp;*Used Room architecture<br/>
     Room Architecture<br/>
     <img src="http://i.imgur.com/4aRRnAe.gif" width="350" />
     &emsp;*Used Content Provider<br/>
     ContentProvider Architecture<br/>
     <img src="http://i.imgur.com/4aRRnAe.gif" width="350" />
     &emsp;*Create Db Schema.<br/>
     <img src="http://i.imgur.com/4aRRnAe.gif" width="350" />

4. ##Chat<br/>
     &emsp;*Chat flow with model, view controller, view(ui) and network api<br/>
      <img src="http://i.imgur.com/4aRRnAe.gif" width="350" />

5. ##Activity State Machine<br/>
   <img src="http://i.imgur.com/4aRRnAe.gif" width="350" />

6. ##Comment Data flow<br/>
   Update item from screen2 to screen1<br/>
   Used Observer Pattern<br/>
   <img src="http://i.imgur.com/4aRRnAe.gif" width="350" />

7. ##Abstraction login<br/>
   Call method from adapter<br/>
```java
   public abstract class BaseActivity{
     public abstract void shareData(Object... data);
   }
   Now in Adapter
   activity.shareData(data);
   Now in HomeActivity
   @Override
   public void shareData(Object... a){
      // find data
   }
```

8. ##Android gradle dependency with version detail
```java
   project.ext {
       supportAndroidVersion = "27.1.1"
       daggerVersion = "2.13"
       retrofitVersion = "2.0.2"
       okhttpVersion = "3.2.0"
   }
   and used here in app gradle dependencies...
   implementation "com.android.support:appcompat-v7:$project.supportAndroidVersion"
```

9. ##Upload Image<br/>
     Do not uload with user data and image at same time.<br/>
     Use two api first upload user data and then upload image but do not show progress bar.<br/>
     If you want ot best practice show notification bar when upload image

10. ##Use ProgressBar in BaseActivity
```java
   public void showProgressBar() {
        if (BaseActivity.this != null && !BaseActivity.this.isFinishing()) {
            if (progressDialog == null) {
                progressDialog = ProgressDialog.show(BaseActivity.this, "", "", true);
            }
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setContentView(R.layout.progress_layout);
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        }
    }

    public void hideProgressBar() {
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }
```

11. ##Use Header as Toolbar in BaseActivity
```java
   public void setToolbarHeader(String title) {
        setToolbarHeader(title, true);
   }

   public void setToolbarHeader(String title, boolean isBack) {
        mTopToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mTopToolbar != null) {
            setSupportActionBar(mTopToolbar);
            getSupportActionBar().setDisplayShowHomeEnabled(isBack);
            getSupportActionBar().setDisplayHomeAsUpEnabled(isBack);
            if (isBack)
                mTopToolbar.setNavigationIcon(R.drawable.back_forgot);
            getSupportActionBar().setTitle(title);

            mTopToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }
   }
 ```

 12. ##Use resume upload and resume download file.<br/>
     Start download/upload file, if network has gone and<br/>after some time network is on then do not start initial download/upload.<br/>
     To save downloaded/uploaded end file length during upload process so that start from this length when network is on.

13 ##Use Memory Management
   Ignore enum.
   Use Sparse Array
   Use Vector drawable
   Check Android Profile
   Do not used abstraction if not needed.
   Use job scheduler for background process

14. ##Api call on Splash Screen
    Get all maximum data in splash  and splash depend on api data response but if response if greater than 10 second then go to home screen and show


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
          

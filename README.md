### Android Project Architecture.
This is example of android project architecture.
- [Project Setup](#project-setup)
- [Package Setup - Layer](#package-setup---layer)
- [Naming Convention](#naming-convention)
- [Module Define](#module-define)
- [Project Architecture](#project-architecture)
  - [Network Api](#network-api)
  - [Factory Pattern for fragment](#factory-pattern-for-fragment)
  - [Get activity from onAttach in fragment](#get-activity-from-onattach-in-fragment)
  - [Database Design](#database-desig)
  - [Chat Flow](#chat-flow)
  - [Activity State Machine](#activity-state-machine)
  - [Comment Data Flow with Observable pattern](#comment-data-flow-with-observable-pattern)
  - [Abstraction Logic](#abstraction-logic)
  - [Android gradle dependency with version details](#android-gradle-dependency-with-version-detail)
  - [Upload Image](#upload-image)
  - [Use Progressbar in BaseActivity](#use-progressbar-in-baseactivity)
  - [Use Header as Toolbar in BaseActivity](#use-header-as-toolbar-in-baseactivity)
  - [Use resume upload and resume download file](#use-resume-upload-and-resume-download-file)
  - [Memory Management](#use-memory-management)
  - [Api call on Splash Screen](#api-call-on-splash-screen)
- [License](#license)

### Project Setup
  * Project name - Android Project Architecture
  * Package name - com.app....
  * Android Design Pattern - MVC
  * Package Init - Package is init by layer
  * Dependency Lib Dagger2
  * Glide for image loading
  * CardView - elevation for view
  * RecycleView - Show data in list/grid
  * Retrofit - Rest network api call
  * OkHttp3 - Used for rest api and it is used to retrofit, interceptor
  * PubNub - Chatting for text - https://www.pubnub.com/

### Package Setup - Layer
   * activity - BaseActivity, LoginActivity, HomeActivity
   * adapter - FlavorAdapter, FlowerAdapter
   * apiclient - ApiCallback, ApiClient, ApiInterceptor, IApiRequest
   * di - Create component and module for dagger2

### Naming Convention
   * activity - LoginActivity.java, activity_login.xml
   * adapter - FlavorAdapter.java, adapter_flavor.xml
   * fragment - LoginFragment.java - fragment_login.xml
   * view - CustomRatingBar - inflater_custom_rating_bar, include_header_toolbar
   * color - color_ff00ff
   * dimens - dp_10, dp_12, sp_12
   * menu - menu_home, menu_flower
   * drawable - bg_rect_white, bg_circle_blue, selector_check_box
   * drawable-hdpi - ic_login_user_logo, ic_login_user_camera, ic_notification_back

### Module Define
  * Login
  * Feed
  * Notification
  * Settings

### Project Architecture
#### Network Api

    Without dagger2
    ApiCallback - Get all pai call and parse response in POJO format and send success or error from call api
    ApiClient - setup all api client with url, interceptor, gson and ssl
    ApiInterceptor - Control header, app version name and code, and validate by access token
    IApiRequest - This is interface. Create all method to api call
```java
    //Class ApiCallback
    public abstract class ApiCallback<T> implements Callback<BaseResponse<T>> {... and so on
    //Class ApiClient
    Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BuildConfig.HOST)
                    .addConverterFactory(GsonConverterFactory.create()).client(okHttpClient)
                    .build();
            apiRequest = retrofit.create(requestClass);
    //Interface IApiRequest
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
Network api architecture without dagger2<br/>
<img src="https://github.com/umeshbsa/android-project-architecture-diagram/blob/master/screen/network_diagram.png"/>

#### Factory Pattern for fragment

   * Used this pattern to manage fragment instance
```java
   public static FragmentFactory newInstance() {
        if (INSTANCE == null) {
            INSTANCE = new FragmentFactory();
        }
        return INSTANCE;
    }
```
   * Use fragment instance by fragment tag
```java
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
  * Used this code in any activity/fragment to get fragment instance by factory pattern
```java
   fragment = FragmentFactory.newInstance().getFragment(FragmentFactory.HOME_FRAGMENT_TAG);
```
  * Fragment Factory Pattern architecture<br/>
    <img src="https://github.com/umeshbsa/android-project-architecture-diagram/blob/master/screen/fragment_factory.png"  />

#### Get activity from onAttach in fragment
 
   * BaseFragment class
```java
    protected BaseActivity activity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (BaseActivity) context;
    }
```
  * To get activity instance in fragment
    * This is HomeFragment class
```java
  private void getUserData() {
        activity.hideProgressBar();
    }
```
#### Database Design

   * Used Room Architecture
     * Entity
     * Dao
     * Room database
   * Used Content Provider
     * MyContentProvider
     * MyDBOpenHelper
     * MyContract
     * MyDBManager
  * Create Db Schema.
    <img src="https://github.com/umeshbsa/android-project-architecture-diagram/blob/master/screen/er.png"  />

#### Chat Flow

   * Chat flow with model, view controller, view(ui) and network api
     <img src="https://github.com/umeshbsa/android-project-architecture-diagram/blob/master/screen/chat_flow.png"/><br/>
   * Chat flow ui.<br/>
     <img src="https://github.com/umeshbsa/android-project-architecture-diagram/blob/master/screen/chat_flow_ui.png"/>

#### Activity State Machine

   <img src="https://github.com/umeshbsa/android-project-architecture-diagram/blob/master/screen/screen_activity_state_machine.png"  />

#### Comment Data flow with Observable Pattern
 
  * Update item from screen2 to screen1
  * Used Observer Pattern diagram
    <img src="https://github.com/umeshbsa/android-project-architecture-diagram/blob/master/screen/comment_count_flow.png"  />

#### Abstraction Logic

   * Call method from adapter
```java
   public abstract class BaseActivity{
     public abstract void shareData(Object... data);
   }
```   
  * Now in Adapter
```java
   activity.shareData(data);
```   
   
   * Now in HomeActivity
```java
   @Override
   public void shareData(Object... a){
      // find data and update ui
   }
```  
#### Android gradle dependency with version detail
```java
   project.ext {
       supportAndroidVersion = "27.1.1"
       daggerVersion = "2.13"
       retrofitVersion = "2.0.2"
       okhttpVersion = "3.2.0"
   }
```   
   *Used in app gradle dependencies
```java
   implementation "com.android.support:appcompat-v7:$project.supportAndroidVersion"
   
   annotationProcessor "com.google.dagger:dagger-compiler:$project.daggerVersion"
   implementation "com.google.dagger:dagger:$project.daggerVersion"

```
#### Upload Image

   * Do not upload user data and image at same time.
   * Use two api first upload user data and then upload image but do not show progress bar.
   * If you want to best practice show notification bar when upload image.

#### Use ProgressBar in BaseActivity
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
#### Use Header as Toolbar in BaseActivity
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
#### Use resume upload and resume download file

   * Start download/upload file, if network has gone and<br/>after some time network is on then do not start initial download/upload.<br/>
   * To save downloaded/uploaded end file length during upload process so that start from this length when network is on.

#### Use Memory Management

   * Ignore enum.
   * Use Sparse Array
   * Use Vector drawable
   * Check Android Profile
   * Do not used abstraction if not needed.
   * Use job scheduler for background process

#### Api call on Splash Screen
    
   * Get all maximum data in splash  and splash depend on api data response but if response if greater than 10 second then go to home screen and show


#### Licence

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
          

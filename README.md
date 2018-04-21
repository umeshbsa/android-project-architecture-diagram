## Android Project Architecture.
This is example of android project architecture.

## Project Setup
  * Project name -
  * Package name - com.app....
  * Android Design Pattern - MVC
  * Package Init - Package is init by layer
  * Dependency Lib
```html
   Dagger2 for DI.
   Glide for image loading
   CardView - elevation for view
   RecycleView - Show data in list/grid
   Retrofit - Rest network api call
   OkHttp3 - Used for rest api and it is used to retrofit, interceptor
   PubNub - Chatting for text - https://www.pubnub.com/
```
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
    &emsp;With dagger2 -
    &emsp;Class NetModule - Create dagger2 module for network
```java
    NetModule -
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
```
## Factory Pattern<br/>
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
To used fragment factory pattern
```java
 fragment = FragmentFactory.newInstance().getFragment(FragmentFactory.HOME_FRAGMENT_TAG);
```
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
          

package com.app.architecture.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Class is used to save user data in preference.
 */
public class PreferenceKeeper {

    private static PreferenceKeeper keeper;
    private static Context context;
    private SharedPreferences prefs;

    private PreferenceKeeper(Context context) {
        if (context != null)
            prefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static PreferenceKeeper getInstance() {
        if (keeper == null) {
            keeper = new PreferenceKeeper(context);
        }
        return keeper;
    }

    public static void setContext(Context context1) {
        context = context1;
    }


    public String getAccessToken() {
        return prefs.getString("token", "");
    }

    public void setAccessToken(String accessToken) {
        prefs.edit().putString("token", accessToken).commit();
    }

}

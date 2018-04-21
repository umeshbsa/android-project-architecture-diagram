package com.app.architecture.db;

import android.content.ContentValues;

import com.app.architecture.activity.BaseActivity;
import com.app.architecture.model.Flavor;

public class FlavorDbManager {


    public static void insertFlavorData(final BaseActivity activity, Flavor[] flavors, final android.os.Handler handler) {
        final ContentValues[] flavorValuesArr = new ContentValues[flavors.length];
        // Loop through static array of Flavors, add each to an instance of ContentValues
        // in the array of ContentValues
        for (int i = 0; i < flavors.length; i++) {
            flavorValuesArr[i] = new ContentValues();
            flavorValuesArr[i].put(FlavorsContract.FlavorEntry.COLUMN_ICON, flavors[i].image);
            flavorValuesArr[i].put(FlavorsContract.FlavorEntry.COLUMN_VERSION_NAME,
                    flavors[i].name);
            flavorValuesArr[i].put(FlavorsContract.FlavorEntry.COLUMN_DESCRIPTION,
                    flavors[i].description);
        }


        new Thread(new Runnable() {
            @Override
            public void run() {
                int i = activity.getContentResolver().bulkInsert(FlavorsContract.FlavorEntry.CONTENT_URI,
                        flavorValuesArr);
                handler.sendEmptyMessage(i);
            }
        }).start();
        // bulkInsert our ContentValues array

    }
}

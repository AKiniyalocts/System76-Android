package com.akiniyalocts.system76.base;

import android.app.Application;
import android.content.Context;

import com.akiniyalocts.system76.R;

/**
 * Created by anthony on 8/23/15.
 */
public class BaseApplication extends Application {

    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    private static Context getContext(){
        return context;
    }

    public static boolean isTablet(){
        return getContext().getResources().getBoolean(R.bool.isTablet);
    }

    public static boolean isLandscape(){
        return getContext().getResources().getBoolean(R.bool.isLandscape);
    }
}

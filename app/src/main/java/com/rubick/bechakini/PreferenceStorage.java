package com.rubick.bechakini;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Mehedee Zaman on 1/21/2015.
 */
public class PreferenceStorage {

    public static Context appContext;

    public static void init(Context context){
        appContext = context;
    }


    public static boolean isFirstLaunch(){
        return !(PreferenceManager.getDefaultSharedPreferences(appContext).contains("notFirstLaunch"));
    }

    public static boolean setFirstLaunch(){

        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(appContext).edit();
        editor.putBoolean("notFirstLaunch", true);

        return editor.commit();
    }
}

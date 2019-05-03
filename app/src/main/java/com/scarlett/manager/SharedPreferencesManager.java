package com.scarlett.manager;

import android.content.Context;
import android.content.SharedPreferences;
import com.scarlett.core.ScarletApplication;

/**
 * Created by rac on 2/1/19.
 */

public class SharedPreferencesManager {
    private static String SHARED_PREF_LUMINA = "SHARED_PREF_LUMINA";
    private static SharedPreferencesManager sharedPreferencesManager;

    public SharedPreferencesManager() {

    }

    private static SharedPreferences getSharedPreferences() {
        return ScarletApplication.getContext().getSharedPreferences(SHARED_PREF_LUMINA, Context.MODE_PRIVATE);
    }

    public static SharedPreferencesManager getInstance() {
        if (sharedPreferencesManager == null)
            sharedPreferencesManager = new SharedPreferencesManager();
        return sharedPreferencesManager;
    }

    // shared preferences method to store string value
    public static void store(String key, String value) {
        SharedPreferences sharedPreferences = getSharedPreferences();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    // shared preferences method to store boolean value
    public static void storeBoolean(String key,boolean value){
        SharedPreferences sharedPreferences = getSharedPreferences();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key,value);
        editor.commit();
    }

    public static String get(String key, String defaultValue) {
        SharedPreferences sharedPreferences = getSharedPreferences();
        String value = sharedPreferences.getString(key, defaultValue);
        return value;
    }
    public static int getInt(String key) {
        SharedPreferences sharedPreferences = getSharedPreferences();
        int value = sharedPreferences.getInt(key, 0);
        return value;
    }
    public static void storeInt(String key, int value) {
        SharedPreferences sharedPreferences = getSharedPreferences();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }


    public static boolean getBoolean(String key , boolean value){

        SharedPreferences sharedPreferences = getSharedPreferences();
         boolean result = sharedPreferences.getBoolean(key,value);
         return result;
    }
    public static void delete(String key) {
        SharedPreferences sharedPreferences = getSharedPreferences();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(key);
        editor.apply();
    }
}
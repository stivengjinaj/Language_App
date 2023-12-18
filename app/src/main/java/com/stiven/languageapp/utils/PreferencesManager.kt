package com.stiven.languageapp.utils

import android.content.Context
import android.content.SharedPreferences

/**
 * Class that handles shared preferences.
 *
 * @param context application context.
 * */
class PreferencesManager(context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
    /**
     * Function used to save a boolean valued preference.
     *
     * @param key preference's (primary) key.
     * @param value preference's value: true/false.
     * */
    fun saveBoolean(key: String, value: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }
    /**
     * Function used to save a string valued preference.
     *
     * @param key preference's (primary) key.
     * @param value preference's value: true/false.
     * */
    fun saveString(key: String, value: String){
        val editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }
    /**
     * Function used to get a boolean preference.
     *
     * @param key preference's (primary) key.
     * @param defaultValue expected value of the preference.
     * @return true if defaultValue matches the preference actual value, false otherwise.
     * */
    fun getBooleanData(key: String, defaultValue: Boolean): Boolean {
        return sharedPreferences.getBoolean(key, defaultValue)
    }
    /**
     * Function used to get a string preference.
     *
     * @param key preference's (primary) key.
     * @param defaultValue value to return if this preference does not exist.
     * @return a string with preference's actual value or null if it doesn't exist.
     * */
    fun getStringData(key: String, defaultValue: String): String? {
        return sharedPreferences.getString(key, null)
    }
}
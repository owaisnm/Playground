package com.owais.playground

import android.content.Context
import android.content.SharedPreferences

class SharedPreference(context: Context) {
    companion object {
        const val REFRESH_TIME_MIN = "max.refresh.time"
    }

    private val PREFS_NAME = Constants.PLAYGROUND_PREFERENCES
    val sharedPref: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun save(KEY_NAME: String, text: String) {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putString(KEY_NAME, text)
        editor!!.commit()
    }

    fun save(KEY_NAME: String, value: Long) {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putLong(KEY_NAME, value)
        editor.commit()
    }

    fun save(KEY_NAME: String, status: Boolean) {

        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putBoolean(KEY_NAME, status!!)
        editor.commit()
    }

    fun getValueString(KEY_NAME: String): String? {
        return sharedPref.getString(KEY_NAME, null)
    }

    fun getValueLong(KEY_NAME: String): Long {
        return sharedPref.getLong(KEY_NAME, 0)
    }

    fun getValueBoolien(KEY_NAME: String, defaultValue: Boolean): Boolean {
        return sharedPref.getBoolean(KEY_NAME, defaultValue)
    }

    fun clearSharedPreference() {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        //sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        editor.clear()
        editor.commit()
    }

    fun removeValue(KEY_NAME: String) {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.remove(KEY_NAME)
        editor.commit()
    }
}
package com.pklein.scrambledpicture.utils

import android.content.Context
import android.content.SharedPreferences

const val SHARED_PREFERENCES_FILE = "scrambledPicture-shared-preferences"
const val SHARED_PREFERENCES_DATA_SAVED = "isDataSaved"

interface IPersistentStorage {
    fun storeSharedPref(context: Context, key: String, value: Any): Boolean
    fun getSharedPrefString(context: Context, key: String): String?
    fun getSharedPrefBoolean(context: Context, key: String): Boolean
    fun remove(context: Context, key: String)
}

object AccessTokenUtils : IPersistentStorage {
    override fun storeSharedPref(context: Context, key: String, value: Any): Boolean {
        val editor = sharedPreferences(context).edit()
        when (value) {
            is Boolean -> editor.putBoolean(key, value)
            is String -> editor.putString(key, value)
            else -> return false
        }
        editor.apply()
        return true
    }

    override fun getSharedPrefString(context: Context, key: String): String? {
        return sharedPreferences(context).getString(key, null)
    }

    override fun getSharedPrefBoolean(context: Context, key: String): Boolean {
        return sharedPreferences(context).getBoolean(key, false)
    }

    override fun remove(context: Context, key: String) {
        context
            .getSharedPreferences(SHARED_PREFERENCES_FILE, Context.MODE_PRIVATE)
            .edit()
            .remove(key)
            .apply()
    }

    private fun sharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(SHARED_PREFERENCES_FILE, Context.MODE_PRIVATE)
    }

}
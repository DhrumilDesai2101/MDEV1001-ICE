package com.example.ices10b
import android.content.Context
import android.content.SharedPreferences
import com.example.ices10b.R
object SessionManager {
    const val IS_LOGIN = "isLogin"

    const val USER_TOKEN = "user_token"

    /**
     * Function to save auth token
     */
    fun saveAuthToken(context: Context, token: String) {
        saveString(context, USER_TOKEN, token)
    }

    /**
     * Function to fetch auth token
     */
    fun getToken(context: Context): String? {
        return getString(context, USER_TOKEN)
    }

    fun saveString(context: Context, key: String, value: String) {
        val prefs: SharedPreferences =
            context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun getString(context: Context, key: String): String? {
        val prefs: SharedPreferences =
            context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)
        return prefs.getString(key, null)
    }

    fun saveBool(context: Context, key: String, value: Boolean) {
        val prefs: SharedPreferences =
            context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putBoolean(key, value)
        editor.apply()

    }
    fun getBool(context: Context, key: String): Boolean {
        val prefs: SharedPreferences =
            context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)
        return prefs.getBoolean(key, false)
    }
    fun clearData(context: Context) {
        val editor =
            context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)
                .edit()
        editor.clear()
        editor.apply()
    }
}
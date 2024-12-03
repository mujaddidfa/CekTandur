package com.dicoding.cektandur.utils

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    companion object {
        private const val PREFS_NAME = "user_session"
        private const val USER_NAME = "user_name"
        private const val USER_ID = "user_id"
    }

    fun saveUserName(userName: String) {
        val editor = prefs.edit()
        editor.putString(USER_NAME, userName)
        editor.apply()
    }

    fun getUserName(): String? {
        return prefs.getString(USER_NAME, null)
    }

    fun clearSession() {
        val editor = prefs.edit()
        editor.clear()
        editor.apply()
    }
}
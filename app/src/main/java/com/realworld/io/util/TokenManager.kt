package com.realworld.io.util

import android.content.Context
import com.realworld.io.util.Constants.PREFES_TOKEN_FILE
import com.realworld.io.util.Constants.USER_NAME
import com.realworld.io.util.Constants.USER_TOKEN
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

/**
 * Global SharedPreferences And Token manager
 */
class TokenManager @Inject constructor(@ApplicationContext context: Context) {

    private var prefs = context.getSharedPreferences(PREFES_TOKEN_FILE,Context.MODE_PRIVATE)

    /**
     * Save token
     */
    fun saveToken(token :String,username : String){
        val editor = prefs.edit()
        editor.putString(USER_TOKEN,token)
        editor.putString(USER_NAME,username)
        editor.apply()
    }

    /**
     * Get token
     */
    fun getToken() : String? {
        return prefs.getString(USER_TOKEN , null)
    }

    /**
     * get user name
     */
    fun getName() :String? {
        return prefs.getString(USER_NAME , null)
    }

    /**
     * Logouts
     */
    fun logout(){
        val editor = prefs.edit()
        editor.clear().apply()
    }
}
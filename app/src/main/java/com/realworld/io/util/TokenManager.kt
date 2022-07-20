package com.realworld.io.util

import android.content.Context
import com.realworld.io.util.Constants.PREFES_TOKEN_FILE
import com.realworld.io.util.Constants.USER_TOKEN
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class TokenManager @Inject constructor(@ApplicationContext context: Context) {

    private var prefs = context.getSharedPreferences(PREFES_TOKEN_FILE,Context.MODE_PRIVATE)

    fun saveToken(token :String){
        val editor = prefs.edit()
        editor.putString(USER_TOKEN,token)
        editor.apply()
    }

    fun getToken() : String? {
        return prefs.getString(USER_TOKEN , null)
    }

    fun logout(){
        val editor = prefs.edit()
        editor.clear().apply()
    }
}
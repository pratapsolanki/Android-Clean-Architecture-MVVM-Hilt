package com.realworld.io.util

import android.content.Context
import com.realworld.io.util.Constants.USER_TOKEN
import okhttp3.Interceptor
import okhttp3.Response

/**
 * AuthInterceptor for managing header auth Token
 */
class AuthInterceptor (context: Context) : Interceptor {
    private val sessionManager = TokenManager(context)

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()

        // If token has been saved, add it to the request
        sessionManager.getToken()?.let {
            requestBuilder.addHeader(USER_TOKEN, it)
        }

        return chain.proceed(requestBuilder.build())
    }
}

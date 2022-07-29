package com.realworld.io.data.di

import android.content.Context
import com.realworld.io.data.remote.ApiService
import com.realworld.io.util.ApiConstants.BASE_URL
import com.realworld.io.util.AuthInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


/**
 * Providing Remote dependency for DI
 */
@InstallIn(SingletonComponent::class)
@Module
class RemoteModule {

    /**
     * Providing retrofit dependency for DI
     */
    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    /**
     * Providing retrofit instance
     */
    @Singleton
    @Provides
    fun provideApiClient(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    /**
     * Providing HTTP dependency for DI
     */
    @Singleton
    @Provides
    fun okhttpClient(context: Context): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(context))
            .build()
    }

}
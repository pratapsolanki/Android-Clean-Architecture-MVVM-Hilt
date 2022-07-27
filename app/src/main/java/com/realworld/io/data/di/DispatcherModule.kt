package com.realworld.io.data.di

import com.realworld.io.data.dispatcher.DispatcherProviders
import com.realworld.io.data.repo.DispatcherImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DispatcherModule {

    @Singleton
    @Provides
    fun getDispatcher() : DispatcherProviders {
        return DispatcherImpl()
    }

}
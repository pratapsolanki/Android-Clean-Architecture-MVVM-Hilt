package com.realworld.io.data.di

import android.app.Application
import com.realworld.io.data.local.AppDao
import com.realworld.io.data.db.AppDatabase
import com.realworld.io.data.dispatcher.DispatcherProviders
import com.realworld.io.data.repo.DispatcherImpl
import com.realworld.io.domain.use_cases.ValidateEmail
import com.realworld.io.domain.use_cases.ValidatePassword
import com.realworld.io.domain.use_cases.ValidateUsername
import com.realworld.io.domain.use_cases.ValidationResult
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Singleton
    @Provides
    fun getAppDB(context: Application): AppDatabase {
        return AppDatabase.getAppDb(context) as AppDatabase
    }

    @Singleton
    @Provides
    fun getDao(appDB: AppDatabase): AppDao {
        return appDB.getDAO()
    }



}
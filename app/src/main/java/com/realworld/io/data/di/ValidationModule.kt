package com.realworld.io.data.di

import com.realworld.io.domain.use_cases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ValidationModule {
    @Singleton
    @Provides
    fun getEmail() : ValidateEmail {
        return ValidateEmail()
    }

    @Singleton
    @Provides
    fun getPassword() : ValidatePassword {
        return ValidatePassword()
    }

    @Singleton
    @Provides
    fun getUsername() : ValidateUsername {
        return ValidateUsername()
    }


    @Singleton
    @Provides
    fun getTitle() : ValidateTitle {
        return ValidateTitle()
    }

    @Singleton
    @Provides
    fun getShortDesc() : ValidateShortDesc {
        return ValidateShortDesc()
    }

    @Singleton
    @Provides
    fun getDesc() : ValidateDesc {
        return ValidateDesc()
    }

}
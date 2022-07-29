package com.realworld.io.data.di

import com.realworld.io.domain.use_cases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Providing  Validation dependency for DI
 */
@Module
@InstallIn(SingletonComponent::class)
object ValidationModule {

    /**
     * Providing Validate Email Class dependency for DI
     */
    @Singleton
    @Provides
    fun getEmail() : ValidateEmail {
        return ValidateEmail()
    }

    /**
     * Providing Validate Password Class dependency for DI
     */
    @Singleton
    @Provides
    fun getPassword() : ValidatePassword {
        return ValidatePassword()
    }

    /**
     * Providing Validate Username Class dependency for DI
     */
    @Singleton
    @Provides
    fun getUsername() : ValidateUsername {
        return ValidateUsername()
    }

    /**
     * Providing Validate Title Class dependency for DI
     */
    @Singleton
    @Provides
    fun getTitle() : ValidateTitle {
        return ValidateTitle()
    }

    /**
     * Providing Validate ShortDesc Class dependency for DI
     */
    @Singleton
    @Provides
    fun getShortDesc() : ValidateShortDesc {
        return ValidateShortDesc()
    }

    /**
     * Providing Validate Description Class dependency for DI
     */
    @Singleton
    @Provides
    fun getDesc() : ValidateDesc {
        return ValidateDesc()
    }

}
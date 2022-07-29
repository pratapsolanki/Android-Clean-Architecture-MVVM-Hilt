package com.realworld.io

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Base Application
 * @HiltAndroidApp - This annotation will generate a base class
 */
@HiltAndroidApp
class BaseApplication : Application(){
    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        private lateinit var instance: BaseApplication

        fun getInstance(): BaseApplication = instance
    }
}
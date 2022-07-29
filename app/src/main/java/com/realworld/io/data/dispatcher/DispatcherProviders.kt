package com.realworld.io.data.dispatcher

import kotlinx.coroutines.CoroutineDispatcher

/**
 * Interface for providing Dispatchers
 * This interface will create dispatcher only once
 */
interface DispatcherProviders {
    val main : CoroutineDispatcher
    val io : CoroutineDispatcher
    val default : CoroutineDispatcher
    val undefined : CoroutineDispatcher
}
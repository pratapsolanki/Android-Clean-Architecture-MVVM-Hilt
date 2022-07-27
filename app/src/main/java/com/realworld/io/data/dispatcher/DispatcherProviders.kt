package com.realworld.io.data.dispatcher

import kotlinx.coroutines.CoroutineDispatcher

interface DispatcherProviders {
    val main : CoroutineDispatcher
    val io : CoroutineDispatcher
    val default : CoroutineDispatcher
    val undefined : CoroutineDispatcher
}
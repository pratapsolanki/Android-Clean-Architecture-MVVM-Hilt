package com.realworld.io.data.repo

import com.realworld.io.data.dispatcher.DispatcherProviders
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class DispatcherImpl : DispatcherProviders {
    override val main: CoroutineDispatcher
        get() = Dispatchers.Main
    override val io: CoroutineDispatcher
        get() = Dispatchers.IO
    override val default: CoroutineDispatcher
        get() = Dispatchers.Default
    override val undefined: CoroutineDispatcher
        get() = Dispatchers.Unconfined

}
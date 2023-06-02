package com.kashif.common.util

import kotlinx.coroutines.CoroutineDispatcher

/**
 * A Dispatcher abstraction in order to ease testing coroutines
 */
interface DispatcherProvider {
    val main: CoroutineDispatcher
    val io: CoroutineDispatcher
    val unconfined: CoroutineDispatcher
}

internal expect fun getDispatcherProvider(): DispatcherProvider
package com.dogify.common.util

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import platform.darwin.NSObject

actual class NetworkConnectivityChecker: INetworkConnectivityChecker {
    private val _isConnected: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isConnected: StateFlow<Boolean> get() = _isConnected
    private var monitor: NSObject?

    init {
        monitor = null
    }
    override fun isConnected(): Boolean {
        return true
    }
}


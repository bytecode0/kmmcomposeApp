package com.kashif.common.util

actual class NetworkConnectivityChecker: INetworkConnectivityChecker {
    override fun isConnected(): Boolean {
        return true
    }
}


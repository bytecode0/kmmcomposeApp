package com.dogify.common.util

actual class NetworkConnectivityChecker: INetworkConnectivityChecker {
    override fun isConnected(): Boolean {
        return true
    }
}
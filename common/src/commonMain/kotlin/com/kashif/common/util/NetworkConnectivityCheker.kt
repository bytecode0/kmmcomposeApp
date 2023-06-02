package com.kashif.common.util

expect class NetworkConnectivityChecker(): INetworkConnectivityChecker

interface INetworkConnectivityChecker {
    fun isConnected(): Boolean
}
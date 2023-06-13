package com.dogify.common.util

expect class NetworkConnectivityChecker(): INetworkConnectivityChecker

interface INetworkConnectivityChecker {
    fun isConnected(): Boolean
}
package com.kashif.common.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

actual class NetworkConnectivityChecker: KoinComponent, INetworkConnectivityChecker {
    private val context: Context by inject()

    override fun isConnected(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            return when {
                capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_SUPL) -> true
                capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_FOTA) -> true
                capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_MMS) -> true
                capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_IMS) -> true
                capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) -> true
                else -> false
            }
        }
        return connectivityManager.activeNetworkInfo?.isConnected ?: false
    }
}
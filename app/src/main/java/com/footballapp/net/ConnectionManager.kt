package com.footballapp.net

import android.net.ConnectivityManager

class ConnectionManager {

    fun hasNetworkConnection(connectivityManager: ConnectivityManager): Boolean =
        connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo.isConnected
}
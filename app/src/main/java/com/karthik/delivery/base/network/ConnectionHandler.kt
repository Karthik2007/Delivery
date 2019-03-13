package com.karthik.delivery.base.network

import android.content.Context
import android.net.ConnectivityManager


/**
 *
 * created by Karthik A on 09/03/19
 */
class ConnectionHandler(private val context: Context) {

    val isConnected: Boolean
        get() {
            var networkInfo = (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo
            networkInfo?.let { return it.isConnected }
            return false
        }
}
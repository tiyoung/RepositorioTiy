package com.mv.listadecontatos.utils

import android.hardware.camera2.params.Capability
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.widget.Toast

class NetworkCheck(private val connectivityManager: ConnectivityManager) {

    fun performActionisConnected(action: () -> Unit){
        if(isConnected()){
            action()
        }
    }

    fun isConnected(): Boolean{
        if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M){ //API23
            val network:Network = connectivityManager?.activeNetwork ?: return false
            val capabilities: NetworkCapabilities = connectivityManager.getNetworkCapabilities(network) ?: return false

            return capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                    || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                    || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_VPN)
        }
        else{
            val activeNetworkInfo : NetworkInfo? = connectivityManager?.activeNetworkInfo

            if(activeNetworkInfo != null){
                return activeNetworkInfo.type == ConnectivityManager.TYPE_WIFI || activeNetworkInfo.type == ConnectivityManager.TYPE_MOBILE
            }
            else{
                return false
            }
        }
    }
}
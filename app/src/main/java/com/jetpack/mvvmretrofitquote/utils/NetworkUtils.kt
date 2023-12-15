package com.jetpack.mvvmretrofitquote.utils

import android.content.Context

import android.net.ConnectivityManager

class NetworkUtils {

    companion object{
        fun isNetworkAvailable(context: Context): Boolean {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            return connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo!!
                .isConnected
        }
    }
}
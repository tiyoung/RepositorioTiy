package com.mv.listadecontatos.utils

import android.content.Context
import android.net.ConnectivityManager
import android.text.TextUtils


object Validates {

    fun String.isEmailValid(): Boolean {
        return !TextUtils.isEmpty(this) && android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
    }
}
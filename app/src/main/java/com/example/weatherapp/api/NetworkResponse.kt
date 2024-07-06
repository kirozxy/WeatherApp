package com.example.weatherapp.api

import android.telephony.SmsMessage

sealed class NetworkResponse<out T> {
    data class Success<out T>(val data: T ): NetworkResponse<T>()
    data class  Error  (val message: String):NetworkResponse<Nothing>()

    object Loading : NetworkResponse<Nothing>()
}
package com.example.weatherapp.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstant {
    private const val baseUrl = "https://api.weatherapi.com/"
    private fun getInstant():Retrofit{
        return Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build()
    }
    val weatherApi : WeatherApi = getInstant().create(WeatherApi::class.java)
}
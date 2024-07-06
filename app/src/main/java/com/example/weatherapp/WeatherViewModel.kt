package com.example.weatherapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.api.Constant
import com.example.weatherapp.api.NetworkResponse
import com.example.weatherapp.api.RetrofitInstant
import com.example.weatherapp.api.WeatherModel
import kotlinx.coroutines.launch

class WeatherViewModel:ViewModel(){
    private val weatherApi = RetrofitInstant.weatherApi
    private val WeatherResult = MutableLiveData<NetworkResponse<WeatherModel>>()
    private val wtherrs : LiveData<NetworkResponse<WeatherModel>> = WeatherResult
    fun getData(search:String){
        WeatherResult.value = NetworkResponse.Loading
        viewModelScope.launch {
          val  response =  weatherApi.getWeather(Constant.apiKey, search)
         try {
             if(response.isSuccessful){
                 response.body()?.let {
                     WeatherResult.value = NetworkResponse.Success(it)
                 }
             }else{
                 WeatherResult.value = NetworkResponse.Error("Something Wrong!")
             }
         }
         catch (e: Exception){
             WeatherResult.value = NetworkResponse.Error("Something Wrong!")
         }
        }
    }
}
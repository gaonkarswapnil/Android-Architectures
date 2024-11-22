package com.example.androidarchitecture.model

import android.util.Log
import kotlinx.coroutines.flow.flow

class WeatherRepository {
    val apiServices = RetrofitHelper.getInstance()

    fun getWeatherData(weatherRequest: WeatherRequest) = flow {
        val weatherResponse = apiServices.getWeatherData(
            weatherRequest.key,
            weatherRequest.location,
            weatherRequest.days,
            weatherRequest.aqi,
            weatherRequest.alerts
        )

        if(weatherResponse.isSuccessful && weatherResponse!=null){
            emit(weatherResponse.body())
        }else{
            Log.d("Retrofit_Weather_Repository", weatherResponse.message())
        }
    }
}
package com.example.androidarchitecture.model

import retrofit2.Response

class WeatherRepository(private val apiServices: ApiServices) {
    suspend fun getWeatherData(
        key: String,
        location: String,
        days: Int,
        aqi: String,
        alerts: String
    ): Response<WeatherResponse> {
        return apiServices.getWeatherData(key, location, days, aqi, alerts)
    }
}
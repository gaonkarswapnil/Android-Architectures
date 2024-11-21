package com.example.androidarchitecture

import android.provider.CalendarContract.CalendarAlerts
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiServices {

    @GET("/v1/forecast.json?key={key}&q={location}&days={days}&aqi={aqi}&alerts={alert}")
    suspend fun getWeatherData(
        @Path("key") key: String,
        @Path("location") location: String,
        @Path("days") days: Int,
        @Path("aqi") aqi: String,
        @Path("alerts") alerts: String
    ): Response<WeatherResponse>
}
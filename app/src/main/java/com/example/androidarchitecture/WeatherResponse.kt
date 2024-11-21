package com.example.androidarchitecture

data class WeatherResponse(
    val current: Current,
    val forecast: Forecast,
    val location: Location
)
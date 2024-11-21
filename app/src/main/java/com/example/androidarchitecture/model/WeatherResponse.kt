package com.example.androidarchitecture.model

data class WeatherResponse(
    val current: Current,
    val forecast: Forecast,
    val location: Location
)
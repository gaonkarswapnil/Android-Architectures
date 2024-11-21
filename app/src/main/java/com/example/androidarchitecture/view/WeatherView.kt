package com.example.androidarchitecture.view

interface WeatherView {
    fun showWeatherData(location: String, temperature: String, condition: String, iconUrl: String)
//    fun showError(message: String)
}
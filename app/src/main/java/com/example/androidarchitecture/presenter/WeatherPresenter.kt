package com.example.androidarchitecture.presenter

import com.example.androidarchitecture.model.WeatherRepository
import com.example.androidarchitecture.view.WeatherView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WeatherPresenter(
    private val view: WeatherView,
    private val repository: WeatherRepository
) {

    fun fetchWeatherData(key: String, location: String, days: Int, aqi: String, alerts: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = repository.getWeatherData(key, location, days, aqi, alerts)
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        response.body()?.let { weatherResponse ->
                            val locationName = weatherResponse.location.name
                            val temperature = "${weatherResponse.current.temp_c} °C"
                            val condition = weatherResponse.current.condition.text
                            val iconUrl = "http:${weatherResponse.current.condition.icon}"

                            view.showWeatherData(locationName, temperature, condition, iconUrl)
                        }
                    }
                }
            } catch (e: Exception) {
//                withContext(Dispatchers.Main) {
//                    view.showError(e.message ?: "An unknown error occurred")
//                }
                e.printStackTrace()
            }
        }
    }

}
package com.example.androidarchitecture.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidarchitecture.model.WeatherRepository
import com.example.androidarchitecture.model.WeatherRequest
import com.example.androidarchitecture.model.WeatherResponse
import kotlinx.coroutines.launch

class WeatherViewModel(
    val application: Application,
    val weatherRepository: WeatherRepository
): ViewModel() {
    fun getWeatherData(weatherRequest: WeatherRequest): LiveData<WeatherResponse>{
        val weatherResponse = MutableLiveData<WeatherResponse>()

        viewModelScope.launch {
            weatherRepository.getWeatherData(weatherRequest).collect{
                weatherResponse.value = it
            }
        }

        return weatherResponse
    }
}
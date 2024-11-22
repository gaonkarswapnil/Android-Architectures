package com.example.androidarchitecture.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.androidarchitecture.model.WeatherRepository

class WeatherViewModelFactory(
    private val application: Application,
    private val weatherRepository: WeatherRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(WeatherViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return WeatherViewModel(application, weatherRepository) as T
        }
        throw IllegalArgumentException("Unknown Argument")
    }
}
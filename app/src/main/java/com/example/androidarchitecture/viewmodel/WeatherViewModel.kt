package com.example.androidarchitecture.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.androidarchitecture.model.WeatherRepository
import com.example.androidarchitecture.model.WeatherRequest
import com.example.androidarchitecture.model.WeatherResponse
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo


class WeatherViewModel(
    private val application: Application,
    private val weatherRepository: WeatherRepository
): ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    fun getWeatherData(weatherRequest: WeatherRequest): LiveData<WeatherResponse>{

        val _weatherLiveData = MutableLiveData<WeatherResponse>()

        weatherRepository.getWeatherData(weatherRequest)
            .subscribe({weather ->
                _weatherLiveData.value = weather
            }, {error ->

            }).addTo(compositeDisposable)

        return _weatherLiveData
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}
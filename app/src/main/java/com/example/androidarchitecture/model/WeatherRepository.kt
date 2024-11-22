package com.example.androidarchitecture.model

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers

class WeatherRepository {
    val apiServices = RetrofitHelper.getInstance()

    fun getWeatherData(weatherRequest: WeatherRequest): Observable<WeatherResponse>{
        return apiServices.getWeatherData(
            weatherRequest.key,
            weatherRequest.location,
            weatherRequest.days,
            weatherRequest.aqi,
            weatherRequest.alerts
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}
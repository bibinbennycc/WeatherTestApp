package com.volvotest.weatherapp.feature.repository

import com.volvotest.weatherapp.common.Resource
import com.volvotest.weatherapp.feature.model.WeatherModel

interface AppRepository {
    suspend fun getWeatherInfo(locationId: String, date: String): Resource<ArrayList<WeatherModel>>
}
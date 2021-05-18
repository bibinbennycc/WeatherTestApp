package com.volvotest.weatherapp.repository

import com.volvotest.weatherapp.common.Resource
import com.volvotest.weatherapp.feature.model.WeatherModel
import com.volvotest.weatherapp.feature.repository.AppRepository

class MockRepository : AppRepository {

    var returnErrorResponse: Boolean = false

    override suspend fun getWeatherInfo(
        locationId: String,
        date: String
    ): Resource<ArrayList<WeatherModel>> {
        if (returnErrorResponse) {
            return Resource.error(null, "Something went wrong")
        } else {
            val list = arrayListOf(
                WeatherModel(
                    "Sunny",
                    "s",
                    10.01F,
                    20.20F,
                    15.0F,
                    "10/12/12",
                    75,
                    13.10F
                )
            )
            return Resource.success(list)
        }
    }
}
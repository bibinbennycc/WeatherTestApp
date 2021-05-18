package com.volvotest.weatherapp.network

import com.volvotest.weatherapp.feature.model.WeatherModel
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiServices {

    @GET("location/{woeid}/{date}/")
    suspend fun getWeatherInfo(
        @Path("woeid") locationId: String,
        @Path("date") date: String
    ): ArrayList<WeatherModel>
}
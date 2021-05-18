package com.volvotest.weatherapp.feature.repository

import android.util.Log
import com.volvotest.weatherapp.common.Resource
import com.volvotest.weatherapp.feature.model.WeatherModel
import com.volvotest.weatherapp.network.ApiServices
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WeatherRepository(private val apiServices: ApiServices) : AppRepository {
    override suspend fun getWeatherInfo(
        locationId: String,
        date: String
    ): Resource<ArrayList<WeatherModel>> {
        var response: Resource<ArrayList<WeatherModel>>
        try {
            withContext(Dispatchers.IO) {
                val result = apiServices.getWeatherInfo(locationId, date)
                response = Resource.success(result)
            }
        } catch (exception: Exception) {
            response = Resource.error(null, "Something went wrong")
            Log.e("Waether App Error", exception.toString())
        }
        return response
    }
}
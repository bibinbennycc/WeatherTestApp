package com.volvotest.weatherapp.feature.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.volvotest.weatherapp.common.Resource
import com.volvotest.weatherapp.feature.model.WeatherModel
import com.volvotest.weatherapp.feature.repository.AppRepository
import kotlinx.coroutines.launch

class WeatherViewModel @ViewModelInject constructor(private val repository: AppRepository) :
    ViewModel() {

    private val _weatherInfo: MutableLiveData<Resource<ArrayList<WeatherModel>>> = MutableLiveData()
    val weatherInfo: LiveData<Resource<ArrayList<WeatherModel>>>
        get() = _weatherInfo

    fun getWeatherInfo(locationId: String, date: String) {
        viewModelScope.launch {
            val result = repository.getWeatherInfo(locationId, date)
            setWeatherInfo(result)
        }
    }

    private fun setWeatherInfo(result: Resource<ArrayList<WeatherModel>>) {
        _weatherInfo.value = result
    }
}
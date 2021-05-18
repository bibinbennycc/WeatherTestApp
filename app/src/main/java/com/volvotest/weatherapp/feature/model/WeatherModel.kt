package com.volvotest.weatherapp.feature.model

import com.google.gson.annotations.SerializedName

data class WeatherModel(
    @SerializedName("weather_state_name")
    val stateName: String,
    @SerializedName("weather_state_abbr")
    val stateAbbr: String,
    @SerializedName("min_temp")
    val minTemperature: Float,
    @SerializedName("max_temp")
    val maxTemperature: Float,
    @SerializedName("wind_speed")
    val windSpeed: Float,
    @SerializedName("applicable_date")
    val date: String,
    val predictability: Int,
    val humidity: Float
)
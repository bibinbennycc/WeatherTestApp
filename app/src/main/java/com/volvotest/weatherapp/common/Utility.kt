package com.volvotest.weatherapp.common

import java.text.SimpleDateFormat
import java.util.*

object Utility {

    private const val weatherIconUrl = "https://www.metaweather.com/static/img/weather/png/"
    private const val themeDateFormat = "yyyy/MM/dd"

    fun getWeatherIconUrl(status: String): String {
        return "$weatherIconUrl$status.png"
    }

    fun getTomorrowDate(): String {
        val calender = Calendar.getInstance()
        calender.add(Calendar.DAY_OF_MONTH, 1)
        val tomorrowDate = calender.time
        val dateFormat = SimpleDateFormat(themeDateFormat)
        return dateFormat.format(tomorrowDate)
    }

}
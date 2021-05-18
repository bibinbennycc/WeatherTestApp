package com.volvotest.weatherapp.feature.view

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.volvotest.weatherapp.R
import com.volvotest.weatherapp.common.Locations
import com.volvotest.weatherapp.common.Status
import com.volvotest.weatherapp.common.Utility
import com.volvotest.weatherapp.feature.model.WeatherModel
import com.volvotest.weatherapp.feature.viewmodel.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

@AndroidEntryPoint
class WeatherInfoActivity : AppCompatActivity() {

    private val viewModel: WeatherViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setLocationSpinner()
        setObservers()
    }

    private fun setObservers() {
        viewModel.weatherInfo.observe(this, Observer { weatherResponse ->
            progress_bar.isVisible = false
            when (weatherResponse.status) {
                Status.SUCCESS -> setWeatherInfoUI(weatherResponse.data)
                Status.ERROR -> handleWeatherInfoError(weatherResponse?.message)
                else -> handleWeatherInfoError(getString(R.string.something_went_wrong))
            }
        })
    }

    private fun setWeatherInfoUI(response: ArrayList<WeatherModel>?) {
        val info = response?.firstOrNull()
        if (info?.stateAbbr != null) {
            Glide.with(baseContext).load(Utility.getWeatherIconUrl(info.stateAbbr))
                .into(weather_icon)
        } else {
            weather_icon.setImageResource(R.drawable.ic_launcher_foreground)
        }

        val maxTempVal = info?.maxTemperature?.toInt()
            ?.let { "${it}${getString(R.string.degree_celsius_symbol)}" }
            ?: getString(R.string.no_value_label)
        val humidityVal =
            info?.humidity?.toInt()?.let { "${it}${getString(R.string.percentage_symbol)}" }
                ?: getString(R.string.no_value_label)
        val windSpeedVal =
            info?.windSpeed?.toInt()?.let { "$it ${getString(R.string.wind_speed_unit)}" }
                ?: getString(R.string.no_value_label)
        val predictabilityVal =
            info?.predictability?.let { "${it}${getString(R.string.percentage_symbol)}" }
                ?: getString(R.string.no_value_label)
        val stateName =
            if (!info?.stateName.isNullOrEmpty()) info?.stateName else getString(R.string.no_value_label)
        val weatherDate =
            if (!info?.date.isNullOrEmpty()) info?.date else getString(R.string.no_value_label)

        max_temperature.text = maxTempVal
        weather_name.text = stateName
        humidity.text = humidityVal
        wind_speed.text = windSpeedVal
        predictability.text = predictabilityVal
        weather_date.text = weatherDate
    }

    private fun setLocationSpinner() {
        val adapter = ArrayAdapter<String>(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            Locations.values().map { it.locationName }
        )
        location_spinner.adapter = adapter
        location_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {}
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                val selectedLocation = Locations.values()[position]
                getWeatherInfo(selectedLocation)
            }
        }
    }

    private fun getWeatherInfo(location: Locations) {
        val date = Utility.getTomorrowDate()
        progress_bar.isVisible = true
        viewModel.getWeatherInfo(location.id, date)
    }

    private fun handleWeatherInfoError(message: String?) {
        setWeatherInfoUI(null)
        Toast.makeText(
            this,
            message ?: getString(R.string.something_went_wrong),
            Toast.LENGTH_SHORT
        ).show()
    }
}
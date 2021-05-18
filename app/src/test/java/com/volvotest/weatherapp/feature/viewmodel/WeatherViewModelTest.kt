package com.volvotest.weatherapp.feature.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.volvotest.weatherapp.MainCoroutineRule
import com.volvotest.weatherapp.common.Status
import com.volvotest.weatherapp.getOrAwaitValueTest
import com.volvotest.weatherapp.repository.MockRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class WeatherViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var repository: MockRepository
    private lateinit var viewModel: WeatherViewModel

    @Before
    fun setUp() {
        repository = MockRepository()
        viewModel = WeatherViewModel(repository)
    }

    @Test
    fun testGetWeatherInfoReturnsSuccess() = mainCoroutineRule.runBlockingTest {
        viewModel.getWeatherInfo("1002", "2021/05/12")
        val result = viewModel.weatherInfo.getOrAwaitValueTest()
        assertThat(result.status).isEqualTo(Status.SUCCESS)
    }

    @Test
    fun testGetWeatherInfoReturnsError() = mainCoroutineRule.runBlockingTest {
        repository.returnErrorResponse = true
        viewModel.getWeatherInfo("1002", "2021/05/12")
        val result = viewModel.weatherInfo.getOrAwaitValueTest()
        assertThat(result.status).isEqualTo(Status.ERROR)
    }
}
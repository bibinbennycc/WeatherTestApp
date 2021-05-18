package com.volvotest.weatherapp.di

import com.volvotest.weatherapp.feature.repository.AppRepository
import com.volvotest.weatherapp.feature.repository.WeatherRepository
import com.volvotest.weatherapp.network.ApiServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideWeatherRepository(apiServices: ApiServices): AppRepository {
        return WeatherRepository(apiServices)
    }
}
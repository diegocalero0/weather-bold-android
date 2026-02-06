package com.diegocalero.weatherbold.data.repository

import com.diegocalero.weatherbold.BuildConfig
import com.diegocalero.weatherbold.core.network.Result
import com.diegocalero.weatherbold.core.network.safeApiCall
import com.diegocalero.weatherbold.data.mapper.toDomain
import com.diegocalero.weatherbold.data.remote.WeatherApiService
import com.diegocalero.weatherbold.domain.model.Forecast
import com.diegocalero.weatherbold.domain.model.Location
import com.diegocalero.weatherbold.domain.repository.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val apiService: WeatherApiService
) : WeatherRepository {

    override suspend fun searchLocations(query: String): Result<List<Location>> {
        return safeApiCall {
            apiService.searchLocations(
                apiKey = BuildConfig.WEATHER_API_KEY,
                query = query
            ).map { it.toDomain() }
        }
    }

    override suspend fun getForecast(query: String, days: Int): Result<Forecast> {
        return safeApiCall {
            apiService.getForecast(
                apiKey = BuildConfig.WEATHER_API_KEY,
                query = query,
                days = days
            ).toDomain()
        }
    }
}
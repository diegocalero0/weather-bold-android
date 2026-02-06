package com.diegocalero.weatherbold.data.remote

import com.diegocalero.weatherbold.data.remote.dto.ForecastResponseDto
import com.diegocalero.weatherbold.data.remote.dto.LocationDto
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {

    @GET("search.json")
    suspend fun searchLocations(
        @Query("key") apiKey: String,
        @Query("q") query: String
    ): List<LocationDto>

    @GET("forecast.json")
    suspend fun getForecast(
        @Query("key") apiKey: String,
        @Query("q") query: String,
        @Query("days") days: Int
    ): ForecastResponseDto
}
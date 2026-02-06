package com.diegocalero.weatherbold.domain.repository

import com.diegocalero.weatherbold.core.network.Result
import com.diegocalero.weatherbold.domain.model.Forecast
import com.diegocalero.weatherbold.domain.model.Location

interface WeatherRepository {

    suspend fun searchLocations(query: String): Result<List<Location>>
    suspend fun getForecast(query: String, days: Int): Result<Forecast>
}
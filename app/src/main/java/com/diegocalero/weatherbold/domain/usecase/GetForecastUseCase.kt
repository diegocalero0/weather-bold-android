package com.diegocalero.weatherbold.domain.usecase

import com.diegocalero.weatherbold.core.network.Result
import com.diegocalero.weatherbold.domain.model.Forecast
import com.diegocalero.weatherbold.domain.repository.WeatherRepository
import javax.inject.Inject

class GetForecastUseCase @Inject constructor(
    private val repository: WeatherRepository
) {

    suspend operator fun invoke(query: String, days: Int = DEFAULT_FORECAST_DAYS): Result<Forecast> {
        return repository.getForecast(query = query, days = days)
    }

    companion object {
        private const val DEFAULT_FORECAST_DAYS = 3
    }
}
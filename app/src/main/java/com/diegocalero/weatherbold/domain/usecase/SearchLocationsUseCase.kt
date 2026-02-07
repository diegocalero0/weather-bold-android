package com.diegocalero.weatherbold.domain.usecase

import com.diegocalero.weatherbold.core.network.Result
import com.diegocalero.weatherbold.domain.model.Location
import com.diegocalero.weatherbold.domain.repository.WeatherRepository
import javax.inject.Inject

class SearchLocationsUseCase
    @Inject
    constructor(
        private val repository: WeatherRepository,
    ) {
        suspend operator fun invoke(query: String): Result<List<Location>> {
            return repository.searchLocations(query)
        }
    }

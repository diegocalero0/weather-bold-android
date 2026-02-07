package com.diegocalero.weatherbold.data.repository

import com.diegocalero.weatherbold.BuildConfig
import com.diegocalero.weatherbold.core.network.AppException
import com.diegocalero.weatherbold.core.network.Result
import com.diegocalero.weatherbold.data.remote.WeatherApiService
import com.diegocalero.weatherbold.data.remote.dto.AstroDto
import com.diegocalero.weatherbold.data.remote.dto.ConditionDto
import com.diegocalero.weatherbold.data.remote.dto.CurrentWeatherDto
import com.diegocalero.weatherbold.data.remote.dto.DayDto
import com.diegocalero.weatherbold.data.remote.dto.ForecastDayDto
import com.diegocalero.weatherbold.data.remote.dto.ForecastDto
import com.diegocalero.weatherbold.data.remote.dto.ForecastLocationDto
import com.diegocalero.weatherbold.data.remote.dto.ForecastResponseDto
import com.diegocalero.weatherbold.data.remote.dto.LocationDto
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class WeatherRepositoryImplTest {
    private lateinit var apiService: WeatherApiService
    private lateinit var repository: WeatherRepositoryImpl

    @Before
    fun setup() {
        apiService = mockk()
        repository = WeatherRepositoryImpl(apiService)
    }

    @Test
    fun `searchLocations should return success with mapped locations`() =
        runTest {
            // Given
            val query = "London"
            val locationDtos =
                listOf(
                    LocationDto(
                        id = 1,
                        name = "London",
                        region = "City of London",
                        country = "United Kingdom",
                        lat = 51.5074,
                        lon = -0.1278,
                        url = "london-city-of-london-united-kingdom",
                    ),
                    LocationDto(
                        id = 2,
                        name = "London",
                        region = "Ontario",
                        country = "Canada",
                        lat = 42.9834,
                        lon = -81.2497,
                        url = "london-ontario-canada",
                    ),
                )
            coEvery { apiService.searchLocations(BuildConfig.WEATHER_API_KEY, query, null) } returns locationDtos

            // When
            val result = repository.searchLocations(query)

            // Then
            coVerify { apiService.searchLocations(BuildConfig.WEATHER_API_KEY, query, null) }
            assertTrue(result is Result.Success)
            assertEquals(2, (result as Result.Success).data.size)
            assertEquals("London", result.data[0].name)
            assertEquals("City of London", result.data[0].region)
        }

    @Test
    fun `searchLocations should handle empty results`() =
        runTest {
            // Given
            val query = "NonExistent"
            coEvery { apiService.searchLocations(BuildConfig.WEATHER_API_KEY, query, null) } returns emptyList()

            // When
            val result = repository.searchLocations(query)

            // Then
            assertTrue(result is Result.Success)
            assertEquals(0, (result as Result.Success).data.size)
        }

    @Test
    fun `getForecast should return success with mapped forecast`() =
        runTest {
            // Given
            val query = "Tokyo"
            val days = 3
            val forecastDto = createMockForecastResponseDto()
            coEvery { apiService.getForecast(BuildConfig.WEATHER_API_KEY, query, days, null) } returns forecastDto

            // When
            val result = repository.getForecast(query, days)

            // Then
            coVerify { apiService.getForecast(BuildConfig.WEATHER_API_KEY, query, days, null) }
            assertTrue(result is Result.Success)
            val forecast = (result as Result.Success).data
            assertEquals("Tokyo", forecast.locationName)
            assertEquals(15.0, forecast.currentWeather.tempC, 0.01)
        }

    @Test
    fun `getForecast with 1 day should work correctly`() =
        runTest {
            // Given
            val query = "Paris"
            val days = 1
            val forecastDto = createMockForecastResponseDto()
            coEvery { apiService.getForecast(BuildConfig.WEATHER_API_KEY, query, days, null) } returns forecastDto

            // When
            val result = repository.getForecast(query, days)

            // Then
            coVerify { apiService.getForecast(BuildConfig.WEATHER_API_KEY, query, days, null) }
            assertTrue(result is Result.Success)
        }

    @Test
    fun `getForecast with 14 days should work correctly`() =
        runTest {
            // Given
            val query = "New York"
            val days = 14
            val forecastDto = createMockForecastResponseDto()
            coEvery { apiService.getForecast(BuildConfig.WEATHER_API_KEY, query, days, null) } returns forecastDto

            // When
            val result = repository.getForecast(query, days)

            // Then
            coVerify { apiService.getForecast(BuildConfig.WEATHER_API_KEY, query, days, null) }
            assertTrue(result is Result.Success)
        }

    @Test
    fun `searchLocations should handle network exceptions`() =
        runTest {
            // Given
            val query = "London"
            coEvery { apiService.searchLocations(any(), any(), any()) } throws Exception("Network error")

            // When
            val result = repository.searchLocations(query)

            // Then
            assertTrue(result is Result.Error)
            assertTrue((result as Result.Error).exception is AppException)
        }

    @Test
    fun `getForecast should handle network exceptions`() =
        runTest {
            // Given
            val query = "Paris"
            val days = 3
            coEvery { apiService.getForecast(any(), any(), any(), any()) } throws Exception("Network error")

            // When
            val result = repository.getForecast(query, days)

            // Then
            assertTrue(result is Result.Error)
            assertTrue((result as Result.Error).exception is AppException)
        }

    private fun createMockLocationDto(): LocationDto {
        return LocationDto(
            id = 1,
            name = "Madrid",
            region = "Madrid",
            country = "Spain",
            lat = 40.4168,
            lon = -3.7038,
            url = "madrid-madrid-spain",
        )
    }

    private fun createMockForecastResponseDto(): ForecastResponseDto {
        return ForecastResponseDto(
            location =
                ForecastLocationDto(
                    name = "Tokyo",
                    region = "Tokyo",
                    country = "Japan",
                    lat = 35.6895,
                    lon = 139.6917,
                    localtime = "2024-01-01 12:00",
                ),
            current =
                CurrentWeatherDto(
                    tempC = 15.0,
                    feelsLikeC = 13.0,
                    humidity = 70,
                    windKph = 10.0,
                    condition = ConditionDto("Clear", "//cdn.weatherapi.com/weather/64x64/day/113.png", 1000),
                ),
            forecast =
                ForecastDto(
                    forecastDay =
                        listOf(
                            ForecastDayDto(
                                date = "2024-01-01",
                                day =
                                    DayDto(
                                        maxTempC = 18.0,
                                        minTempC = 12.0,
                                        avgTempC = 15.0,
                                        avgHumidity = 70.0,
                                        maxWindKph = 15.0,
                                        chanceOfRain = 20.0,
                                        condition = ConditionDto("Clear", "//cdn.weatherapi.com/weather/64x64/day/113.png", 1000),
                                    ),
                                hour = emptyList(),
                                astro =
                                    AstroDto(
                                        sunrise = "06:00 AM",
                                        sunset = "06:00 PM",
                                    ),
                            ),
                        ),
                ),
        )
    }
}

package com.diegocalero.weatherbold.domain.usecase

import com.diegocalero.weatherbold.core.network.AppException
import com.diegocalero.weatherbold.core.network.Result
import com.diegocalero.weatherbold.domain.model.CurrentWeather
import com.diegocalero.weatherbold.domain.model.Forecast
import com.diegocalero.weatherbold.domain.model.ForecastDay
import com.diegocalero.weatherbold.domain.model.WeatherCondition
import com.diegocalero.weatherbold.domain.repository.WeatherRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class GetForecastUseCaseTest {
    private lateinit var repository: WeatherRepository
    private lateinit var useCase: GetForecastUseCase

    @Before
    fun setup() {
        repository = mockk()
        useCase = GetForecastUseCase(repository)
    }

    @Test
    fun `invoke with default days should call repository with 3 days`() =
        runTest {
            // Given
            val query = "London"
            val mockForecast = createMockForecast()
            coEvery { repository.getForecast(query, 3) } returns Result.Success(mockForecast)

            // When
            val result = useCase.invoke(query)

            // Then
            coVerify { repository.getForecast(query, 3) }
            assertTrue(result is Result.Success)
            assertEquals(mockForecast, (result as Result.Success).data)
        }

    @Test
    fun `invoke with custom days should call repository with specified days`() =
        runTest {
            // Given
            val query = "Paris"
            val days = 7
            val mockForecast = createMockForecast()
            coEvery { repository.getForecast(query, days) } returns Result.Success(mockForecast)

            // When
            val result = useCase.invoke(query, days)

            // Then
            coVerify { repository.getForecast(query, days) }
            assertTrue(result is Result.Success)
            assertEquals(mockForecast, (result as Result.Success).data)
        }

    @Test
    fun `invoke should return error when repository fails`() =
        runTest {
            // Given
            val query = "InvalidCity"
            val exception = AppException.NetworkException("Network error")
            coEvery { repository.getForecast(query, 3) } returns Result.Error(exception)

            // When
            val result = useCase.invoke(query)

            // Then
            assertTrue(result is Result.Error)
            assertEquals(exception, (result as Result.Error).exception)
        }

    @Test
    fun `invoke with empty query should call repository`() =
        runTest {
            // Given
            val query = ""
            val exception = AppException.NetworkException("Invalid query")
            coEvery { repository.getForecast(query, 3) } returns Result.Error(exception)

            // When
            val result = useCase.invoke(query)

            // Then
            coVerify { repository.getForecast(query, 3) }
            assertTrue(result is Result.Error)
        }

    @Test
    fun `invoke with days = 1 should work correctly`() =
        runTest {
            // Given
            val query = "Tokyo"
            val days = 1
            val mockForecast = createMockForecast()
            coEvery { repository.getForecast(query, days) } returns Result.Success(mockForecast)

            // When
            val result = useCase.invoke(query, days)

            // Then
            coVerify { repository.getForecast(query, days) }
            assertTrue(result is Result.Success)
        }

    @Test
    fun `invoke with days = 14 should work correctly`() =
        runTest {
            // Given
            val query = "New York"
            val days = 14
            val mockForecast = createMockForecast()
            coEvery { repository.getForecast(query, days) } returns Result.Success(mockForecast)

            // When
            val result = useCase.invoke(query, days)

            // Then
            coVerify { repository.getForecast(query, days) }
            assertTrue(result is Result.Success)
        }

    private fun createMockForecast(): Forecast {
        return Forecast(
            locationName = "London",
            region = "City of London",
            country = "United Kingdom",
            currentWeather =
                CurrentWeather(
                    tempC = 15.0,
                    feelsLikeC = 13.0,
                    humidity = 75,
                    windKph = 20.0,
                    condition = WeatherCondition("Partly cloudy", "//cdn.weatherapi.com/weather/64x64/day/116.png", 1003),
                ),
            forecastDays =
                listOf(
                    ForecastDay(
                        date = "2024-01-01",
                        maxTempC = 18.0,
                        minTempC = 12.0,
                        avgTempC = 15.0,
                        avgHumidity = 70.0,
                        maxWindKph = 25.0,
                        chanceOfRain = 30.0,
                        condition = WeatherCondition("Partly cloudy", "//cdn.weatherapi.com/weather/64x64/day/116.png", 1003),
                        hours = emptyList(),
                        sunrise = "07:00 AM",
                        sunset = "05:30 PM",
                    ),
                ),
        )
    }
}

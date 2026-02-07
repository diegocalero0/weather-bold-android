package com.diegocalero.weatherbold.domain.usecase

import com.diegocalero.weatherbold.core.network.AppException
import com.diegocalero.weatherbold.core.network.Result
import com.diegocalero.weatherbold.domain.model.Location
import com.diegocalero.weatherbold.domain.repository.WeatherRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class SearchLocationsUseCaseTest {
    private lateinit var repository: WeatherRepository
    private lateinit var useCase: SearchLocationsUseCase

    @Before
    fun setup() {
        repository = mockk()
        useCase = SearchLocationsUseCase(repository)
    }

    @Test
    fun `invoke should return success with locations list`() =
        runTest {
            // Given
            val query = "London"
            val mockLocations =
                listOf(
                    createMockLocation(1, "London", "City of London", "United Kingdom"),
                    createMockLocation(2, "London", "Ontario", "Canada"),
                )
            coEvery { repository.searchLocations(query) } returns Result.Success(mockLocations)

            // When
            val result = useCase.invoke(query)

            // Then
            coVerify { repository.searchLocations(query) }
            assertTrue(result is Result.Success)
            assertEquals(2, (result as Result.Success).data.size)
            assertEquals("London", result.data[0].name)
        }

    @Test
    fun `invoke should return empty list when no locations found`() =
        runTest {
            // Given
            val query = "NonExistentCity123"
            coEvery { repository.searchLocations(query) } returns Result.Success(emptyList())

            // When
            val result = useCase.invoke(query)

            // Then
            assertTrue(result is Result.Success)
            assertEquals(0, (result as Result.Success).data.size)
        }

    @Test
    fun `invoke should return error when repository fails`() =
        runTest {
            // Given
            val query = "Paris"
            val exception = AppException.NetworkException("Network error")
            coEvery { repository.searchLocations(query) } returns Result.Error(exception)

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
            coEvery { repository.searchLocations(query) } returns Result.Success(emptyList())

            // When
            val result = useCase.invoke(query)

            // Then
            coVerify { repository.searchLocations(query) }
            assertTrue(result is Result.Success)
        }

    @Test
    fun `invoke with single character query should work`() =
        runTest {
            // Given
            val query = "L"
            val mockLocations = listOf(createMockLocation(1, "London", "City", "UK"))
            coEvery { repository.searchLocations(query) } returns Result.Success(mockLocations)

            // When
            val result = useCase.invoke(query)

            // Then
            coVerify { repository.searchLocations(query) }
            assertTrue(result is Result.Success)
            assertEquals(1, (result as Result.Success).data.size)
        }

    @Test
    fun `invoke should handle special characters in query`() =
        runTest {
            // Given
            val query = "São Paulo"
            val mockLocations = listOf(createMockLocation(1, "São Paulo", "São Paulo", "Brazil"))
            coEvery { repository.searchLocations(query) } returns Result.Success(mockLocations)

            // When
            val result = useCase.invoke(query)

            // Then
            coVerify { repository.searchLocations(query) }
            assertTrue(result is Result.Success)
        }

    @Test
    fun `invoke should handle long query strings`() =
        runTest {
            // Given
            val query = "This is a very long query string that should still work correctly"
            coEvery { repository.searchLocations(query) } returns Result.Success(emptyList())

            // When
            val result = useCase.invoke(query)

            // Then
            coVerify { repository.searchLocations(query) }
            assertTrue(result is Result.Success)
        }

    private fun createMockLocation(
        id: Int,
        name: String,
        region: String,
        country: String,
    ): Location {
        return Location(
            id = id,
            name = name,
            region = region,
            country = country,
            lat = 51.5074,
            lon = -0.1278,
        )
    }
}

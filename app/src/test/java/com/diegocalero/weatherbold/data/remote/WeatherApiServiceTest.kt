package com.diegocalero.weatherbold.data.remote

import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Test class for WeatherApiService interface.
 * Since this is a Retrofit interface, we verify its structure and contract.
 * Integration tests with actual API calls should be done separately.
 */
class WeatherApiServiceTest {
    @Test
    fun `WeatherApiService should be an interface`() {
        // Verify that WeatherApiService is indeed an interface
        val isInterface = WeatherApiService::class.java.isInterface
        assertTrue("WeatherApiService should be an interface", isInterface)
    }

    @Test
    fun `WeatherApiService should have methods defined`() {
        // Verify that the interface has methods
        val methods = WeatherApiService::class.java.declaredMethods
        assertTrue("Should have methods declared", methods.isNotEmpty())
    }

    @Test
    fun `searchLocations method signature should be valid`() {
        // Verify method exists and can be called
        try {
            val methods = WeatherApiService::class.java.declaredMethods
            val searchMethod = methods.firstOrNull { it.name == "searchLocations" }
            assertNotNull("searchLocations method should exist", searchMethod)
        } catch (e: Exception) {
            throw AssertionError("Should be able to access searchLocations method")
        }
    }

    @Test
    fun `getForecast method signature should be valid`() {
        // Verify method exists and can be called
        try {
            val methods = WeatherApiService::class.java.declaredMethods
            val forecastMethod = methods.firstOrNull { it.name == "getForecast" }
            assertNotNull("getForecast method should exist", forecastMethod)
        } catch (e: Exception) {
            throw AssertionError("Should be able to access getForecast method")
        }
    }
}

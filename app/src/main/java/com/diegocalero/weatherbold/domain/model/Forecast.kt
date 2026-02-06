package com.diegocalero.weatherbold.domain.model

data class Forecast(
    val locationName: String,
    val region: String,
    val country: String,
    val currentWeather: CurrentWeather,
    val forecastDays: List<ForecastDay>
)

data class CurrentWeather(
    val tempC: Double,
    val feelsLikeC: Double,
    val humidity: Int,
    val windKph: Double,
    val condition: WeatherCondition
)

data class ForecastDay(
    val date: String,
    val maxTempC: Double,
    val minTempC: Double,
    val avgTempC: Double,
    val avgHumidity: Double,
    val maxWindKph: Double,
    val chanceOfRain: Double,
    val condition: WeatherCondition,
    val hours: List<HourForecast>
)

data class HourForecast(
    val time: String,
    val tempC: Double,
    val condition: WeatherCondition,
    val chanceOfRain: Double,
    val humidity: Int,
    val windKph: Double
)

data class WeatherCondition(
    val text: String,
    val iconUrl: String,
    val code: Int
)
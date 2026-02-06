package com.diegocalero.weatherbold.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ForecastResponseDto(
    @SerializedName("location") val location: ForecastLocationDto,
    @SerializedName("current") val current: CurrentWeatherDto,
    @SerializedName("forecast") val forecast: ForecastDto
)

data class ForecastLocationDto(
    @SerializedName("name") val name: String,
    @SerializedName("region") val region: String,
    @SerializedName("country") val country: String,
    @SerializedName("lat") val lat: Double,
    @SerializedName("lon") val lon: Double,
    @SerializedName("localtime") val localtime: String
)

data class CurrentWeatherDto(
    @SerializedName("temp_c") val tempC: Double,
    @SerializedName("feelslike_c") val feelsLikeC: Double,
    @SerializedName("humidity") val humidity: Int,
    @SerializedName("wind_kph") val windKph: Double,
    @SerializedName("condition") val condition: ConditionDto
)

data class ForecastDto(
    @SerializedName("forecastday") val forecastDay: List<ForecastDayDto>
)

data class ForecastDayDto(
    @SerializedName("date") val date: String,
    @SerializedName("day") val day: DayDto,
    @SerializedName("hour") val hour: List<HourDto>
)

data class DayDto(
    @SerializedName("maxtemp_c") val maxTempC: Double,
    @SerializedName("mintemp_c") val minTempC: Double,
    @SerializedName("avgtemp_c") val avgTempC: Double,
    @SerializedName("avghumidity") val avgHumidity: Double,
    @SerializedName("maxwind_kph") val maxWindKph: Double,
    @SerializedName("daily_chance_of_rain") val chanceOfRain: Double,
    @SerializedName("condition") val condition: ConditionDto
)

data class HourDto(
    @SerializedName("time") val time: String,
    @SerializedName("temp_c") val tempC: Double,
    @SerializedName("condition") val condition: ConditionDto,
    @SerializedName("chance_of_rain") val chanceOfRain: Double,
    @SerializedName("humidity") val humidity: Int,
    @SerializedName("wind_kph") val windKph: Double
)

data class ConditionDto(
    @SerializedName("text") val text: String,
    @SerializedName("icon") val icon: String,
    @SerializedName("code") val code: Int
)
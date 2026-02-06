package com.diegocalero.weatherbold.data.mapper

import com.diegocalero.weatherbold.data.remote.dto.ConditionDto
import com.diegocalero.weatherbold.data.remote.dto.CurrentWeatherDto
import com.diegocalero.weatherbold.data.remote.dto.ForecastDayDto
import com.diegocalero.weatherbold.data.remote.dto.ForecastResponseDto
import com.diegocalero.weatherbold.data.remote.dto.HourDto
import com.diegocalero.weatherbold.domain.model.CurrentWeather
import com.diegocalero.weatherbold.domain.model.Forecast
import com.diegocalero.weatherbold.domain.model.ForecastDay
import com.diegocalero.weatherbold.domain.model.HourForecast
import com.diegocalero.weatherbold.domain.model.WeatherCondition

fun ForecastResponseDto.toDomain(): Forecast {
    return Forecast(
        locationName = location.name,
        region = location.region,
        country = location.country,
        currentWeather = current.toDomain(),
        forecastDays = forecast.forecastDay.map { it.toDomain() }
    )
}

fun CurrentWeatherDto.toDomain(): CurrentWeather {
    return CurrentWeather(
        tempC = tempC,
        feelsLikeC = feelsLikeC,
        humidity = humidity,
        windKph = windKph,
        condition = condition.toDomain()
    )
}

fun ForecastDayDto.toDomain(): ForecastDay {
    return ForecastDay(
        date = date,
        maxTempC = day.maxTempC,
        minTempC = day.minTempC,
        avgTempC = day.avgTempC,
        avgHumidity = day.avgHumidity,
        maxWindKph = day.maxWindKph,
        chanceOfRain = day.chanceOfRain,
        condition = day.condition.toDomain(),
        hours = hour.map { it.toDomain() }
    )
}

fun HourDto.toDomain(): HourForecast {
    return HourForecast(
        time = time,
        tempC = tempC,
        condition = condition.toDomain(),
        chanceOfRain = chanceOfRain,
        humidity = humidity,
        windKph = windKph
    )
}

fun ConditionDto.toDomain(): WeatherCondition {
    return WeatherCondition(
        text = text,
        iconUrl = "https:${icon}",
        code = code
    )
}
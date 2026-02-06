package com.diegocalero.weatherbold.presentation.detail

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import android.content.res.Configuration
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.diegocalero.weatherbold.R
import com.diegocalero.weatherbold.core.ui.UiState
import com.diegocalero.weatherbold.core.ui.theme.BoldGradientEnd
import com.diegocalero.weatherbold.core.ui.theme.BoldGradientStart
import com.diegocalero.weatherbold.core.ui.theme.BoldNavy
import com.diegocalero.weatherbold.core.ui.theme.WeatherBoldTheme
import com.diegocalero.weatherbold.domain.model.Forecast
import com.diegocalero.weatherbold.presentation.detail.components.ForecastDayAccordion
import com.diegocalero.weatherbold.presentation.detail.components.HourlyForecastRow
import com.diegocalero.weatherbold.presentation.detail.components.SunriseSunsetRow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    query: String,
    onBackClick: () -> Unit,
    viewModel: DetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val expandedDays by viewModel.expandedDays.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    if (uiState is UiState.Success<Forecast>) {
                        val forecast = (uiState as UiState.Success<Forecast>).data
                        Column {
                            Text(
                                text = forecast.locationName,
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )

                            Text(
                                text = "${forecast.region}, ${forecast.country}",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.White.copy(alpha = 0.7f)
                            )
                        }
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(id = R.string.back),
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.secondary,
                )
            )
        }
    ) { paddingValues ->
        when (uiState) {
            is UiState.Loading -> {
                LoadingContent()
            }
            is UiState.Success -> {
                val forecast = (uiState as UiState.Success<Forecast>).data
                ForecastContent(
                    forecast = forecast,
                    expandedDays = expandedDays,
                    onDayToggle = viewModel::toggleDayExpanded,
                    modifier = Modifier.padding(paddingValues)
                )
            }
            is UiState.Error -> {
                ErrorContent(
                    message = (uiState as UiState.Error).message,
                    onRetry = { viewModel.retry(query) }
                )
            }
            is UiState.Idle -> { }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ForecastContent(
    forecast: Forecast,
    expandedDays: Set<String>,
    onDayToggle: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val todayForecast = forecast.forecastDays.firstOrNull()
    val upcomingDays = if (forecast.forecastDays.size > 1) {
        forecast.forecastDays.subList(1, forecast.forecastDays.size)
    } else {
        emptyList()
    }

    LazyColumn(
        modifier = modifier.fillMaxSize()
    ) {
        stickyHeader {
            CurrentWeatherHeader(forecast = forecast)
        }

        if (todayForecast != null) {
            item {
                Spacer(modifier = Modifier.height(16.dp))
                SectionTitle(
                    title = stringResource(id = R.string.today_hourly),
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                HourlyForecastRow(hours = todayForecast.hours)
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
                SunriseSunsetRow(
                    sunrise = todayForecast.sunrise,
                    sunset = todayForecast.sunset
                )
            }
        }

        if (upcomingDays.isNotEmpty()) {
            item {
                Spacer(modifier = Modifier.height(24.dp))
                SectionTitle(
                    title = stringResource(id = R.string.next_days),
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            items(
                items = upcomingDays,
                key = { it.date }
            ) { day ->
                ForecastDayAccordion(
                    forecastDay = day,
                    isExpanded = expandedDays.contains(day.date),
                    onToggle = { onDayToggle(day.date) },
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun CurrentWeatherHeader(forecast: Forecast) {
    val isLandscape = LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(BoldGradientStart, BoldGradientEnd).reversed()
                ),
                shape = RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp)
            )
            .padding(horizontal = 24.dp, vertical = 20.dp)
    ) {
        if (isLandscape) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                TemperatureInfo(forecast = forecast, modifier = Modifier.weight(1f))
                WeatherChips(forecast = forecast, modifier = Modifier.weight(1f))
            }
        } else {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TemperatureInfo(forecast = forecast)
                Spacer(modifier = Modifier.height(12.dp))
                WeatherChips(
                    forecast = forecast,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
private fun TemperatureInfo(
    forecast: Forecast,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = "${forecast.currentWeather.tempC.toInt()}°",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            AsyncImage(
                model = forecast.currentWeather.condition.iconUrl,
                contentDescription = forecast.currentWeather.condition.text,
                modifier = Modifier.size(32.dp)
            )
            Text(
                text = forecast.currentWeather.condition.text,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White.copy(alpha = 0.9f)
            )
        }
    }
}

@Composable
private fun WeatherChips(
    forecast: Forecast,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        WeatherInfoChip(
            label = stringResource(id = R.string.feels_like),
            value = "${forecast.currentWeather.feelsLikeC.toInt()}°"
        )
        WeatherInfoChip(
            label = stringResource(id = R.string.humidity),
            value = "${forecast.currentWeather.humidity}%"
        )
        WeatherInfoChip(
            label = stringResource(id = R.string.wind),
            value = "${forecast.currentWeather.windKph.toInt()} km/h"
        )
    }
}

@Composable
private fun WeatherInfoChip(
    label: String,
    value: String
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = Color.White.copy(alpha = 0.6f)
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            color = Color.White
        )
    }
}

@Composable
private fun SectionTitle(
    title: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onBackground,
        modifier = modifier
    )
}

@Composable
private fun LoadingContent() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
private fun ErrorContent(
    message: String,
    onRetry: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(32.dp)
        ) {
            Text(
                text = "😕",
                style = MaterialTheme.typography.headlineLarge
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = message,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(16.dp))
            androidx.compose.material3.TextButton(onClick = onRetry) {
                Text(text = stringResource(id = R.string.retry))
            }
        }
    }
}
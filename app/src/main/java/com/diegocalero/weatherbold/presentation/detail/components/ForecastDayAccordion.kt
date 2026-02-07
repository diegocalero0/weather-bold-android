package com.diegocalero.weatherbold.presentation.detail.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.diegocalero.weatherbold.R
import com.diegocalero.weatherbold.core.formatter.formatDayName
import com.diegocalero.weatherbold.domain.model.ForecastDay

@Composable
fun ForecastDayAccordion(
    forecastDay: ForecastDay,
    isExpanded: Boolean,
    onToggle: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val dayName =
        formatDayName(
            dateString = forecastDay.date,
            todayLabel = stringResource(id = R.string.today),
            tomorrowLabel = stringResource(id = R.string.tomorrow),
        )
    val expandLabel = stringResource(id = R.string.expand)
    val collapseLabel = stringResource(id = R.string.collapse)
    val avgTempLabel = stringResource(id = R.string.avg_temp)
    val minTempLabel = stringResource(id = R.string.min_temp)
    val maxTempLabel = stringResource(id = R.string.max_temp)
    val degreesLabel = stringResource(id = R.string.degrees)

    val dayAccordionContentDescription =
        buildString {
            append(dayName)
            append(", ${forecastDay.condition.text}, ")
            append("$avgTempLabel ${forecastDay.avgTempC.toInt()} $degreesLabel, ")
            append("$minTempLabel ${forecastDay.minTempC.toInt()}, ")
            append("$maxTempLabel ${forecastDay.maxTempC.toInt()} $degreesLabel")
        }

    Card(
        modifier =
            modifier
                .fillMaxWidth()
                .testTag("forecast_day_accordion")
                .semantics(mergeDescendants = true) {
                    stateDescription = if (isExpanded) expandLabel else collapseLabel
                    contentDescription = dayAccordionContentDescription
                    role = Role.Button
                },
        onClick = onToggle,
        shape = RoundedCornerShape(12.dp),
        colors =
            CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface,
            ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    ) {
        Column {
            DayHeader(
                forecastDay = forecastDay,
                isExpanded = isExpanded,
            )

            AnimatedVisibility(
                modifier = Modifier.testTag("accordion_expanded_content"),
                visible = isExpanded,
                enter = expandVertically(),
                exit = shrinkVertically(),
            ) {
                Column(
                    modifier = Modifier.padding(bottom = 12.dp),
                ) {
                    DayDetails(forecastDay = forecastDay)

                    Spacer(modifier = Modifier.height(12.dp))

                    HourlyForecastRow(hours = forecastDay.hours)
                }
            }
        }
    }
}

@Composable
private fun DayHeader(
    forecastDay: ForecastDay,
    isExpanded: Boolean,
) {
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AsyncImage(
            model = forecastDay.condition.iconUrl,
            contentDescription = forecastDay.condition.text,
            modifier = Modifier.size(40.dp),
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text =
                    formatDayName(
                        dateString = forecastDay.date,
                        todayLabel = stringResource(id = R.string.today),
                        tomorrowLabel = stringResource(id = R.string.tomorrow),
                    ),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface,
            )
            Text(
                text = forecastDay.condition.text,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
            )
        }

        Column(horizontalAlignment = Alignment.End) {
            Text(
                text = "${forecastDay.avgTempC.toInt()}°",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
            )
            Text(
                text = "${forecastDay.minTempC.toInt()}° / ${forecastDay.maxTempC.toInt()}°",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        Icon(
            imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
            contentDescription = if (isExpanded) stringResource(id = R.string.collapse) else stringResource(id = R.string.expand),
            tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
        )
    }
}

@Composable
private fun DayDetails(forecastDay: ForecastDay) {
    val detailsHeader = stringResource(id = R.string.forecast_details_header)
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .semantics {
                    heading()
                    contentDescription = detailsHeader
                },
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        DetailChip(
            label = stringResource(id = R.string.humidity),
            value = "${forecastDay.avgHumidity.toInt()}%",
        )
        DetailChip(
            label = stringResource(id = R.string.wind),
            value = "${forecastDay.maxWindKph.toInt()} km/h",
        )
        DetailChip(
            label = stringResource(id = R.string.rain),
            value = "${forecastDay.chanceOfRain.toInt()}%",
        )
    }
}

@Composable
private fun DetailChip(
    label: String,
    value: String,
) {
    Column(
        modifier =
            Modifier.semantics(mergeDescendants = true) {
                contentDescription = "$label: $value"
            },
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.primary,
        )
    }
}

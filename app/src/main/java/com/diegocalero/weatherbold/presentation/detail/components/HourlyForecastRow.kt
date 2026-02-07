package com.diegocalero.weatherbold.presentation.detail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.ScrollAxisRange
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.horizontalScrollAxisRange
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.diegocalero.weatherbold.R
import com.diegocalero.weatherbold.core.formatter.formatHour
import com.diegocalero.weatherbold.domain.model.HourForecast

@Composable
fun HourlyForecastRow(
    hours: List<HourForecast>,
    modifier: Modifier = Modifier,
) {
    val scrollDescription = stringResource(id = R.string.hourly_forecast_scroll)
    LazyRow(
        modifier =
            modifier.semantics {
                horizontalScrollAxisRange = ScrollAxisRange(value = { 0f }, maxValue = { 1f })
                contentDescription = scrollDescription
            },
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(
            items = hours,
            key = { it.time },
        ) { hour ->
            HourItem(hour = hour)
        }
    }
}

@Composable
private fun HourItem(hour: HourForecast) {
    Card(
        modifier =
            Modifier.semantics(mergeDescendants = true) {
                contentDescription =
                    "${formatHour(hour.time)}, ${hour.condition.text}, ${hour.tempC.toInt()} grados celsius"
            },
        shape = RoundedCornerShape(12.dp),
        colors =
            CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface,
            ),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
    ) {
        Column(
            modifier =
                Modifier
                    .width(72.dp)
                    .padding(vertical = 12.dp, horizontal = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = formatHour(hour.time),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
            )

            Spacer(modifier = Modifier.height(6.dp))

            AsyncImage(
                model = hour.condition.iconUrl,
                contentDescription = hour.condition.text,
                modifier = Modifier.size(36.dp),
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "${hour.tempC.toInt()}°",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface,
            )
        }
    }
}

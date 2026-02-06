package com.diegocalero.weatherbold.presentation.detail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.diegocalero.weatherbold.R
import com.diegocalero.weatherbold.core.ui.theme.SunriseEnd
import com.diegocalero.weatherbold.core.ui.theme.SunriseStart
import com.diegocalero.weatherbold.core.ui.theme.SunsetEnd
import com.diegocalero.weatherbold.core.ui.theme.SunsetStart

@Composable
fun SunriseSunsetRow(
    sunrise: String,
    sunset: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        SunCard(
            label = stringResource(id = R.string.sunrise),
            time = sunrise,
            gradient = Brush.horizontalGradient(listOf(SunriseStart, SunriseEnd)),
            modifier = Modifier.weight(1f)
        )
        SunCard(
            label = stringResource(id = R.string.sunset),
            time = sunset,
            gradient = Brush.horizontalGradient(listOf(SunsetStart, SunsetEnd)),
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun SunCard(
    label: String,
    time: String,
    gradient: Brush,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(gradient)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = Color.White.copy(alpha = 0.8f)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = time,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )
        }
    }
}

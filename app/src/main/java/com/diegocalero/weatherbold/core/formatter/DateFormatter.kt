package com.diegocalero.weatherbold.core.formatter

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

fun formatDayName(
    dateString: String,
    todayLabel: String,
    tomorrowLabel: String,
): String {
    return try {
        val date = LocalDate.parse(dateString, DateTimeFormatter.ISO_LOCAL_DATE)
        val today = LocalDate.now()
        when (date) {
            today -> todayLabel
            today.plusDays(1) -> tomorrowLabel
            else ->
                date.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault())
                    .replaceFirstChar { it.uppercase() }
        }
    } catch (e: Exception) {
        dateString
    }
}

fun formatHour(time: String): String {
    return try {
        val hour = time.substringAfter(" ").substringBefore(":")
        val hourInt = hour.toInt()
        when {
            hourInt == 0 -> "12 AM"
            hourInt < 12 -> "$hourInt AM"
            hourInt == 12 -> "12 PM"
            else -> "${hourInt - 12} PM"
        }
    } catch (e: Exception) {
        time
    }
}

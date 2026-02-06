package com.diegocalero.weatherbold.core.formatter

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
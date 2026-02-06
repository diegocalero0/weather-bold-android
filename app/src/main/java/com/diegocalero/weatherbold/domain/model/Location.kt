package com.diegocalero.weatherbold.domain.model

data class Location(
    val id: Int,
    val name: String,
    val region: String,
    val country: String,
    val lat: Double,
    val lon: Double
)
package com.diegocalero.weatherbold.navigation

sealed class Route(val route: String) {
    data object Splash : Route("splash")
    data object Search : Route("search")
    data object Detail : Route("detail/{query}") {
        fun createRouteByLatLng(latitude: Double, longitude: Double): String = "detail/$latitude,$longitude"
    }
}
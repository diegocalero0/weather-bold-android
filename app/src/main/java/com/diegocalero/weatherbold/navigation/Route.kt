package com.diegocalero.weatherbold.navigation

import android.net.Uri
import com.diegocalero.weatherbold.domain.model.Location

sealed class Route(val route: String) {
    data object Splash : Route("splash")

    data object Search : Route("search")

    data object Detail : Route("detail/{locationName}/{query}") {
        fun createRouteByLatLng(location: Location): String = "detail/${Uri.encode(location.name)}/${location.lat},${location.lon}"
    }
}

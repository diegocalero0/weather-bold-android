package com.diegocalero.weatherbold.data.mapper

import com.diegocalero.weatherbold.data.remote.dto.LocationDto
import com.diegocalero.weatherbold.domain.model.Location

fun LocationDto.toDomain(): Location {
    return Location(
        id = id,
        name = name,
        region = region,
        country = country,
        lat = lat,
        lon = lon,
    )
}

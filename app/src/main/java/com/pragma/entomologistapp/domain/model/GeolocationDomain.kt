package com.pragma.entomologistapp.domain.model

import com.pragma.entomologistapp.data.local.database.entity.GeolocationEntity

data class GeolocationDomain(
    val id: Int?,
    val latitude: Double,
    val longitude: Double,
    val city: String
) {

    fun toEntity() : GeolocationEntity = GeolocationEntity(
        this.id,
        this.latitude,
        this.longitude,
        this.city
    )

}
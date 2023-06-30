package com.pragma.entomologistapp.domain.repository

import com.pragma.entomologistapp.data.local.database.entity.GeolocationEntity

interface GeolocationRepository {

    suspend fun insertGeolocation(geolocationEntity: GeolocationEntity) : Long

}
package com.pragma.entomologistapp.data.datasource.interfaces.geolocation

import com.pragma.entomologistapp.data.local.database.entity.GeolocationEntity

interface GeolocationDataBaseDataSource {

    suspend fun insertGeolocation(geolocationEntity: GeolocationEntity) : Long

}
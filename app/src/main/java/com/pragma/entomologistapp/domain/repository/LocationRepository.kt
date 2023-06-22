package com.pragma.entomologistapp.domain.repository

interface LocationRepository {

    suspend fun checkLocationServicesEnabled() : Boolean

    suspend fun getCoordinatesAndPlaceName(): Triple<Double, Double, String>?

}
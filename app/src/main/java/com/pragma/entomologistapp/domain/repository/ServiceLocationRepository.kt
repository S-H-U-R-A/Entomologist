package com.pragma.entomologistapp.domain.repository

interface ServiceLocationRepository {

    suspend fun checkLocationServicesEnabled() : Boolean

    suspend fun getCoordinatesAndPlaceName(): Triple<Double, Double, String>?

}
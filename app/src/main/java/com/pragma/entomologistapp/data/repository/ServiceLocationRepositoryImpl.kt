package com.pragma.entomologistapp.data.repository


import android.location.Location
import com.pragma.entomologistapp.data.datasource.interfaces.AppDataSource
import com.pragma.entomologistapp.di.IoDispatcher
import com.pragma.entomologistapp.domain.repository.ServiceLocationRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject


class ServiceLocationRepositoryImpl @Inject constructor(
    private val appDataSource: AppDataSource,
    @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher
) : ServiceLocationRepository {

    override suspend fun checkLocationServicesEnabled(): Boolean {
        return withContext(coroutineDispatcher){
            appDataSource.checkLocationServicesEnabled()
        }
    }

    override suspend fun getCoordinatesAndPlaceName(): Triple<Double, Double, String>? =
        withContext(coroutineDispatcher) {
            //SE OBTIENEN LAS COORDENADAS DE LA LOCALIZACION
            val location: Location? = appDataSource.getLocationCoordinates()
            //SI TENEMOS UNA LOCALIZACIÓN
            if (location != null) {
                Triple(
                    location.latitude,
                    location.longitude,
                    appDataSource.getPlaceNameFromCoordinates(location)
                )
            } else {
                null
            }
        }


}


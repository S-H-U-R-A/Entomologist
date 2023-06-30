package com.pragma.entomologistapp.data.repository

import com.pragma.entomologistapp.data.datasource.interfaces.geolocation.GeolocationDataBaseDataSource
import com.pragma.entomologistapp.data.local.database.entity.GeolocationEntity
import com.pragma.entomologistapp.di.IoDispatcher
import com.pragma.entomologistapp.domain.repository.GeolocationRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject


class GeolocationRepositoryImpl @Inject constructor(
    private val geolocationDataBaseDataSource: GeolocationDataBaseDataSource,
    @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher
) : GeolocationRepository{

    override suspend fun insertGeolocation(geolocationEntity: GeolocationEntity): Long {
        return withContext(coroutineDispatcher){
            geolocationDataBaseDataSource.insertGeolocation(geolocationEntity)
        }
    }
}
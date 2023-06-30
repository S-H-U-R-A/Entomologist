package com.pragma.entomologistapp.data.datasource.geolocation

import com.pragma.entomologistapp.data.datasource.interfaces.geolocation.GeolocationDataBaseDataSource
import com.pragma.entomologistapp.data.local.database.dao.GeolocationDao
import com.pragma.entomologistapp.data.local.database.entity.GeolocationEntity
import javax.inject.Inject

class GeolocationDataBaseDataSourceImpl @Inject constructor(
    private val geolocationDao: GeolocationDao
) : GeolocationDataBaseDataSource{

    override suspend fun insertGeolocation(geolocationEntity: GeolocationEntity): Long {
        return geolocationDao.insertGeolocation(geolocationEntity)
    }

}
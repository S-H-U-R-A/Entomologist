package com.pragma.entomologistapp.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.pragma.entomologistapp.data.local.database.entity.GeolocationEntity

@Dao
interface GeolocationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGeolocation(geolocationEntity: GeolocationEntity): Long

}
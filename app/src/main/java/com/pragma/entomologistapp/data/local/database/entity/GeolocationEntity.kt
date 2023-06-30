package com.pragma.entomologistapp.data.local.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.pragma.entomologistapp.domain.model.GeolocationDomain
import java.util.Calendar

@Entity(
    tableName = GeolocationEntity.TABLE_NAME
)
data class GeolocationEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    @ColumnInfo(name = "latitude")
    val latitude: Double,
    @ColumnInfo(name = "longitude")
    val longitude: Double,
    @ColumnInfo(name = "city_name")
    val city: String,
){
    companion object{
        const val TABLE_NAME = "geolocation_table"
    }

    fun toDomain(): GeolocationDomain = GeolocationDomain(
        this.id,
        this.latitude,
        this.longitude,
        this.city
    )
}

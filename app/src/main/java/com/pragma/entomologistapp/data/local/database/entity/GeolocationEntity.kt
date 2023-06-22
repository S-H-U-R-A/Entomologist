package com.pragma.entomologistapp.data.local.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = GeolocationEntity.TABLE_NAME)
data class GeolocationEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "latitude")
    val latitude: Double,
    @ColumnInfo(name = "longitude")
    val longitude: Double,
    @ColumnInfo(name = "town")
    val town: String,
/*    @ColumnInfo(name = "Date")
    val date: Date*/
){
    companion object{
        const val TABLE_NAME = "geolocation_table"
    }
}

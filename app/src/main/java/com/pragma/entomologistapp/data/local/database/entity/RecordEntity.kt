package com.pragma.entomologistapp.data.local.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = RecordEntity.TABLE_NAME,
    indices = [
        Index(value = ["id_entomologist", "id_insect", "id_geolocation"] )
    ],
    foreignKeys = [
        ForeignKey(
            EntomologistEntity::class,
            parentColumns = ["id"],
            childColumns = ["id_entomologist"]
        ),
        ForeignKey(
            InsectEntity::class,
            parentColumns = ["id"],
            childColumns = ["id_insect"]
        ),
        ForeignKey(
            GeolocationEntity::class,
            parentColumns = ["id"],
            childColumns = ["id_geolocation"]
        )
    ]
)
data class RecordEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "comment")
    val comment: String,
    @ColumnInfo(name = "count_insect")
    val countInsect: Int,
    @ColumnInfo(name = "id_entomologist")
    val idEntomologist: Int,
    @ColumnInfo(name = "id_insect")
    val idInsect: Int,
    @ColumnInfo(name = "id_geolocation")
    val idGeolocation: Int
) {
    companion object {
        const val TABLE_NAME = "record_table"
    }
}

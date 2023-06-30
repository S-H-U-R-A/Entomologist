package com.pragma.entomologistapp.data.local.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.pragma.entomologistapp.domain.model.RecordDomain
import java.util.Calendar

@Entity(
    tableName = RecordEntity.TABLE_NAME,
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
    ],
    indices = [
        Index(value = ["id_entomologist"] ),
        Index(value = ["id_insect"]),
        Index(value = ["id_geolocation"])
    ]
)
data class RecordEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    @ColumnInfo(name = "comment")
    val comment: String,
    @ColumnInfo(name = "count_insect")
    val countInsect: Int,
    @ColumnInfo(name = "date")
    val date: Calendar,
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

    fun toEntity() : RecordDomain = RecordDomain(
        this.id,
        this.comment,
        this.countInsect,
        this.date,
        this.idEntomologist,
        this.idInsect,
        this.idGeolocation
    )

}

package com.pragma.entomologistapp.data.local.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.pragma.entomologistapp.domain.model.InsectDomain


@Entity(
    tableName = InsectEntity.TABLE_NAME
)
data class InsectEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "url_photo")
    val urlPhoto: String,
    @ColumnInfo(name = "more_information")
    val moreInfo: String
){
    companion object {
        const val TABLE_NAME = "insect_table"
    }

    fun toDomain(): InsectDomain {
        return InsectDomain(
            this.id,
            this.name,
            this.urlPhoto,
            this.moreInfo
        )
    }

}

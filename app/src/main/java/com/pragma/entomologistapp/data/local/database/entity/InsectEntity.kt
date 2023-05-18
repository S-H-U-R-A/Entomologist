package com.pragma.entomologistapp.data.local.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = InsectEntity.TABLE_NAME)
data class InsectEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
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
}

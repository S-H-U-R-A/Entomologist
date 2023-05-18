package com.pragma.entomologistapp.data.local.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = EntomologistEntity.TABLE_NAME)
data class EntomologistEntity(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "url_photo")
    val urlPhoto: String
){
    companion object{
        const val TABLE_NAME = "entomologist_table"
    }
}

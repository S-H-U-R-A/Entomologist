package com.pragma.entomologistapp.data.local.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(
    tableName = RecordEntity.TABLE_NAME,
)
data class RecordEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "comment")
    val comment: String,
    @ColumnInfo(name = "count")
    val count: Int
){
    companion object{
        const val TABLE_NAME = "record_table"
    }
}

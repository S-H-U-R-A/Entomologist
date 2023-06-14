package com.pragma.entomologistapp.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.pragma.entomologistapp.data.local.database.entity.InsectEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface InsectDao {

    @Query("SELECT * FROM ${InsectEntity.TABLE_NAME}")
    fun getAllInsects(): Flow<List<InsectEntity>>

    @Query("SELECT DISTINCT name FROM ${InsectEntity.TABLE_NAME}")
    fun getAllInsectsOnlyName(): Flow<List<String>>

    @Query("SELECT * FROM ${InsectEntity.TABLE_NAME} WHERE id = :id")
    suspend fun getInsectById(id: Int): InsectEntity

    @Transaction
    suspend fun insertAndGetInsect(insect: InsectEntity): InsectEntity {
        return getInsectById(
            insertInsect(insect).toInt()
        )
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInsect(insect: InsectEntity): Long

}
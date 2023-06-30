package com.pragma.entomologistapp.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Transaction
import com.pragma.entomologistapp.data.local.database.entity.GeolocationEntity
import com.pragma.entomologistapp.data.local.database.entity.RecordEntity

@Dao
interface RecordDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecord(recordEntity: RecordEntity)

}
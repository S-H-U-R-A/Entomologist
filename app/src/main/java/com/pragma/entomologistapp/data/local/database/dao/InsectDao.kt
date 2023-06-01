package com.pragma.entomologistapp.data.local.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.pragma.entomologistapp.data.local.database.entity.InsectEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface InsectDao {

    @Query("SELECT * FROM ${InsectEntity.TABLE_NAME}")
    fun getAllInsects(): Flow< List<InsectEntity> >

}
package com.pragma.entomologistapp.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pragma.entomologistapp.data.local.database.entity.EntomologistEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface EntomologistDao {

    @Query("SELECT * FROM ${EntomologistEntity.TABLE_NAME} WHERE id = :idEntomologist ")
    fun getEntomologist(idEntomologist: Int): Flow<EntomologistEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEntomologist(entomologist: EntomologistEntity)

}

package com.pragma.entomologistapp.domain.repository

import com.pragma.entomologistapp.data.local.database.dao.EntomologistDao
import com.pragma.entomologistapp.data.local.database.entity.EntomologistEntity
import kotlinx.coroutines.flow.Flow

interface EntomologistRepository {

    fun getPreferencesFirstTime() : Flow<Boolean>

    suspend fun savePreferencesFirstTime(data: Boolean) : Unit

    fun getEntomologist(id: Int): Flow<EntomologistEntity>

    suspend fun insertEntomologist(entomologist: EntomologistEntity)

}
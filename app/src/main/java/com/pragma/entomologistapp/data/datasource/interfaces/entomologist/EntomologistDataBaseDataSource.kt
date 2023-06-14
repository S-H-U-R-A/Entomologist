package com.pragma.entomologistapp.data.datasource.interfaces.entomologist

import com.pragma.entomologistapp.data.local.database.entity.EntomologistEntity
import kotlinx.coroutines.flow.Flow


interface EntomologistDataBaseDataSource {

    fun getEntomologist(id: Int) : Flow<EntomologistEntity>

    suspend fun insertEntomologist(entomologist: EntomologistEntity): Long

}
package com.pragma.entomologistapp.data.repository

import com.pragma.entomologistapp.data.datasource.interfaces.EntomologistDataBaseDataSource
import com.pragma.entomologistapp.data.datasource.interfaces.EntomologistDataStorageDataSource
import com.pragma.entomologistapp.data.local.database.dao.EntomologistDao
import com.pragma.entomologistapp.data.local.database.entity.EntomologistEntity
import com.pragma.entomologistapp.di.IoDispatcher
import com.pragma.entomologistapp.domain.repository.EntomologistRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class EntomologistRepositoryImpl @Inject constructor(
    private val entomologistDataStorageDataSource: EntomologistDataStorageDataSource,
    private val entomologistDataBaseDataSource: EntomologistDataBaseDataSource,
    @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher
) : EntomologistRepository {

    override fun getPreferencesFirstTime(): Flow<Boolean> =
        entomologistDataStorageDataSource
            .getPreferencesFirstTime()
            .flowOn(coroutineDispatcher)

    override suspend fun savePreferencesFirstTime(data: Boolean) {
        withContext( coroutineDispatcher ){
            entomologistDataStorageDataSource.savePreferencesFirstTime(data)
        }
    }

    override fun getEntomologist(id: Int): Flow<EntomologistEntity> {
        TODO("Not yet implemented")
    }

    override suspend fun insertEntomologist(entomologist: EntomologistEntity) {
        withContext(coroutineDispatcher){
            entomologistDataBaseDataSource.insertEntomologist(entomologist)
        }
    }




}
package com.pragma.entomologistapp.data.repository

import com.pragma.entomologistapp.data.datasource.interfaces.record.RecordDataBaseDataSource
import com.pragma.entomologistapp.data.local.database.entity.RecordEntity
import com.pragma.entomologistapp.di.IoDispatcher
import com.pragma.entomologistapp.domain.repository.RecordRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RecordRepositoryImpl @Inject constructor(
    private val recordDataBaseDataSource: RecordDataBaseDataSource,
    @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher
) : RecordRepository {

    override suspend fun insertRecord(recordEntity: RecordEntity) {
        withContext(coroutineDispatcher) {
            recordDataBaseDataSource.insertRecord(recordEntity)
        }
    }

    override suspend fun updateRecord(recordEntity: RecordEntity) {
        withContext(coroutineDispatcher){
            recordDataBaseDataSource.updateRecord(recordEntity)
        }
    }

}
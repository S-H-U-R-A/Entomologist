package com.pragma.entomologistapp.data.repository

import com.pragma.entomologistapp.data.datasource.interfaces.recordInsectGeolocation.RecordInsectGeolocationDataBaseDataSource
import com.pragma.entomologistapp.di.IoDispatcher
import com.pragma.entomologistapp.domain.model.RecordInsectGeolocationDomain
import com.pragma.entomologistapp.domain.repository.RecordInsectGeolocationRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RecordInsectGeolocationRepositoryImpl @Inject constructor(
    private val recordInsectGeolocationDataBaseDataSource: RecordInsectGeolocationDataBaseDataSource,
    @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher
) : RecordInsectGeolocationRepository {

    override fun loadRecordWithInsectAndGeolocation(): Flow< List<RecordInsectGeolocationDomain> > {
        return recordInsectGeolocationDataBaseDataSource.loadRecordWithInsectAndGeolocation().map { listRecords ->
            listRecords.map {
                it.toDomain()
            }
        }.flowOn(coroutineDispatcher)
    }

}
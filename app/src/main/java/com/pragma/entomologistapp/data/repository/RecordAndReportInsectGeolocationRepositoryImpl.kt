package com.pragma.entomologistapp.data.repository

import com.pragma.entomologistapp.data.datasource.interfaces.recordInsectGeolocation.RecordAndReportInsectGeolocationDataBaseDataSource
import com.pragma.entomologistapp.di.IoDispatcher
import com.pragma.entomologistapp.domain.model.RecordInsectGeolocationDomain
import com.pragma.entomologistapp.domain.model.ReportInsectBySpeciesDomain
import com.pragma.entomologistapp.domain.repository.RecordAndReportInsectGeolocationRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RecordAndReportInsectGeolocationRepositoryImpl @Inject constructor(
    private val recordAndReportInsectGeolocationDataBaseDataSource: RecordAndReportInsectGeolocationDataBaseDataSource,
    @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher
) : RecordAndReportInsectGeolocationRepository {

    override fun loadRecordWithInsectAndGeolocation(): Flow< List<RecordInsectGeolocationDomain> > {
        return recordAndReportInsectGeolocationDataBaseDataSource.loadRecordWithInsectAndGeolocation().map { listRecords ->
            listRecords.map {
                it.toDomain()
            }
        }.flowOn(coroutineDispatcher)
    }

    override fun loadReportWithInsectAndGeolocation(): Flow<List<ReportInsectBySpeciesDomain>> {
        return recordAndReportInsectGeolocationDataBaseDataSource.loadReportWithInsectAndGeolocation().map { listReports ->
            listReports.map {
                it.toDomain()
            }
        }
    }

}
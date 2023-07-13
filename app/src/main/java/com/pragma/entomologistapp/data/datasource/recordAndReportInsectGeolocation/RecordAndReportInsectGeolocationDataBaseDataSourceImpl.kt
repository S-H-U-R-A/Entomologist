package com.pragma.entomologistapp.data.datasource.recordAndReportInsectGeolocation

import com.pragma.entomologistapp.data.datasource.interfaces.recordInsectGeolocation.RecordAndReportInsectGeolocationDataBaseDataSource
import com.pragma.entomologistapp.data.local.database.dao.RecordAndReportInsectGeolocationDao
import com.pragma.entomologistapp.data.local.database.entity.customResponses.RecordInsectGeolocation
import com.pragma.entomologistapp.data.local.database.entity.customResponses.ReportInsectBySpecies
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RecordAndReportInsectGeolocationDataBaseDataSourceImpl @Inject constructor(
    private val recordAndReportInsectGeolocationDao: RecordAndReportInsectGeolocationDao
) : RecordAndReportInsectGeolocationDataBaseDataSource {

    override fun loadRecordWithInsectAndGeolocation(): Flow< List<RecordInsectGeolocation> > {
        return recordAndReportInsectGeolocationDao.loadRecordWithInsectAndGeolocation()
    }

    override fun loadReportWithInsectAndGeolocation(): Flow<List<ReportInsectBySpecies>> {
        return recordAndReportInsectGeolocationDao.loadReportWithInsectAndGeolocation()
    }

}
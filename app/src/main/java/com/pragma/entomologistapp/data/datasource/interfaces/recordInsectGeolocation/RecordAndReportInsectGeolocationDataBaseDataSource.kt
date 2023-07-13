package com.pragma.entomologistapp.data.datasource.interfaces.recordInsectGeolocation

import com.pragma.entomologistapp.data.local.database.entity.customResponses.RecordInsectGeolocation
import com.pragma.entomologistapp.data.local.database.entity.customResponses.ReportInsectBySpecies
import kotlinx.coroutines.flow.Flow

interface RecordAndReportInsectGeolocationDataBaseDataSource {
    fun loadRecordWithInsectAndGeolocation(): Flow<List<RecordInsectGeolocation>>

    fun loadReportWithInsectAndGeolocation() : Flow<List<ReportInsectBySpecies>>
}
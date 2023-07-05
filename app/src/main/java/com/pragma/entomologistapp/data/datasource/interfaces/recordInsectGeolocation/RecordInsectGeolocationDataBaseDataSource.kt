package com.pragma.entomologistapp.data.datasource.interfaces.recordInsectGeolocation

import com.pragma.entomologistapp.data.local.database.entity.customResponses.RecordInsectGeolocation
import kotlinx.coroutines.flow.Flow

interface RecordInsectGeolocationDataBaseDataSource {
    fun loadRecordWithInsectAndGeolocation(): Flow<List<RecordInsectGeolocation>>
}
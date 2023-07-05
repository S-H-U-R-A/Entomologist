package com.pragma.entomologistapp.domain.repository

import com.pragma.entomologistapp.data.local.database.entity.customResponses.RecordInsectGeolocation
import com.pragma.entomologistapp.domain.model.RecordInsectGeolocationDomain
import kotlinx.coroutines.flow.Flow

interface RecordInsectGeolocationRepository {

    fun loadRecordWithInsectAndGeolocation(): Flow<List<RecordInsectGeolocationDomain>>

}
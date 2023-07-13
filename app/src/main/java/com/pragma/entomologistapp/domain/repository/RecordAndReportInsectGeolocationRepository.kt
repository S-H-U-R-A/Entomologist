package com.pragma.entomologistapp.domain.repository

import com.pragma.entomologistapp.domain.model.RecordInsectGeolocationDomain
import com.pragma.entomologistapp.domain.model.ReportInsectBySpeciesDomain
import kotlinx.coroutines.flow.Flow

interface RecordAndReportInsectGeolocationRepository {

    fun loadRecordWithInsectAndGeolocation(): Flow< List<RecordInsectGeolocationDomain>>

    fun loadReportWithInsectAndGeolocation() : Flow< List<ReportInsectBySpeciesDomain> >

}
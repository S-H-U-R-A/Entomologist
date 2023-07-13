package com.pragma.entomologistapp.domain.usecases.recordAndReportInsectGeolocation

import com.pragma.entomologistapp.domain.model.RecordInsectGeolocationDomain
import com.pragma.entomologistapp.domain.repository.RecordAndReportInsectGeolocationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoadRecordWithInsectAndGeolocationUseCase @Inject constructor(
    private val recordAndReportInsectGeolocationRepository: RecordAndReportInsectGeolocationRepository
) {
    operator fun invoke() : Flow< List<RecordInsectGeolocationDomain> >{
        return recordAndReportInsectGeolocationRepository.loadRecordWithInsectAndGeolocation()
    }
}
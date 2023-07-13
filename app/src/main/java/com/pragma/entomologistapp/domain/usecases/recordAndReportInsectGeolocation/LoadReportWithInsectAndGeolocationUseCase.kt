package com.pragma.entomologistapp.domain.usecases.recordAndReportInsectGeolocation

import com.pragma.entomologistapp.domain.model.ReportInsectBySpeciesDomain
import com.pragma.entomologistapp.domain.repository.RecordAndReportInsectGeolocationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoadReportWithInsectAndGeolocationUseCase @Inject constructor(
    private val recordAndReportInsectGeolocationRepository: RecordAndReportInsectGeolocationRepository
) {
    operator fun invoke() : Flow<List<ReportInsectBySpeciesDomain>> {
        return recordAndReportInsectGeolocationRepository.loadReportWithInsectAndGeolocation()
    }
}
package com.pragma.entomologistapp.domain.usecases.recordInsectGeolocation

import com.pragma.entomologistapp.domain.model.RecordInsectGeolocationDomain
import com.pragma.entomologistapp.domain.repository.RecordInsectGeolocationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoadRecordWithInsectAndGeolocationUseCase @Inject constructor(
    private val recordInsectGeolocationRepository: RecordInsectGeolocationRepository
) {

    operator fun invoke() : Flow< List<RecordInsectGeolocationDomain> >{
        return recordInsectGeolocationRepository.loadRecordWithInsectAndGeolocation()
    }

}
package com.pragma.entomologistapp.domain.usecases.record

import com.pragma.entomologistapp.domain.model.RecordDomain
import com.pragma.entomologistapp.domain.repository.RecordRepository
import javax.inject.Inject

class SaveRecordDataBaseUseCase @Inject constructor(
    private val recordRepository: RecordRepository
) {

    suspend operator fun invoke(recordDomain: RecordDomain){
        recordRepository.insertRecord( recordDomain.toEntity() )
    }

}
package com.pragma.entomologistapp.domain.usecases.entomologist

import com.pragma.entomologistapp.domain.model.EntomologistDomain
import com.pragma.entomologistapp.domain.repository.EntomologistRepository
import javax.inject.Inject


class saveEntomologistDataBaseUseCase @Inject constructor(
    private val entomologistRepository: EntomologistRepository
) {
    suspend operator fun invoke(entomologist: EntomologistDomain){
        entomologistRepository.insertEntomologist(
            entomologist.toEntity()
        )
    }

}
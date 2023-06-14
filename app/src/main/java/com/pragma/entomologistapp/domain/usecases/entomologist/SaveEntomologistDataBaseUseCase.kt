package com.pragma.entomologistapp.domain.usecases.entomologist

import com.pragma.entomologistapp.domain.model.EntomologistDomain
import com.pragma.entomologistapp.domain.repository.EntomologistRepository
import javax.inject.Inject


class SaveEntomologistDataBaseUseCase @Inject constructor(
    private val entomologistRepository: EntomologistRepository
) {
    suspend operator fun invoke(entomologist: EntomologistDomain): Long{
        return entomologistRepository.insertEntomologist(
            entomologist.toEntity()
        )
    }

}
package com.pragma.entomologistapp.domain.usecases.entomologist

import com.pragma.entomologistapp.domain.model.EntomologistDomain
import com.pragma.entomologistapp.domain.repository.EntomologistRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetEntomologistDataBaseUseCase @Inject constructor(
    private val entomologistRepository: EntomologistRepository
) {

    operator fun invoke(id: Int) : Flow<EntomologistDomain> {
        return entomologistRepository.getEntomologist(id)
    }

}
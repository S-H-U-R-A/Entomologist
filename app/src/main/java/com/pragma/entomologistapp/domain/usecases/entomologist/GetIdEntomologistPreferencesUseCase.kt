package com.pragma.entomologistapp.domain.usecases.entomologist

import com.pragma.entomologistapp.domain.repository.EntomologistRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetIdEntomologistPreferencesUseCase @Inject constructor(
    private val entomologistRepository: EntomologistRepository
) {

    operator fun invoke(): Flow<Long> {
        return entomologistRepository.getPreferencesIdUser()
    }

}
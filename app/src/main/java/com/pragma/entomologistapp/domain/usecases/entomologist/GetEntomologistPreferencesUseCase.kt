package com.pragma.entomologistapp.domain.usecases.entomologist

import com.pragma.entomologistapp.domain.repository.EntomologistRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetEntomologistPreferencesUseCase @Inject constructor(
    private val entomologistRepository: EntomologistRepository
) {

    operator fun invoke() : Flow<Boolean> = entomologistRepository.getPreferencesFirstTime()

}
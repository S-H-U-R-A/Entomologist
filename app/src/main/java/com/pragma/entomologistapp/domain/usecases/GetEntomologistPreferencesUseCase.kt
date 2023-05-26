package com.pragma.entomologistapp.domain.usecases

import com.pragma.entomologistapp.domain.repository.EntomologistRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetEntomologistPreferencesUseCase @Inject constructor(
    val entomologistRepository: EntomologistRepository
) {

    operator fun invoke() : Flow<Boolean> = entomologistRepository.getPreferencesFirstTime()

}
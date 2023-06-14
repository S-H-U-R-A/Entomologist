package com.pragma.entomologistapp.domain.usecases.entomologist

import com.pragma.entomologistapp.domain.repository.EntomologistRepository
import javax.inject.Inject


class SaveIdEntomologistPreferencesUseCase @Inject constructor(
    private val entomologistRepository: EntomologistRepository
) {

    suspend operator fun invoke(id: Long){
        entomologistRepository.savePreferencesIdUser(id)
    }

}
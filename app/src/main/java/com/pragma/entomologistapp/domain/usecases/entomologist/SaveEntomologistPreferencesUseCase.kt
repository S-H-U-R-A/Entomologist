package com.pragma.entomologistapp.domain.usecases.entomologist

import com.pragma.entomologistapp.domain.repository.EntomologistRepository
import javax.inject.Inject

class SaveEntomologistPreferencesUseCase @Inject constructor (
    private val entomologistRepositoryImpl: EntomologistRepository
) {

    suspend operator fun invoke(data: Boolean){
        entomologistRepositoryImpl.savePreferencesFirstTime(data)
    }

}
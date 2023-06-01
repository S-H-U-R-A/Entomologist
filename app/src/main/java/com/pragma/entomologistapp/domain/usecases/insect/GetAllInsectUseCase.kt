package com.pragma.entomologistapp.domain.usecases.insect

import com.pragma.entomologistapp.domain.model.InsectDomain
import com.pragma.entomologistapp.domain.repository.InsectRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllInsectUseCase @Inject constructor(
    private val insectRepository: InsectRepository
) {

    operator fun invoke() : Flow<List<InsectDomain>>{
        return insectRepository.getAllInsects()
    }

}
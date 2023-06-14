package com.pragma.entomologistapp.domain.usecases.insect

import com.pragma.entomologistapp.data.local.database.entity.InsectEntity
import com.pragma.entomologistapp.domain.model.InsectDomain
import com.pragma.entomologistapp.domain.repository.InsectRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class SaveInsectDataBaseUseCase @Inject constructor(
    private val insectRepository: InsectRepository
) {
    suspend operator fun invoke(insect: InsectDomain ): InsectDomain  {
        return insectRepository.insertAndGetInsect( insect.toEntity() )
    }
}
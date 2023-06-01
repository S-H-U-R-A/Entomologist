package com.pragma.entomologistapp.data.repository

import com.pragma.entomologistapp.data.datasource.interfaces.insect.InsectDataBaseDataSource
import com.pragma.entomologistapp.data.local.database.entity.InsectEntity
import com.pragma.entomologistapp.di.IoDispatcher
import com.pragma.entomologistapp.domain.model.InsectDomain
import com.pragma.entomologistapp.domain.repository.InsectRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class InsectRepositoryImpl @Inject constructor(
    private val insectDataBaseDataSource: InsectDataBaseDataSource,
    @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher
) : InsectRepository {

    override fun getAllInsects(): Flow< List<InsectDomain>> {
        return insectDataBaseDataSource
            .getAllInsects()
            .map { insectList ->
                insectList.map {
                    it.toDomain()
                }
            }
            .flowOn(coroutineDispatcher)
    }

}
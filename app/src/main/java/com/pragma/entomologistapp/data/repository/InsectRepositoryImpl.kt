package com.pragma.entomologistapp.data.repository

import android.net.Uri
import com.pragma.entomologistapp.core.TypeUser
import com.pragma.entomologistapp.data.datasource.interfaces.insect.InsectDataBaseDataSource
import com.pragma.entomologistapp.data.local.database.entity.InsectEntity
import com.pragma.entomologistapp.di.IoDispatcher
import com.pragma.entomologistapp.domain.model.InsectDomain
import com.pragma.entomologistapp.domain.repository.ImagesRepository
import com.pragma.entomologistapp.domain.repository.InsectRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class InsectRepositoryImpl @Inject constructor(
    private val insectDataBaseDataSource: InsectDataBaseDataSource,
    private val imagesRepository: ImagesRepository,
    @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher
) : InsectRepository {
    override suspend fun savePhotoInExternalStorage(
        uri: Uri,
        type: TypeUser,
        nameInsect: String?
    ): String? {
        return withContext(coroutineDispatcher) {
            imagesRepository.savePhotoInExternalStorage(
                uri,
                type,
                nameInsect
            )
        }
    }

    override fun getAllInsects(): Flow< List<InsectDomain> > {
        return insectDataBaseDataSource.getAllInsects().map { listInsectEntity ->
            listInsectEntity.map {
                it.toDomain()
            }
        }.flowOn(coroutineDispatcher)
    }

    override suspend fun insertAndGetInsect(insect: InsectEntity): InsectDomain {
        return insectDataBaseDataSource.insertAndGetInsect(insect).toDomain()
    }

}
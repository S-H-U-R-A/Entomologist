package com.pragma.entomologistapp.data.repository

import android.net.Uri
import com.pragma.entomologistapp.core.TypeUser
import com.pragma.entomologistapp.data.datasource.interfaces.entomologist.EntomologistDataBaseDataSource
import com.pragma.entomologistapp.data.datasource.interfaces.entomologist.EntomologistDataStorageDataSource
import com.pragma.entomologistapp.data.local.database.entity.EntomologistEntity
import com.pragma.entomologistapp.di.IoDispatcher
import com.pragma.entomologistapp.domain.repository.EntomologistRepository
import com.pragma.entomologistapp.domain.repository.ImagesRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class EntomologistRepositoryImpl @Inject constructor(
    private val entomologistDataStorageDataSource: EntomologistDataStorageDataSource,
    private val entomologistDataBaseDataSource: EntomologistDataBaseDataSource,
    private val imagesRepository: ImagesRepository,
    @IoDispatcher private val coroutineDispatcherIo: CoroutineDispatcher
) : EntomologistRepository {
    override suspend fun savePhotoInExternalStorage(
        uri: Uri,
        type: TypeUser,
        nameInsect: String?
    ): String? {
        return imagesRepository.savePhotoInExternalStorage(
            uri,
            type,
            nameInsect
        )
    }


    override fun getPreferencesFirstTime(): Flow<Boolean> =
        entomologistDataStorageDataSource
            .getPreferencesFirstTime()
            .flowOn(coroutineDispatcherIo)

    override suspend fun savePreferencesFirstTime(data: Boolean) {
        withContext(coroutineDispatcherIo){
            entomologistDataStorageDataSource.savePreferencesFirstTime(data)
        }
    }

    override fun getEntomologist(id: Int): Flow<EntomologistEntity> {
        TODO("Not yet implemented")
    }

    override suspend fun insertEntomologist(entomologist: EntomologistEntity) {
        withContext(coroutineDispatcherIo){
            entomologistDataBaseDataSource.insertEntomologist(entomologist)
        }
    }




}
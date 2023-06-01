package com.pragma.entomologistapp.data.repository

import android.net.Uri
import com.pragma.entomologistapp.core.TypeUser
import com.pragma.entomologistapp.data.datasource.interfaces.AppStorageDataSource
import com.pragma.entomologistapp.di.IoDispatcher
import com.pragma.entomologistapp.domain.repository.ImagesRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class ImageRepositoryImpl @Inject constructor(
    private val appStorageDataSourceImpl: AppStorageDataSource,
    @IoDispatcher private val dispatcherIO: CoroutineDispatcher
) : ImagesRepository {

    override suspend fun savePhotoInExternalStorage(
        uri: Uri,
        type: TypeUser,
        nameInsect: String?
    ): String? = withContext(dispatcherIO){
        appStorageDataSourceImpl.savePhotoInExternalStorage(
            uri,
            type,
            nameInsect
        )
    }


}
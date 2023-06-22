package com.pragma.entomologistapp.data.repository

import android.net.Uri
import com.pragma.entomologistapp.core.TypeUser
import com.pragma.entomologistapp.data.datasource.interfaces.AppDataSource
import com.pragma.entomologistapp.di.IoDispatcher
import com.pragma.entomologistapp.domain.repository.ImagesRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject


class ImageRepositoryImpl @Inject constructor(
    private val appDataSourceImpl: AppDataSource,
    @IoDispatcher private val dispatcherIO: CoroutineDispatcher
) : ImagesRepository {

    override suspend fun savePhotoInExternalStorage(
        uri: Uri,
        type: TypeUser,
        nameInsect: String?
    ): String? = appDataSourceImpl.savePhotoInExternalStorage(
        uri,
        type,
        nameInsect
    )


}
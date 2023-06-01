package com.pragma.entomologistapp.data.datasource

import android.content.Context
import android.net.Uri
import com.pragma.entomologistapp.core.TypeUser
import com.pragma.entomologistapp.core.ext.savePhotoInExternalStorage
import com.pragma.entomologistapp.core.ext.toBitmap
import com.pragma.entomologistapp.data.datasource.interfaces.AppStorageDataSource
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AppStorageDataSourceImpl @Inject constructor(
     @ApplicationContext val context: Context
) : AppStorageDataSource {

    override suspend fun savePhotoInExternalStorage(
        uriImage: Uri,
        type: TypeUser,
        nameInsect:String?
    ): String? {
        return uriImage.toBitmap(context)?.savePhotoInExternalStorage(
            context,
            type,
            nameInsect
        )
    }



}
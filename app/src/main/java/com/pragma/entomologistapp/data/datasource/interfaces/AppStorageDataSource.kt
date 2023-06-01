package com.pragma.entomologistapp.data.datasource.interfaces

import android.net.Uri
import com.pragma.entomologistapp.core.TypeUser
import java.io.File


interface AppStorageDataSource {

    suspend fun savePhotoInExternalStorage(
        uriImage: Uri,
        type: TypeUser,
        nameInsect:String?
    ): String?

}
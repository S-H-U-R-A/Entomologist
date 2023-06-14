package com.pragma.entomologistapp.domain.repository

import android.net.Uri
import com.pragma.entomologistapp.core.TypeUser

interface ImagesRepository {

    suspend fun savePhotoInExternalStorage(
        uri: Uri,
        type: TypeUser,
        nameInsect:String?
    ): String?

}
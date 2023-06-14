package com.pragma.entomologistapp.domain.repository

import android.net.Uri
import com.pragma.entomologistapp.core.TypeUser
import com.pragma.entomologistapp.data.local.database.entity.InsectEntity
import com.pragma.entomologistapp.domain.model.InsectDomain
import kotlinx.coroutines.flow.Flow

interface InsectRepository {

    suspend fun savePhotoInExternalStorage(
        uri: Uri,
        type: TypeUser,
        nameInsect:String?
    ) : String?

    fun getAllInsectsOnlyName(): Flow< List<String> >

    suspend fun insertAndGetInsect(insect: InsectEntity): InsectDomain

}
package com.pragma.entomologistapp.domain.repository

import android.net.Uri
import com.pragma.entomologistapp.core.TypeUser
import com.pragma.entomologistapp.data.local.database.entity.EntomologistEntity
import com.pragma.entomologistapp.domain.model.EntomologistDomain
import kotlinx.coroutines.flow.Flow

interface EntomologistRepository {

    suspend fun savePhotoInExternalStorage(
        uri: Uri,
        type: TypeUser,
        nameInsect:String?
    ) : String?

    fun getPreferencesFirstTime() : Flow<Boolean>

    suspend fun savePreferencesFirstTime(data: Boolean)

    fun getPreferencesIdUser() : Flow<Long>

    suspend fun savePreferencesIdUser(id: Long)

    fun getEntomologist(id: Int): Flow<EntomologistDomain>

    suspend fun insertEntomologist(entomologist: EntomologistEntity): Long

}
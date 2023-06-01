package com.pragma.entomologistapp.domain.usecases.entomologist

import android.net.Uri
import com.pragma.entomologistapp.core.TypeUser
import com.pragma.entomologistapp.domain.repository.EntomologistRepository
import javax.inject.Inject

class SaveImageEntomologistAppUseCase @Inject constructor(
    private val entomologistRepository: EntomologistRepository
) {
    suspend operator fun invoke(
        uri: Uri,
        type: TypeUser,
        nameInsect: String?
    ): String?{
        return entomologistRepository.savePhotoInExternalStorage(
            uri,
            type,
            nameInsect
        )
    }
}
package com.pragma.entomologistapp.domain.usecases.insect

import android.net.Uri
import com.pragma.entomologistapp.core.TypeUser
import com.pragma.entomologistapp.domain.repository.InsectRepository
import javax.inject.Inject

class SaveImageInsectAppUseCase @Inject constructor(
    private val insectRepository: InsectRepository
) {
    suspend operator fun invoke(
        uri: Uri,
        type: TypeUser,
        nameInsect: String?
    ): String?{
        return insectRepository.savePhotoInExternalStorage(
            uri,
            type,
            nameInsect
        )
    }
}
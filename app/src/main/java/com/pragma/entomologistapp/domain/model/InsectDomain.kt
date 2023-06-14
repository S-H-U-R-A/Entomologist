package com.pragma.entomologistapp.domain.model

import android.os.Parcelable
import com.pragma.entomologistapp.data.local.database.entity.InsectEntity
import kotlinx.parcelize.Parcelize

@Parcelize
data class InsectDomain(
    val id: Int?,
    val name: String,
    val urlPhoto: String,
    val moreInfo: String
): Parcelable {

    companion object {
        const val IMAGE_DEFAULT = "WITHOUT PHOTO"
    }

    fun toEntity(): InsectEntity {
        return InsectEntity(
            this.id,
            this.name,
            this.urlPhoto,
            this.moreInfo
        )
    }

}
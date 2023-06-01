package com.pragma.entomologistapp.domain.model

import com.pragma.entomologistapp.data.local.database.entity.InsectEntity

data class InsectDomain(
    val id: Int?,
    val name: String,
    val urlPhoto: String,
    val moreInfo: String
) {

    fun toEntity(): InsectEntity {
        return InsectEntity(
            this.id,
            this.name,
            this.urlPhoto,
            this.moreInfo
        )
    }

}
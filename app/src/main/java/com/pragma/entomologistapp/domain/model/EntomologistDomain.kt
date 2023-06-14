package com.pragma.entomologistapp.domain.model

import com.pragma.entomologistapp.data.local.database.entity.EntomologistEntity

data class EntomologistDomain(
    val id : Int?,
    val name: String,
    val urlPhoto: String
){

    companion object {
        const val IMAGE_DEFAULT = "WITHOUT PHOTO"
    }

    fun toEntity(): EntomologistEntity{
        return EntomologistEntity(
            this.id,
            this.name,
            this.urlPhoto
        )
    }

}
package com.pragma.entomologistapp.domain.model

import com.pragma.entomologistapp.data.local.database.entity.RecordEntity
import java.util.Calendar

data class RecordDomain(
    val id: Int?,
    val comment: String,
    val countInsect: Int,
    val date: Calendar,
    val idEntomologist: Int,
    val idInsect: Int,
    val idGeolocation: Int
){
    fun toEntity() : RecordEntity = RecordEntity(
        this.id,
        this.comment,
        this.countInsect,
        this.date,
        this.idEntomologist,
        this.idInsect,
        this.idGeolocation
    )
}

package com.pragma.entomologistapp.data.local.database.entity.customResponses

import com.pragma.entomologistapp.core.FormatDateEntomologist.*
import com.pragma.entomologistapp.core.ext.toStringWithFormat
import com.pragma.entomologistapp.domain.model.ReportInsectBySpeciesDomain
import java.util.Calendar

data class ReportInsectBySpecies(
    val idInsect: Int,
    val nameInsect: String,
    val photoInsect: String,
    val countInsectBySpecie: Int,
    val cityCount: Int,
    val dateInitialRecord: Calendar,
    val dateEndRecord: Calendar
){
    fun toDomain(): ReportInsectBySpeciesDomain = ReportInsectBySpeciesDomain(
        this.idInsect,
        this.nameInsect,
        this.photoInsect,
        this.countInsectBySpecie,
        this.cityCount,
        this.dateInitialRecord.toStringWithFormat(DD_MM_YYYY.format),
        this.dateEndRecord.toStringWithFormat(DD_MM_YYYY.format)
    )
}
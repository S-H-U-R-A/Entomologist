package com.pragma.entomologistapp.domain.model

import com.pragma.entomologistapp.core.FormatDateEntomologist.DD_MM_YYYY
import com.pragma.entomologistapp.core.ext.toCalendar
import com.pragma.entomologistapp.data.local.database.entity.customResponses.ReportInsectBySpecies

data class ReportInsectBySpeciesDomain(
    val idInsect: Int,
    val nameInsect: String,
    val photoInsect: String,
    val countInsectBySpecie: Int,
    val cityCount: Int,
    val dateInitialRecord: String,
    val dateEndRecord: String
) {
    fun toDataLayer(): ReportInsectBySpecies = ReportInsectBySpecies(
        this.idInsect,
        this.nameInsect,
        this.photoInsect,
        this.countInsectBySpecie,
        this.cityCount,
        this.dateInitialRecord.toCalendar(DD_MM_YYYY.format),
        this.dateEndRecord.toCalendar(DD_MM_YYYY.format)
    )
}
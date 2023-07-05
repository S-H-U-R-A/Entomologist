package com.pragma.entomologistapp.domain.model

import com.pragma.entomologistapp.core.FormatDateEntomologist.*
import com.pragma.entomologistapp.core.ext.toCalendar
import com.pragma.entomologistapp.data.local.database.entity.customResponses.RecordInsectGeolocation

data class RecordInsectGeolocationDomain(
    val idRecord: Int,
    val countInsect: Int,
    val dateRecord: String,
    val nameInsect: String,
    val photoInsect: String,
    val cityName: String,
){
    fun toDataLayer() : RecordInsectGeolocation = RecordInsectGeolocation(
        this.idRecord,
        this.countInsect,
        this.dateRecord.toCalendar( DD_MM_YYYY.format ),
        this.nameInsect,
        this.photoInsect,
        this.cityName
    )
}

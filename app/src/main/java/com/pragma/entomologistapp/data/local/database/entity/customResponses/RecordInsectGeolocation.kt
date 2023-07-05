package com.pragma.entomologistapp.data.local.database.entity.customResponses

import com.pragma.entomologistapp.core.FormatDateEntomologist.DD_MM_YYYY
import com.pragma.entomologistapp.core.ext.toStringWithFormat
import com.pragma.entomologistapp.domain.model.RecordInsectGeolocationDomain
import java.util.Calendar

data class RecordInsectGeolocation(
    val idRecord: Int,
    val countInsect: Int,
    val dateRecord: Calendar,
    val nameInsect: String,
    val photoInsect: String,
    val cityName: String,
){
    fun toDomain() : RecordInsectGeolocationDomain = RecordInsectGeolocationDomain(
        this.idRecord,
        this.countInsect,
        this.dateRecord.toStringWithFormat( DD_MM_YYYY.format ),
        this.nameInsect,
        this.photoInsect,
        this.cityName
    )
}
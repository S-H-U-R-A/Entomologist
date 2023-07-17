package com.pragma.entomologistapp.data.local.database.entity.customResponses

import com.pragma.entomologistapp.core.FormatDateEntomologist.DD_MM_YYYY
import com.pragma.entomologistapp.core.ext.toStringWithFormat
import com.pragma.entomologistapp.domain.model.RecordInsectGeolocationDomain
import java.util.Calendar


data class RecordInsectGeolocation(
    val idEntomologist: Int,
    val idGeolocation: Int,
    val idInsect: Int,
    val idRecord: Int,
    val countInsect: Int,
    val dateRecord: Calendar,
    val countComment: String,
    val nameInsect: String,
    val photoInsect: String,
    val moreInfo: String,
    val cityName: String,
    val latitude: Double,
    val longitude: Double
){
    fun toDomain() : RecordInsectGeolocationDomain = RecordInsectGeolocationDomain(
        this.idEntomologist,
        this.idGeolocation,
        this.idInsect,
        this.idRecord,
        this.countInsect,
        this.dateRecord.toStringWithFormat( DD_MM_YYYY.format ),
        this.countComment,
        this.nameInsect,
        this.photoInsect,
        this.moreInfo,
        this.cityName,
        this.latitude,
        this.longitude
    )
}
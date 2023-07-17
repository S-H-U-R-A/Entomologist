package com.pragma.entomologistapp.domain.model

import android.os.Parcelable
import com.pragma.entomologistapp.core.FormatDateEntomologist.*
import com.pragma.entomologistapp.core.ext.toCalendar
import com.pragma.entomologistapp.data.local.database.entity.customResponses.RecordInsectGeolocation
import kotlinx.parcelize.Parcelize

@Parcelize
data class RecordInsectGeolocationDomain(
    val idEntomologist: Int,
    val idGeolocation: Int,
    val idInsect: Int,
    val idRecord: Int,
    val countInsect: Int,
    val dateRecord: String,
    val countComment: String,
    val nameInsect: String,
    val photoInsect: String,
    val moreInfo: String,
    val cityName: String,
    val latitude: Double,
    val longitude: Double
): Parcelable{
    fun toDataLayer() : RecordInsectGeolocation = RecordInsectGeolocation(
        this.idEntomologist,
        this.idGeolocation,
        this.idInsect,
        this.idRecord,
        this.countInsect,
        this.dateRecord.toCalendar( DD_MM_YYYY.format ),
        this.countComment,
        this.nameInsect,
        this.photoInsect,
        this.moreInfo,
        this.cityName,
        this.latitude,
        this.longitude
    )
}

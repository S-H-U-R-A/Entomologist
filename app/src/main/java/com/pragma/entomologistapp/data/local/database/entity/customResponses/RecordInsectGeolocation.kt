package com.pragma.entomologistapp.data.local.database.entity.customResponses

import java.util.Calendar

data class RecordInsectGeolocation(
    val countInsect: Int,
    val dateRecord: Calendar,
    val nameInsect: String,
    val photoInsect: String,
    val cityName: String,
)
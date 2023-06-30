package com.pragma.entomologistapp.data.local.database.converters

import androidx.room.TypeConverter
import java.util.Calendar

class DateConverter {

    @TypeConverter
    fun fromTimestamp(dateInMillis: Long): Calendar {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = dateInMillis
        return calendar
    }

    @TypeConverter
    fun localDateToTimestamp(date: Calendar) : Long = date.timeInMillis

}
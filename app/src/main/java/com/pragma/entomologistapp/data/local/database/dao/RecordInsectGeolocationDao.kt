package com.pragma.entomologistapp.data.local.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.pragma.entomologistapp.data.local.database.entity.customResponses.RecordInsectGeolocation
import kotlinx.coroutines.flow.Flow

@Dao
interface RecordInsectGeolocationDao {

    @Query("SELECT \n" +
            "re.count_insect AS countInsect, \n" +
            "re.date AS dateRecord,\n" +
            "ins.name AS nameInsect,\n" +
            "ins.url_photo  AS photoInsect,\n" +
            "gt.city_name AS cityName\n" +
            "FROM record_table AS re \n" +
            "JOIN insect_table AS ins ON re.id_insect = ins.id\n" +
            "JOIN geolocation_table AS gt  ON re.id_geolocation = gt.id")
    fun loadRecordWithInsectAndGeolocation() : Flow< List<RecordInsectGeolocation>>


}
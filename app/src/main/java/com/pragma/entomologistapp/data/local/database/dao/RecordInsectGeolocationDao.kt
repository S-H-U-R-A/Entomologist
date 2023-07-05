package com.pragma.entomologistapp.data.local.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.pragma.entomologistapp.data.local.database.entity.customResponses.RecordInsectGeolocation
import kotlinx.coroutines.flow.Flow

@Dao
interface RecordInsectGeolocationDao {

    @Query(
        """
            SELECT
            re.id AS idRecord,
            re.count_insect AS countInsect, 
            re.date AS dateRecord, 
            ins.name AS nameInsect, 
            ins.url_photo  AS photoInsect, 
            gt.city_name AS cityName
            FROM record_table AS re 
            JOIN insect_table AS ins ON re.id_insect = ins.id 
            JOIN geolocation_table AS gt ON re.id_geolocation = gt.id 
            ORDER BY re.id DESC
        """
    )
    fun loadRecordWithInsectAndGeolocation(): Flow< List< RecordInsectGeolocation > >


}
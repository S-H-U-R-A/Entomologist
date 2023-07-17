package com.pragma.entomologistapp.data.local.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.pragma.entomologistapp.data.local.database.entity.customResponses.RecordInsectGeolocation
import com.pragma.entomologistapp.data.local.database.entity.customResponses.ReportInsectBySpecies
import kotlinx.coroutines.flow.Flow

@Dao
interface RecordAndReportInsectGeolocationDao {

    @Query(
        """
            SELECT
                et.id AS idEntomologist,
                gt.id AS idGeolocation,
                ins.id AS idInsect,
                re.id AS idRecord,
                re.count_insect AS countInsect, 
                re.date AS dateRecord,
                re.comment AS countComment,
                ins.name AS nameInsect, 
                ins.url_photo  AS photoInsect,
                ins.more_information AS moreInfo,
                gt.city_name AS cityName,
                gt.latitude AS latitude,
                gt.longitude AS longitude
            FROM record_table AS re 
            JOIN insect_table AS ins ON re.id_insect = ins.id 
            JOIN geolocation_table AS gt ON re.id_geolocation = gt.id
            JOIN entomologist_table AS et ON et.id = re.id_entomologist
            ORDER BY re.id DESC
        """
    )
    fun loadRecordWithInsectAndGeolocation(): Flow< List< RecordInsectGeolocation > >

    @Query(
        """
            SELECT
                it.id AS idInsect,
                it.name AS nameInsect,
                it.url_photo AS photoInsect,
                SUM(rt.count_insect) AS countInsectBySpecie,
                COUNT(DISTINCT gt.city_name) AS cityCount,
                MIN(rt.date) AS dateInitialRecord,
                MAX(rt.date) AS dateEndRecord
            FROM record_table AS rt 
            JOIN entomologist_table AS et ON rt.id_entomologist = et.id 
            JOIN insect_table AS it ON rt.id_insect = it.id 
            JOIN geolocation_table AS gt ON rt.id_geolocation = gt.id
            GROUP BY it.name
        """
    )
    fun loadReportWithInsectAndGeolocation() : Flow< List< ReportInsectBySpecies > >


}
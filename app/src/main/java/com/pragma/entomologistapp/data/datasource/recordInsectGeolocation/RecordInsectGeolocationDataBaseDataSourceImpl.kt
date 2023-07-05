package com.pragma.entomologistapp.data.datasource.recordInsectGeolocation

import com.pragma.entomologistapp.data.datasource.interfaces.recordInsectGeolocation.RecordInsectGeolocationDataBaseDataSource
import com.pragma.entomologistapp.data.local.database.dao.RecordInsectGeolocationDao
import com.pragma.entomologistapp.data.local.database.entity.customResponses.RecordInsectGeolocation
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RecordInsectGeolocationDataBaseDataSourceImpl @Inject constructor(
    private val recordInsectGeolocationDao: RecordInsectGeolocationDao
) : RecordInsectGeolocationDataBaseDataSource {

    override fun loadRecordWithInsectAndGeolocation(): Flow< List<RecordInsectGeolocation> > {
        return recordInsectGeolocationDao.loadRecordWithInsectAndGeolocation()
    }

}
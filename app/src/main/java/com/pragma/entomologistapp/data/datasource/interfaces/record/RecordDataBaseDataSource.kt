package com.pragma.entomologistapp.data.datasource.interfaces.record

import com.pragma.entomologistapp.data.local.database.entity.RecordEntity

interface RecordDataBaseDataSource {

    suspend fun insertRecord(recordEntity: RecordEntity)

}
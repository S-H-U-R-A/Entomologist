package com.pragma.entomologistapp.data.datasource.record

import com.pragma.entomologistapp.data.datasource.interfaces.record.RecordDataBaseDataSource
import com.pragma.entomologistapp.data.local.database.dao.RecordDao
import com.pragma.entomologistapp.data.local.database.entity.RecordEntity
import javax.inject.Inject

class RecordDataBaseDataSourceImpl @Inject constructor(
    private val recordDao: RecordDao
) : RecordDataBaseDataSource {

    override suspend fun insertRecord(recordEntity: RecordEntity) {
        recordDao.insertRecord(recordEntity)
    }
}
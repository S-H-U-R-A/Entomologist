package com.pragma.entomologistapp.domain.repository

import com.pragma.entomologistapp.data.local.database.entity.RecordEntity

interface RecordRepository {
    suspend fun insertRecord(recordEntity: RecordEntity)
}
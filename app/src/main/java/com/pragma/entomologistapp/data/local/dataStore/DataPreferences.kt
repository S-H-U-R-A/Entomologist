package com.pragma.entomologistapp.data.local.dataStore

import kotlinx.coroutines.flow.Flow

interface DataPreferences<T> {
    val data: Flow<T>
    suspend fun saveData(data: T)
}
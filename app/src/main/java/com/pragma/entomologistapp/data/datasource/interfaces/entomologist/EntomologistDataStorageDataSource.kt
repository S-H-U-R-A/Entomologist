package com.pragma.entomologistapp.data.datasource.interfaces.entomologist

import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.Flow

interface EntomologistDataStorageDataSource {

    fun getPreferencesFirstTime(): Flow<Boolean>

    suspend fun savePreferencesFirstTime(data: Boolean): Unit

}
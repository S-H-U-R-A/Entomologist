package com.pragma.entomologistapp.data.datasource.interfaces.entomologist

import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.Flow

interface EntomologistDataStorageDataSource {

    fun getPreferencesFirstTime(): Flow<Boolean>

    suspend fun savePreferencesFirstTime(firstTime: Boolean)

    fun getPreferencesIdUser() : Flow<Long>

    suspend fun savePreferencesIdUser(id: Long)

}
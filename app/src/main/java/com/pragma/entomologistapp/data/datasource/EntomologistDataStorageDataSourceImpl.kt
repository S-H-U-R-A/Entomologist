package com.pragma.entomologistapp.data.datasource

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.pragma.entomologistapp.data.datasource.interfaces.EntomologistDataStorageDataSource
import com.pragma.entomologistapp.data.local.dataStore.EntomologistPreferences
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class EntomologistDataStorageDataSourceImpl @Inject constructor(
    private val entomologistPreferences: EntomologistPreferences
) : EntomologistDataStorageDataSource {

    override fun getPreferencesFirstTime(): Flow<Boolean> {
        return entomologistPreferences.data
    }

    override suspend fun savePreferencesFirstTime(data: Boolean) {
       entomologistPreferences.saveData(data)
    }


}
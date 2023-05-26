package com.pragma.entomologistapp.data.local.dataStore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject


class EntomologistPreferences @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : DataPreferences<Boolean> {

    companion object PreferencesKey {
        val KEY_FIRST_TIME = booleanPreferencesKey("key_first_time")
    }

    override val data: Flow<Boolean>
        get() = dataStore.data
            .catch {
                if (it is IOException) {
                    it.printStackTrace()
                    emit(
                        emptyPreferences()
                    )
                } else throw it
            }
            .map { preferences ->
                preferences[KEY_FIRST_TIME] ?: false
            }

    override suspend fun saveData(data: Boolean){
        dataStore.edit { mutablePreferences ->
            mutablePreferences[KEY_FIRST_TIME] = data
        }
    }

}
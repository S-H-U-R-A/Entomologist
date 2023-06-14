package com.pragma.entomologistapp.data.local.dataStore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.longPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject


class EntomologistPreferences @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {

    companion object PreferencesKey {
        val KEY_FIRST_TIME = booleanPreferencesKey("key_first_time")
        val KEY_ID_USER = longPreferencesKey("key_id_user")
    }

    fun getFirstTime() : Flow<Boolean>{
        return dataStore.data
            .catch {
                if (it is IOException) {
                    it.printStackTrace()
                    emit(
                        emptyPreferences()
                    )
                } else throw it
            }
            .map { preferences ->
                preferences[KEY_FIRST_TIME] ?: true
            }
    }

    fun getIdUser() : Flow<Long>{
        return dataStore.data
            .catch {
                if (it is IOException) {
                    it.printStackTrace()
                    emit(
                        emptyPreferences()
                    )
                } else throw it
            }
            .map { preferences ->
                preferences[KEY_ID_USER] ?: 0
            }
    }

    suspend fun saveFirstTime(firstTime: Boolean) {
        dataStore.edit { mutablePreferences ->
            mutablePreferences[KEY_FIRST_TIME] = firstTime
        }
    }

    suspend fun saveIdUser(id: Long) {
        dataStore.edit { mutablePreferences ->
            mutablePreferences[KEY_ID_USER] = id
        }
    }


}
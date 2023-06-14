package com.pragma.entomologistapp.data.datasource.entomologist

import com.pragma.entomologistapp.data.datasource.interfaces.entomologist.EntomologistDataStorageDataSource
import com.pragma.entomologistapp.data.local.dataStore.EntomologistPreferences
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class EntomologistDataStorageDataSourceImpl @Inject constructor(
    private val entomologistPreferences: EntomologistPreferences
) : EntomologistDataStorageDataSource {

    override fun getPreferencesFirstTime(): Flow<Boolean> {
        return entomologistPreferences.getFirstTime()
    }

    override suspend fun savePreferencesFirstTime(firstTime: Boolean) {
       entomologistPreferences.saveFirstTime(firstTime)
    }

    override fun getPreferencesIdUser(): Flow<Long> {
       return entomologistPreferences.getIdUser()
    }

    override suspend fun savePreferencesIdUser(id: Long) {
        entomologistPreferences.saveIdUser(id)
    }


}
package com.pragma.entomologistapp.data.datasource.entomologist

import com.pragma.entomologistapp.data.datasource.interfaces.entomologist.EntomologistDataBaseDataSource
import com.pragma.entomologistapp.data.local.database.dao.EntomologistDao
import com.pragma.entomologistapp.data.local.database.entity.EntomologistEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class EntomologistDataBaseDataSourceImpl @Inject constructor(
    private val entomologistDao: EntomologistDao
) : EntomologistDataBaseDataSource {

    override fun getEntomologist(id: Int): Flow<EntomologistEntity> {
        return entomologistDao.getEntomologist(id)
    }

    override suspend fun insertEntomologist(entomologist: EntomologistEntity) {
        entomologistDao.insertEntomologist(entomologist)
    }


}
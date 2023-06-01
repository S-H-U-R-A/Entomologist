package com.pragma.entomologistapp.data.datasource.insect

import com.pragma.entomologistapp.data.datasource.interfaces.insect.InsectDataBaseDataSource
import com.pragma.entomologistapp.data.local.database.dao.InsectDao
import com.pragma.entomologistapp.data.local.database.entity.InsectEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class InsectDataBaseDataSourceImpl @Inject constructor(
    private val insectDao: InsectDao
) : InsectDataBaseDataSource {

    override fun getAllInsects(): Flow< List<InsectEntity> > {
        return insectDao.getAllInsects()
    }

}
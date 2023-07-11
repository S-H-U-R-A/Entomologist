package com.pragma.entomologistapp.data.datasource.interfaces.insect

import com.pragma.entomologistapp.data.local.database.entity.InsectEntity
import kotlinx.coroutines.flow.Flow


interface InsectDataBaseDataSource {

    fun getAllInsects() : Flow< List<InsectEntity> >

    suspend fun insertAndGetInsect(insect: InsectEntity): InsectEntity

}
package com.pragma.entomologistapp.domain.repository

import com.pragma.entomologistapp.data.local.database.entity.InsectEntity
import com.pragma.entomologistapp.domain.model.InsectDomain
import kotlinx.coroutines.flow.Flow

interface InsectRepository {

    fun getAllInsects(): Flow< List<InsectDomain> >

}
package com.pragma.entomologistapp.domain.usecases.geolocation

import com.pragma.entomologistapp.domain.model.GeolocationDomain
import com.pragma.entomologistapp.domain.repository.GeolocationRepository
import javax.inject.Inject

class SaveAndGetGeolocationDataBaseUseCase @Inject constructor (
    private val geolocationRepository: GeolocationRepository
) {

    suspend operator fun invoke(geolocationDomain: GeolocationDomain) : Long{
        return geolocationRepository.insertGeolocation( geolocationDomain.toEntity() )
    }

}
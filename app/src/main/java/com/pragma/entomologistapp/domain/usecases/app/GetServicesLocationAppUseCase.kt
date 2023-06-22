package com.pragma.entomologistapp.domain.usecases.app

import com.pragma.entomologistapp.domain.repository.LocationRepository
import javax.inject.Inject

class GetServicesLocationAppUseCase @Inject constructor(
    private val locationRepository: LocationRepository
) {

    suspend operator fun invoke() : Boolean{
        return locationRepository.checkLocationServicesEnabled()
    }

}
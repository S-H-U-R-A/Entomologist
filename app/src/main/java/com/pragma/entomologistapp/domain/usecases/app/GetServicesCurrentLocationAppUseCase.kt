package com.pragma.entomologistapp.domain.usecases.app

import com.pragma.entomologistapp.domain.repository.ServiceLocationRepository
import javax.inject.Inject

class GetServicesCurrentLocationAppUseCase @Inject constructor(
    private val serviceLocationRepository: ServiceLocationRepository
) {
    suspend operator fun invoke(): Triple<Double, Double, String>?{
        return serviceLocationRepository.getCoordinatesAndPlaceName()
    }
}
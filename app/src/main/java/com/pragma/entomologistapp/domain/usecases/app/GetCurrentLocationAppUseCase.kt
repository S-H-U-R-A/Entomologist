package com.pragma.entomologistapp.domain.usecases.app

import com.pragma.entomologistapp.domain.repository.LocationRepository
import javax.inject.Inject

class GetCurrentLocationAppUseCase @Inject constructor(
    private val locationRepository: LocationRepository
) {
    suspend operator fun invoke(): Triple<Double, Double, String>?{
        return locationRepository.getCoordinatesAndPlaceName()
    }
}
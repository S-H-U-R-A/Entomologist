package com.pragma.entomologistapp.domain.usecases.entomologist

import com.pragma.entomologistapp.domain.model.EntomologistDomain
import com.pragma.entomologistapp.domain.repository.EntomologistRepository
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetEntomologistDataBaseUseCase @Inject constructor(
    private val getIdEntomologistPreferencesUseCase: GetIdEntomologistPreferencesUseCase,
    private val entomologistRepository: EntomologistRepository
) {

    @OptIn(FlowPreview::class)
    operator fun invoke(id: Int?) : Flow<EntomologistDomain?> {
        return if(id != null){
            entomologistRepository.getEntomologist(id)
        }else{
            getIdEntomologistPreferencesUseCase()
                .flatMapConcat { idUser ->
                if(idUser.toInt() != 0){
                    entomologistRepository.getEntomologist( idUser.toInt() )
                }else {
                    flowOf(null)
                }
            }
        }
    }

}
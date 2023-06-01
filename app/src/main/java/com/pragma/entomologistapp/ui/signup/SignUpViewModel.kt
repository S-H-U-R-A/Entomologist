package com.pragma.entomologistapp.ui.signup

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pragma.entomologistapp.core.TypeUser
import com.pragma.entomologistapp.domain.model.EntomologistDomain
import com.pragma.entomologistapp.domain.usecases.entomologist.SaveEntomologistPreferencesUseCase
import com.pragma.entomologistapp.domain.usecases.entomologist.SaveImageEntomologistAppUseCase
import com.pragma.entomologistapp.domain.usecases.entomologist.saveEntomologistDataBaseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val saveEntomologistPreferencesUseCase: SaveEntomologistPreferencesUseCase,
    private val saveEntomologistDataBaseUseCase: saveEntomologistDataBaseUseCase,
    private val saveImageEntomologistAppUseCase: SaveImageEntomologistAppUseCase
) : ViewModel() {

    private var _uiState: MutableStateFlow<SignUpUIState> = MutableStateFlow(SignUpUIState())
    val uiState: StateFlow<SignUpUIState> = _uiState.asStateFlow()

    //UI - ACTUALIZA LA OPCION DE GUARDAR
    fun setSaveButton(isEnable: Boolean) {
        _uiState.update { state ->
            state.copy(
                canSave = isEnable
            )
        }
    }

    //ACTUALIZAR LA PREFERENCIA
    fun setStartDestination(data: Boolean) {
        viewModelScope.launch {
            saveEntomologistPreferencesUseCase(data)
        }
    }

    //GUARDAR EL REGISTRO DEL ENTOMOLOGO
    fun saveEntomologist(
        id: Int?,
        name: String,
        uriPhoto: String
    ) {

        viewModelScope.launch {

            saveImageEntomologistAppUseCase(
                Uri.parse(uriPhoto),
                TypeUser.USER,
                null
            ).also { uriStorage ->

                val entomologist = EntomologistDomain(
                    id,
                    name,
                    uriStorage ?: uriPhoto
                )

                saveEntomologistDataBaseUseCase(entomologist)

            }

        }

    }

}

data class SignUpUIState(
    val canSave: Boolean = false
)



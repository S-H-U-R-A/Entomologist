package com.pragma.entomologistapp.ui.signup

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pragma.entomologistapp.core.TypeUser
import com.pragma.entomologistapp.domain.model.EntomologistDomain
import com.pragma.entomologistapp.domain.usecases.entomologist.SaveFirstTimeEntomologistPreferencesUseCase
import com.pragma.entomologistapp.domain.usecases.entomologist.SaveIdEntomologistPreferencesUseCase
import com.pragma.entomologistapp.domain.usecases.entomologist.SaveImageEntomologistAppUseCase
import com.pragma.entomologistapp.domain.usecases.entomologist.SaveEntomologistDataBaseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val saveEntomologistDataBaseUseCase: SaveEntomologistDataBaseUseCase,
    private val saveImageEntomologistAppUseCase: SaveImageEntomologistAppUseCase,
    private val saveIdEntomologistPreferencesUseCase: SaveIdEntomologistPreferencesUseCase,
    private val saveFirstTimeEntomologistPreferencesUseCase: SaveFirstTimeEntomologistPreferencesUseCase
) : ViewModel() {

    private var _uiState: MutableStateFlow<SignUpUIState> = MutableStateFlow(SignUpUIState())
    val uiState: StateFlow<SignUpUIState> = _uiState.asStateFlow()

    //UI - ACTUALIZA LA OPCIÓN DE GUARDAR
    fun setSaveButton(isEnable: Boolean) {
        _uiState.update { state ->
            state.copy(
                canSave = isEnable
            )
        }
    }

    //GUARDAR EL REGISTRO DEL ENTOMOLOGO
    fun saveEntomologist(
        id: Int?,
        name: String,
        uriPhoto: String
    ){
        viewModelScope.launch {

            //SE GUARDA LA IMAGEN EN EL ALMACENAMIENTO EXTERNO ESPECÍFICO
            val uriStorage: String? =  saveImageEntomologistAppUseCase(
                Uri.parse(uriPhoto),
                TypeUser.USER,
                null
            )

            //SE CONSTRUYE EL ENTOMÓLOGO
            val entomologist = EntomologistDomain(
                id,
                name,
                uriStorage ?: uriPhoto
            )

            //SE REGISTRA EN BASE
            val idUser = viewModelScope.async {
                saveEntomologistDataBaseUseCase(entomologist)
            }

            //SE REGISTRA EN BASE Y SE OBTIENE EL ID, PARA GURADAR EL IDENTIFICADOR
            saveIdEntomologistPreferencesUseCase( idUser.await() )

            //AL CAMBIAR LA PREFERENCIA SE NAVEGA
            saveFirstTimeEntomologistPreferencesUseCase(false)

             _uiState.update { state ->
                state.copy(
                    isFirstTime = false
                )
             }

        }

    }
}

data class SignUpUIState(
    val canSave: Boolean = false,
    val isFirstTime: Boolean = true
)



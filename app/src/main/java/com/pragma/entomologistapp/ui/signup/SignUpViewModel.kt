package com.pragma.entomologistapp.ui.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pragma.entomologistapp.domain.model.EntomologistDomain
import com.pragma.entomologistapp.domain.repository.EntomologistRepository
import com.pragma.entomologistapp.domain.usecases.SaveEntomologistPreferencesUseCase
import com.pragma.entomologistapp.domain.usecases.saveEntomologistDataBaseUseCase
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
    private val saveEntomologistDataBaseUseCase: saveEntomologistDataBaseUseCase
) : ViewModel() {

    private var _uiState: MutableStateFlow<SignUpUIState> = MutableStateFlow( SignUpUIState() )
    val uiState: StateFlow<SignUpUIState> = _uiState.asStateFlow()



    fun setSaveButton(isEnable: Boolean){
        _uiState.update { state ->
            state.copy(
                save = isEnable
            )
        }
    }

    fun setStartDestination(data: Boolean){
        viewModelScope.launch {
            saveEntomologistPreferencesUseCase(data)
        }
    }

    fun saveEntomologist(entomologist: EntomologistDomain){
        viewModelScope.launch {
            saveEntomologistDataBaseUseCase( entomologist )
        }
    }

}


data class SignUpUIState(
    val save: Boolean = false
)


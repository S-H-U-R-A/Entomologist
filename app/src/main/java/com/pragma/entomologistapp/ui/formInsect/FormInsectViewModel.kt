package com.pragma.entomologistapp.ui.formInsect

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pragma.entomologistapp.core.TypeUser
import com.pragma.entomologistapp.domain.model.EntomologistDomain
import com.pragma.entomologistapp.domain.model.InsectDomain
import com.pragma.entomologistapp.domain.usecases.entomologist.GetEntomologistDataBaseUseCase
import com.pragma.entomologistapp.domain.usecases.insect.GetInsectsNamesUseCase
import com.pragma.entomologistapp.domain.usecases.insect.SaveImageInsectAppUseCase
import com.pragma.entomologistapp.domain.usecases.insect.SaveInsectDataBaseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FormInsectViewModel @Inject constructor(
    private val getEntomologistDataBaseUseCase: GetEntomologistDataBaseUseCase,
    private val getInsectsNamesUseCase: GetInsectsNamesUseCase,
    private val saveImageInsectAppUseCase: SaveImageInsectAppUseCase,
    private val saveInsectDataBaseUseCase: SaveInsectDataBaseUseCase
) : ViewModel() {

    private var _uiState: MutableStateFlow<FormInsectUiState> =
        MutableStateFlow(FormInsectUiState())
    val uiState: StateFlow<FormInsectUiState> = _uiState.asStateFlow()

    init {
        getAllInsects()
    }

    private fun getAllInsects() {
        viewModelScope.launch {
            try {
                getInsectsNamesUseCase().collect { names ->
                    _uiState.update { state ->
                        state.copy(
                            listInsect = names
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.update { state ->
                    state.copy(
                        userMessage = e.message ?: "Ocurrio un error inesperado"
                    )
                }
            }
        }
    }

    fun loadUser() {
        viewModelScope.launch {
            //LOADING ON
            _uiState.update { uiState -> uiState.copy(isLoading = true) }
            //GET ENTOMOLOGIST
            getEntomologistDataBaseUseCase(null).collect { entomologist ->
                entomologist?.let { ento ->
                    if ( ento.urlPhoto != EntomologistDomain.IMAGE_DEFAULT ) {
                        _uiState.update { state ->
                            state.copy(
                                isLoading = false,
                                photoEntomologist = ento.urlPhoto
                            )
                        }
                    }
                }
            }
        }
    }

    fun saveInsect(
        name: String,
        image: Uri,
        moreInfo: String,
        onSaveInsect: (insect: InsectDomain) -> Unit
    ) {
        viewModelScope.launch {
            //LOADING ON
            _uiState.update { uiState -> uiState.copy(isLoading = true) }

            val uriStorage: String? = saveImageInsectAppUseCase(
                image,
                TypeUser.INSECT,
                name
            )

            val insect: InsectDomain = InsectDomain(
                null,
                name,
                uriStorage ?: InsectDomain.IMAGE_DEFAULT,
                moreInfo
            )

            val insectInserted: InsectDomain = saveInsectDataBaseUseCase(insect)
            //LOADING OFF
            _uiState.update { uiState -> uiState.copy(isLoading = true) }
            //NAVIGATE
            onSaveInsect(insectInserted)

        }
    }

    fun setVisibilityButtons(saveButton: Boolean, selectedButton: Boolean) {
        _uiState.update { state ->
            state.copy(
                isVisibleButtonSave = saveButton,
                isVisibleButtonSelected = selectedButton
            )
        }
    }

}

data class FormInsectUiState(
    val isLoading: Boolean= false,
    val canNavigate: Boolean = false,
    val photoEntomologist: String = EntomologistDomain.IMAGE_DEFAULT,
    val listInsect: List<String> = emptyList(),
    val isVisibleButtonSave: Boolean = true,
    val isVisibleButtonSelected: Boolean = false,
    val userMessage: String = ""
)
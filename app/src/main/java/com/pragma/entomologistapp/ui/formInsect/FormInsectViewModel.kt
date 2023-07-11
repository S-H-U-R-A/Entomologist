package com.pragma.entomologistapp.ui.formInsect

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pragma.entomologistapp.core.TypeUser
import com.pragma.entomologistapp.domain.model.EntomologistDomain
import com.pragma.entomologistapp.domain.model.InsectDomain
import com.pragma.entomologistapp.domain.usecases.entomologist.GetEntomologistDataBaseUseCase
import com.pragma.entomologistapp.domain.usecases.insect.GetInsectsUseCase
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
    private val getInsectsUseCase: GetInsectsUseCase,
    private val saveImageInsectAppUseCase: SaveImageInsectAppUseCase,
    private val saveInsectDataBaseUseCase: SaveInsectDataBaseUseCase
) : ViewModel() {

    private var _uiState: MutableStateFlow<FormInsectUiState> = MutableStateFlow(FormInsectUiState())
    val uiState: StateFlow<FormInsectUiState> = _uiState.asStateFlow()

    private var insectDomain: InsectDomain? = null

    init {
        getAllInsects()
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

    private fun getAllInsects() {
        viewModelScope.launch {
            try {
                getInsectsUseCase().collect { listInsectDomain ->
                    val listOfNames: List<String> = listInsectDomain.map { it.name }
                    _uiState.update { state ->
                        state.copy(
                            listInsectNames = listOfNames.map { word ->  word.lowercase().replaceFirstChar { it.uppercase() } },
                            listInsect = listInsectDomain
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

    fun selectInsect(nameInsect: String){
        //SE SELECCIONA EL INSECTO
        val filterInsect = _uiState.value.listInsect.filter { insect -> insect.name.uppercase() == nameInsect.uppercase() }
        //SI EXISTE UN ELEMENTO QUE COINCIDE CON LOS INSECTOS REGISTRADOS
        if( filterInsect.isNotEmpty() ){
            //UPDATE DATA
            with(filterInsect.first()){

                insectDomain = this

                setDataInsect(
                    urlPhoto,
                    moreInfo,
                    false
                )
            }

        }else{
            //RESET DATA
            insectDomain = null
            setDataInsect("", "", true )
        }
    }

     fun setDataInsect(image: String, urlInfo: String, isEnableDataInsectSelected: Boolean ){
        _uiState.update { state ->
            state.copy(
                imageSelected = image,
                isEnableDataInsectSelected = isEnableDataInsectSelected,
                urlInfo = urlInfo
            )
        }
    }

    fun followTheCount( onSaveInsect: (insect: InsectDomain) -> Unit   ){
        insectDomain?.let{
            onSaveInsect(it)
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

    fun saveInsect( name: String, image: Uri, moreInfo: String, onSaveInsect: (insect: InsectDomain) -> Unit ) {
        viewModelScope.launch {
            //LOADING ON
            _uiState.update { uiState -> uiState.copy(isLoading = true) }
            //SAVE IMAGE
            val uriStorage: String? = saveImageInsectAppUseCase( image, TypeUser.INSECT, name)
            //CREATE INSECT DOMAIN
            val insect: InsectDomain = InsectDomain(null, name, uriStorage ?: InsectDomain.IMAGE_DEFAULT, moreInfo )
            //INSERT INSECT
            val insectInserted: InsectDomain = saveInsectDataBaseUseCase(insect)
            //LOADING OFF
            _uiState.update { uiState -> uiState.copy( isLoading = true ) }
            //NAVIGATE
            onSaveInsect(insectInserted)
        }
    }

}

data class FormInsectUiState(
    val isLoading: Boolean= false,
    val canNavigate: Boolean = false,
    val photoEntomologist: String = EntomologistDomain.IMAGE_DEFAULT,

    val listInsect: List<InsectDomain> = emptyList(), //ESTA ES PARA HACER OPERACIONES
    val listInsectNames: List<String> = emptyList(),

    val imageSelected: String = "",
    val isEnableDataInsectSelected: Boolean = true,
    val urlInfo: String = "",

    val isVisibleButtonSave: Boolean = true,
    val isVisibleButtonSelected: Boolean = false,
    val userMessage: String = ""
)

//val FormInsectUiState.isEnableImage:Boolean get() = imageSelected.isNotEmpty()


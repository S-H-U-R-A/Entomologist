package com.pragma.entomologistapp.ui.recordInsect


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pragma.entomologistapp.domain.model.EntomologistDomain
import com.pragma.entomologistapp.domain.model.InsectDomain
import com.pragma.entomologistapp.domain.usecases.entomologist.GetEntomologistDataBaseUseCase
import com.pragma.entomologistapp.domain.usecases.entomologist.GetFirstTimeEntomologistPreferencesUseCase
import com.pragma.entomologistapp.domain.usecases.entomologist.GetIdEntomologistPreferencesUSeCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecordInsectViewModel @Inject constructor(
    private val getIdEntomologistPreferencesUSeCase: GetIdEntomologistPreferencesUSeCase,
    private val getEntomologistDataBaseUseCase: GetEntomologistDataBaseUseCase,
    var getFirstTimeEntomologistPreferencesUseCase: GetFirstTimeEntomologistPreferencesUseCase
) : ViewModel() {

    private var _uiState: MutableStateFlow<RecordInsectUiState> = MutableStateFlow( RecordInsectUiState() )
    val uiState: StateFlow<RecordInsectUiState> = _uiState.asStateFlow()

    private var _firstTime: MutableLiveData<Boolean> = MutableLiveData()
    val firstTime: LiveData<Boolean> = _firstTime

    fun loadUser() {
        viewModelScope.launch {
            //VALIDATE FIRST TIME
            getFirstTimeEntomologistPreferencesUseCase().collect{ firstTime -> //true

                _firstTime.postValue(firstTime)

                //SE VALIDA QUE NO ES LA PRIMERA VEZ
                if(!firstTime){
                    //OBTENEMOS EL ID DEL ENTOMOLOGO
                    getIdEntomologistPreferencesUSeCase().collect { idUser ->

                        getEntomologistDataBaseUseCase( idUser.toInt() ).collect{ entomologist ->

                            _uiState.update { state ->
                                state.copy(
                                    imageEntomologist = entomologist.urlPhoto
                                )
                            }

                        }

                    }
                }

            }
        }
    }

}

data class RecordInsectUiState(
    val imageEntomologist: String = EntomologistDomain.IMAGE_DEFAULT,
    val insectList: List<InsectDomain> = emptyList(),
    val isVisibleList: Boolean = false,
    val isVisibleReport: Boolean = false,
    val isVisibleRecords: Boolean = false
)
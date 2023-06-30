package com.pragma.entomologistapp.ui.recordInsect


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pragma.entomologistapp.domain.model.EntomologistDomain
import com.pragma.entomologistapp.domain.model.InsectDomain
import com.pragma.entomologistapp.domain.usecases.entomologist.GetEntomologistDataBaseUseCase
import com.pragma.entomologistapp.domain.usecases.entomologist.GetFirstTimeEntomologistPreferencesUseCase
import com.pragma.entomologistapp.domain.usecases.entomologist.GetIdEntomologistPreferencesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecordInsectViewModel @Inject constructor(
    private val getIdEntomologistPreferencesUSeCase: GetIdEntomologistPreferencesUseCase,
    private val getEntomologistDataBaseUseCase: GetEntomologistDataBaseUseCase,
    var getFirstTimeEntomologistPreferencesUseCase: GetFirstTimeEntomologistPreferencesUseCase
) : ViewModel() {

    private var _uiState: MutableStateFlow<RecordInsectUiState> = MutableStateFlow( RecordInsectUiState() )
    val uiState: StateFlow<RecordInsectUiState> = _uiState.asStateFlow()

    private var _firstTime: MutableLiveData<Boolean> = MutableLiveData()
    val firstTime: LiveData<Boolean> = _firstTime

    fun loadUser() {
        viewModelScope.launch {
            //LOADING ON
            _uiState.update { uiState -> uiState.copy(isLoading = true) }
            //VALIDATE FIRST TIME
            getFirstTimeEntomologistPreferencesUseCase().collect{ firstTime -> //true
                if(!firstTime){ //IS NOT FIRST TIME
                    getIdEntomologistPreferencesUSeCase().collect { idUser -> //GET EL ID DEL ENTOMOLOGIST
                        getEntomologistDataBaseUseCase( idUser.toInt() ).collect{ entomologist ->
                            _uiState.update { state ->
                                state.copy(
                                    isLoading = false,
                                    imageEntomologist = entomologist.urlPhoto
                                )
                            }
                        }
                    }
                }else{
                    _uiState.update { uiState -> uiState.copy(isLoading = false) }
                }
                _firstTime.postValue(firstTime)
            }
        }
    }

}

data class RecordInsectUiState(
    val isLoading: Boolean = false,
    val imageEntomologist: String = EntomologistDomain.IMAGE_DEFAULT,
    val insectList: List<InsectDomain> = emptyList(),
    val isVisibleList: Boolean = false,
    val isVisibleReport: Boolean = false,
    val isVisibleRecords: Boolean = false
)
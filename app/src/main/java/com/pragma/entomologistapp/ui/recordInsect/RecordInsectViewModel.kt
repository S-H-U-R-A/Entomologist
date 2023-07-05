package com.pragma.entomologistapp.ui.recordInsect


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pragma.entomologistapp.domain.model.EntomologistDomain
import com.pragma.entomologistapp.domain.model.RecordInsectGeolocationDomain
import com.pragma.entomologistapp.domain.usecases.entomologist.GetEntomologistDataBaseUseCase
import com.pragma.entomologistapp.domain.usecases.recordInsectGeolocation.LoadRecordWithInsectAndGeolocationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecordInsectViewModel @Inject constructor(
    private val getEntomologistDataBaseUseCase: GetEntomologistDataBaseUseCase,
    private val loadRecordWithInsectAndGeolocationUseCase: LoadRecordWithInsectAndGeolocationUseCase
) : ViewModel() {

    private var _uiState: MutableStateFlow<RecordInsectUiState> = MutableStateFlow( RecordInsectUiState() )
    val uiState: StateFlow<RecordInsectUiState> = _uiState.asStateFlow()

    private var _firstTime: MutableLiveData<Boolean> = MutableLiveData()
    val firstTime: LiveData<Boolean> = _firstTime

    fun loadData() {
        viewModelScope.launch {
            //LOADING ON
            _uiState.update { uiState -> uiState.copy( isLoading = true) }
            //GET FIRST TIME
            getEntomologistDataBaseUseCase(null)
                .collect {  entomologist ->
                if(entomologist != null){
                    //HACER COSAS SI EXISTE UN ENTOMOLOGO
                    _firstTime.postValue(false)
                    //
                    _uiState.update { state -> state.copy( isLoading = false, imageEntomologist = entomologist.urlPhoto ) }
                    //SE CARGAN LOS INSECTOS
                    loadRecordsInsect()
                }else{
                    _uiState.update { uiState -> uiState.copy(isLoading = false) }
                    _firstTime.postValue(true)
                }
            }
        }
    }

    private suspend fun loadRecordsInsect(){
        loadRecordWithInsectAndGeolocationUseCase()
            .collect{ listRecords: List<RecordInsectGeolocationDomain> ->
               _uiState.update { uiState -> uiState.copy( isLoading = false, recordList = listRecords) }
            }
    }

}

data class RecordInsectUiState(
    val isLoading: Boolean = false,
    val imageEntomologist: String = EntomologistDomain.IMAGE_DEFAULT,
    val recordList: List<RecordInsectGeolocationDomain> = emptyList()
    //LISTA DE INFORMES
)

val RecordInsectUiState.isVisibleButtonsAndShouldAdjustUi: Boolean get() = recordList.isNotEmpty()
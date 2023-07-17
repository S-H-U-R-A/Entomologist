package com.pragma.entomologistapp.ui.countInsect

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pragma.entomologistapp.core.FormatDateEntomologist
import com.pragma.entomologistapp.core.FormatDateEntomologist.*
import com.pragma.entomologistapp.core.ext.toCalendar
import com.pragma.entomologistapp.domain.model.GeolocationDomain
import com.pragma.entomologistapp.domain.model.InsectDomain
import com.pragma.entomologistapp.domain.model.RecordDomain
import com.pragma.entomologistapp.domain.model.RecordInsectGeolocationDomain
import com.pragma.entomologistapp.domain.usecases.app.GetServicesCurrentLocationAppUseCase
import com.pragma.entomologistapp.domain.usecases.app.GetServicesLocationAppUseCase
import com.pragma.entomologistapp.domain.usecases.entomologist.GetIdEntomologistPreferencesUseCase
import com.pragma.entomologistapp.domain.usecases.geolocation.SaveAndGetGeolocationDataBaseUseCase
import com.pragma.entomologistapp.domain.usecases.record.SaveRecordDataBaseUseCase
import com.pragma.entomologistapp.domain.usecases.record.UpdateRecordDataBaseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class CountInsectViewModel @Inject constructor(
    private val getIdEntomologistPreferencesUseCase: GetIdEntomologistPreferencesUseCase,
    private val getServicesLocationAppUseCase: GetServicesLocationAppUseCase,
    private val getServicesCurrentLocationAppUseCase: GetServicesCurrentLocationAppUseCase,
    private val saveAndGetGeolocationDataBaseUseCase: SaveAndGetGeolocationDataBaseUseCase,
    private val saveRecordDataBaseUseCase: SaveRecordDataBaseUseCase,
    private val updateRecordDataBaseUseCase: UpdateRecordDataBaseUseCase
) : ViewModel() {

    private var _uiState: MutableStateFlow<CountInsectUiState> = MutableStateFlow(CountInsectUiState())
    val uiState: StateFlow<CountInsectUiState> get() = _uiState.asStateFlow()

    fun plusInsect(){
        _uiState.update { state ->
            val countInsectPlus = state.countInsect.plus(1)
            state.copy(
                countInsect = countInsectPlus
            )
        }
    }

    fun minusInsect(){
        _uiState.update { state ->
            val countInsectMinus = if(state.countInsect > 0) state.countInsect.minus(1) else state.countInsect
            state.copy(
                countInsect = countInsectMinus

            )
        }
    }

    fun setCount(count: Int){
        _uiState.update { state ->
            state.copy(
                countInsect = count
            )
        }
    }

    fun setData(insect: InsectDomain?, recordAndInsect: RecordInsectGeolocationDomain?){
        _uiState.update { uiState ->
            uiState.copy(
                insect = insect,
                recordInsect = recordAndInsect
            )
        }
    }

    fun sendMessageEntomologist( message: UserMessages ){
        _uiState.update { actualSate ->
            val messageError: List<UserMessages> =
                actualSate.messageEntomologist + message

            actualSate.copy(
                messageEntomologist = messageError
            )
        }
    }

    fun deleteMessageEntomologist( message: UserMessages ){
        _uiState.update { actualState ->
            val newListWithOutMessage =
                actualState.messageEntomologist.filterNot {
                    it.code == message.code
                }
            actualState.copy(
                messageEntomologist = newListWithOutMessage
            )
        }
    }

    fun getServicesLocationInfoApp( action: ( isLocationUsable: Boolean ) -> Unit ){
        viewModelScope.launch {
            //SE OBTIENE INFORMACIÓN DE DISPONIBILIDAD DEL HARDWARE PARA OBTENER LA UBICACIÓN
            val isLocationEnabled =  getServicesLocationAppUseCase()
            //SE EJECUTA EL CÓDIGO
            action( isLocationEnabled )
        }
    }

    fun enterInsectCount(idInsect: Int, comment: String, edit: Boolean, actionNavigate: () -> Unit){
        viewModelScope.launch {
            //LOADING ON
            _uiState.update { uiState -> uiState.copy(isLoading = true) }
            //VALIDATE IF IS A EDIT
            if(edit){
                val recordDomain: RecordDomain = createRecordData(
                    uiState.value.recordInsect?.idRecord!!,
                    comment,
                    uiState.value.countInsect,
                    uiState.value.recordInsect?.dateRecord!!.toCalendar(DD_MM_YYYY.format),
                    uiState.value.recordInsect?.idEntomologist!!,
                    idInsect,
                    uiState.value.recordInsect?.idGeolocation!!
                )
                //UPDATE RECORD INSECT
                updateRecordDataBaseUseCase(recordDomain)
                //LOADING OFF
                _uiState.update { uiState -> uiState.copy(isLoading = false) }
                //NAVIGATE TO LIST
                actionNavigate()
            }else{
                //GET INFORMATION OF SERVICES LOCATION
                val dataLocationComplete: Triple<Double, Double, String>? = getCurrentLocationComplete()
                if(dataLocationComplete != null){
                    //OBTENER EL USUARIO
                    getIdEntomologistPreferencesUseCase().collect{ idUser ->
                        if( idUser.toInt() != 0){
                            //MAKE AND REGISTER GEOLOCATION
                            val geolocationData = createGeolocationData(dataLocationComplete.first, dataLocationComplete.second, dataLocationComplete.third)
                            val idGeolocation: Long = saveAndGetGeolocationDataBaseUseCase( geolocationData )
                            //REGISTER ENTITY RECORD
                            val recordDomain  = createRecordData( null, comment, uiState.value.countInsect, Calendar.getInstance(), idUser.toInt(), idInsect, idGeolocation.toInt())
                            saveRecordDataBaseUseCase( recordDomain )
                            //LOADING OFF
                            _uiState.update { uiState -> uiState.copy(isLoading = false) }
                            //NAVIGATE TO LIST
                            actionNavigate()
                        }else{
                            sendMessageEntomologist( UserMessages.USER_NOT_FOUND )
                        }
                    }
                }else {
                    //LE INDICAMOS AL USUARIO QUE HUBO UN ERROR AL OBTENER LA LOCALIZACIÓN Y QUE LO INTENTE DE NUEVO
                    sendMessageEntomologist( UserMessages.GET_LOCATION_ERROR )
                }
            }
        }
    }

    private suspend fun getCurrentLocationComplete(): Triple<Double, Double, String>?{
        val currentLocationComplete: Deferred< Triple<Double, Double, String>?> = viewModelScope.async {
            getServicesCurrentLocationAppUseCase()
        }
        return currentLocationComplete.await()
    }

    private fun createGeolocationData(latitude: Double, longitude: Double, cityName: String) : GeolocationDomain {
        return  GeolocationDomain(
            null,
            latitude,
            longitude,
            cityName
        )
    }

    private fun createRecordData(idRecord:Int?, comment: String, countInsect: Int, date: Calendar, idUser: Int, idInsect: Int, idGeolocation: Int ) : RecordDomain {
        return RecordDomain(
            idRecord,
            comment,
            countInsect,
            date,
            idUser,
            idInsect,
            idGeolocation
        )
    }

}

data class CountInsectUiState (
    val isLoading: Boolean = false,
    val insect: InsectDomain? = null,
    val recordInsect: RecordInsectGeolocationDomain? = null,
    val countInsect: Int = 0,
    val comment: String = "",
    var messageEntomologist: List<UserMessages> = emptyList()
)

//VALOR DE ESTADO QUE DEPENDE DE OTRO VALOR
val CountInsectUiState.isVisibleButtonSave: Boolean get() = countInsect > 0
val CountInsectUiState.isVisibleComment: Boolean get() = countInsect > 0



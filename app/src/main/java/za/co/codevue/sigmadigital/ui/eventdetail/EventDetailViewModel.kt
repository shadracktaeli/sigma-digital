package za.co.codevue.sigmadigital.ui.eventdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import za.co.codevue.shared.domain.statefuldata.StatefulData
import za.co.codevue.shared.domain.usecase.events.GetEventByIdUseCase
import javax.inject.Inject

@HiltViewModel
class EventDetailViewModel @Inject constructor(
    private val getEventByIdUseCase: GetEventByIdUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow<EventDetailUiState>(EventDetailUiState.Idle)
    val uiState = _uiState.asStateFlow()
    var eventId: String? = null

    fun getEvent() {
        eventId?.also {
            viewModelScope.launch {
                _uiState.value = when (val result = getEventByIdUseCase(it)) {
                    is StatefulData.Success -> EventDetailUiState.Success(result.data)
                    is StatefulData.Error -> EventDetailUiState.Error(result.message)
                    else -> EventDetailUiState.Idle
                }
            }
        }
    }
}
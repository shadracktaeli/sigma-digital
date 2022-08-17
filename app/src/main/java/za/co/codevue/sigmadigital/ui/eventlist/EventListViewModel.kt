package za.co.codevue.sigmadigital.ui.eventlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import za.co.codevue.shared.domain.usecase.events.GetEventsUseCase
import javax.inject.Inject

@HiltViewModel
class EventListViewModel @Inject constructor(
    private val getEventsUseCase: GetEventsUseCase
) : ViewModel() {
    fun getEvents() = getEventsUseCase(Unit).cachedIn(viewModelScope)
}
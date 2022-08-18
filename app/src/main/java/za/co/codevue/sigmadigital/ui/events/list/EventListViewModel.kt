package za.co.codevue.sigmadigital.ui.events.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import za.co.codevue.shared.domain.usecase.events.GetEventsUseCase
import za.co.codevue.sigmadigital.extensions.addSeparators
import za.co.codevue.sigmadigital.ui.common.PagingListModel
import javax.inject.Inject

@HiltViewModel
class EventListViewModel @Inject constructor(
    private val getEventsUseCase: GetEventsUseCase
) : ViewModel() {
    fun getEvents() = getEventsUseCase(Unit).map { pagedData ->
        pagedData.map { PagingListModel.Data(it) }.addSeparators()
    }.cachedIn(viewModelScope)
}
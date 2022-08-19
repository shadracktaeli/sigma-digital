package za.co.codevue.sigmadigital.ui.schedule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import za.co.codevue.shared.domain.usecase.schedule.GetScheduleUseCase
import za.co.codevue.sigmadigital.extensions.addSeparators
import za.co.codevue.sigmadigital.ui.common.PagingListModel
import javax.inject.Inject

@HiltViewModel
class ScheduleViewModel @Inject constructor(
    private val getScheduleUseCase: GetScheduleUseCase
) : ViewModel() {
    fun getSchedule() = getScheduleUseCase(Unit).map { pagedData ->
        pagedData.map { PagingListModel.Data(it) }.addSeparators()
    }.cachedIn(viewModelScope)
}
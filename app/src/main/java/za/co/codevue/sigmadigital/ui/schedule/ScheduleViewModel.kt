package za.co.codevue.sigmadigital.ui.schedule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import za.co.codevue.shared.domain.usecase.schedule.GetScheduleUseCase
import javax.inject.Inject

@HiltViewModel
class ScheduleViewModel @Inject constructor(
    private val getScheduleUseCase: GetScheduleUseCase
) : ViewModel() {
    fun getSchedule() = getScheduleUseCase(Unit).cachedIn(viewModelScope)
}
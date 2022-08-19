package za.co.codevue.shared.domain.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import za.co.codevue.shared.models.domain.Schedule

interface IScheduleRepository {
    fun getSchedules(): Flow<PagingData<Schedule>>
}
package za.co.codevue.shared.domain.datasource

import androidx.paging.PagingSource
import za.co.codevue.shared.models.entities.ScheduleEntity

internal interface ILocalScheduleDataSource {
    suspend fun deleteSchedules()
    fun getSchedules(): PagingSource<Int, ScheduleEntity>
    suspend fun saveSchedules(schedules: List<ScheduleEntity>)
}
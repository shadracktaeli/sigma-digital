package za.co.codevue.shared.data.datasource

import androidx.paging.PagingSource
import za.co.codevue.shared.domain.datasource.ILocalScheduleDataSource
import za.co.codevue.shared.models.entities.ScheduleEntity
import za.co.codevue.shared.persistence.room.IScheduleDao

internal class LocalScheduleDataSourceImpl(
    private val dao: IScheduleDao
) : ILocalScheduleDataSource {
    override suspend fun deleteSchedules() = dao.deleteSchedules()

    override fun getSchedules(): PagingSource<Int, ScheduleEntity> = dao.getSchedules()

    override suspend fun saveSchedules(schedules: List<ScheduleEntity>) {
        return dao.saveSchedules(schedules)
    }
}
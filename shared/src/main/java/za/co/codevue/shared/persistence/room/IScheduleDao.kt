package za.co.codevue.shared.persistence.room

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import za.co.codevue.shared.models.entities.ScheduleEntity

@Dao
internal interface IScheduleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveSchedule(schedule: List<ScheduleEntity>)

    @Query(value = "SELECT * FROM schedules")
    suspend fun getScheduleList(): PagingSource<Int, ScheduleEntity>

    @Query(value = "DELETE FROM schedules")
    suspend fun deleteAllSchedules()
}
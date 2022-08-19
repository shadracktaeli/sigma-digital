package za.co.codevue.shared.persistence.room

import androidx.annotation.VisibleForTesting
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import za.co.codevue.shared.models.entities.ScheduleEntity

@Dao
internal interface IScheduleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveSchedules(schedule: List<ScheduleEntity>)

    @Query(value = """
        SELECT * FROM schedules 
        WHERE date(date) = date('now', '+1 day') 
        ORDER BY datetime(date) ASC
    """
    )
    fun getSchedules(): PagingSource<Int, ScheduleEntity>

    @VisibleForTesting(otherwise = VisibleForTesting.NONE)
    @Query(value = "SELECT * FROM schedules")
    suspend fun getSchedulesTest(): List<ScheduleEntity>

    @Query(value = "DELETE FROM schedules")
    suspend fun deleteSchedules()
}
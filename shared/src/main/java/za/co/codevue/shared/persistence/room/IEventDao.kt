package za.co.codevue.shared.persistence.room

import androidx.annotation.VisibleForTesting
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import za.co.codevue.shared.models.entities.EventEntity

@Dao
internal interface IEventDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveEvents(events: List<EventEntity>)

    @Query(value = "SELECT * FROM events WHERE id = :eventId")
    suspend fun getEvent(eventId: String): EventEntity

    @Query(value = "SELECT * FROM events")
    fun getEvents(): PagingSource<Int, EventEntity>

    @VisibleForTesting(otherwise = VisibleForTesting.NONE)
    @Query(value = "SELECT * FROM events")
    suspend fun getEventsTest(): List<EventEntity>

    @Query(value = "DELETE FROM events")
    suspend fun deleteEvents()
}
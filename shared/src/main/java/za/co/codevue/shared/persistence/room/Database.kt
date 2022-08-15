package za.co.codevue.shared.persistence.room

import androidx.room.Database
import androidx.room.RoomDatabase
import za.co.codevue.shared.models.entities.EventEntity
import za.co.codevue.shared.models.entities.ScheduleEntity

@Database(
    entities = [EventEntity::class, ScheduleEntity::class],
    version = DatabaseConstants.VERSION,
    exportSchema = false
)
internal abstract class Database : RoomDatabase() {
    abstract fun eventDao(): IEventDao
    abstract fun scheduleDao(): IScheduleDao
}
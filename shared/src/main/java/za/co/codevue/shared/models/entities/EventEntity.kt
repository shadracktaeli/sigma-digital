package za.co.codevue.shared.models.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "events")
internal data class EventEntity(
    @PrimaryKey
    val id: String,
    val date: String,
    val imageUrl: String,
    val subtitle: String,
    val title: String,
    val videoUrl: String
)

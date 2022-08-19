package za.co.codevue.shared.models.mappers

import za.co.codevue.shared.extensions.toDate
import za.co.codevue.shared.extensions.valueOrDefault
import za.co.codevue.shared.models.domain.Event
import za.co.codevue.shared.models.entities.EventEntity
import za.co.codevue.shared.models.network.EventDTO

/**
 * Maps [EventDTO] to [EventEntity]
 */
internal fun EventDTO.toEventEntity(): EventEntity {
    return EventEntity(
        id = this.id.valueOrDefault(),
        date = this.date.valueOrDefault(),
        imageUrl = this.imageUrl.valueOrDefault(),
        subtitle = this.subtitle.valueOrDefault(),
        title = this.title.valueOrDefault(),
        videoUrl = this.videoUrl.valueOrDefault()
    )
}

/**
 * Maps [EventEntity] to [Event]
 */
internal fun EventEntity.toEvent(): Event {
    return Event(
        id = this.id,
        date = this.date.toDate(),
        imageUrl = this.imageUrl,
        subtitle = this.subtitle,
        title = this.title,
        videoUrl = this.videoUrl
    )
}
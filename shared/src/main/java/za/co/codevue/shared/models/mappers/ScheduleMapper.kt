package za.co.codevue.shared.models.mappers

import za.co.codevue.shared.extensions.valueOrDefault
import za.co.codevue.shared.models.domain.Schedule
import za.co.codevue.shared.models.entities.ScheduleEntity
import za.co.codevue.shared.models.network.EventDTO

/**
 * Maps [EventDTO] to [ScheduleEntity]
 */
internal fun EventDTO.toScheduleEntity(): ScheduleEntity {
    return ScheduleEntity(
        id = this.id.valueOrDefault(),
        date = this.date.valueOrDefault(),
        imageUrl = this.imageUrl.valueOrDefault(),
        subtitle = this.subtitle.valueOrDefault(),
        title = this.title.valueOrDefault()
    )
}

/**
 * Maps [ScheduleEntity] to [Schedule]
 */
internal fun ScheduleEntity.toSchedule(): Schedule {
    return Schedule(
        id = this.id,
        date = this.date,
        imageUrl = this.imageUrl,
        subtitle = this.subtitle,
        title = this.title
    )
}
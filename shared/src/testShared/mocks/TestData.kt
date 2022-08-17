package mocks

import za.co.codevue.shared.models.entities.EventEntity
import za.co.codevue.shared.models.entities.ScheduleEntity
import za.co.codevue.shared.models.network.EventDTO

internal val DEFAULT_EVENT_DTO = EventDTO(
    id = "1",
    date = "2022-08-15T01:14:10.071Z",
    imageUrl = "image.com",
    subtitle = "Rick and Morty Space League",
    title = "Rick vs Morty",
    videoUrl = "video.com"
)

internal val DEFAULT_SCHEDULE_DTO = EventDTO(
    id = "1",
    date = "2022-08-15T01:14:10.071Z",
    imageUrl = "image.com",
    subtitle = "Rick and Morty Space League",
    title = "Rick vs Morty",
    videoUrl = null
)

internal val DEFAULT_EVENT_ENTITY = EventEntity(
    id = "1",
    date = "2022-08-15T01:14:10.071Z",
    imageUrl = "image.com",
    subtitle = "Rick and Morty Space League",
    title = "Rick vs Morty",
    videoUrl = "video.com"
)

internal val DEFAULT_SCHEDULE_ENTITY = ScheduleEntity(
    id = "1",
    date = "2022-08-15T01:14:10.071Z",
    imageUrl = "image.com",
    subtitle = "Rick and Morty Space League",
    title = "Rick vs Morty"
)
package mocks

import za.co.codevue.shared.models.domain.Event
import za.co.codevue.shared.models.domain.Schedule

val DEFAULT_EVENT = Event(
    id = "1",
    date = "2022-08-15T01:14:10.071Z",
    imageUrl = "image.com",
    subtitle = "Rick and Morty Space League",
    title = "Rick vs Morty",
    videoUrl = "video.com"
)

val DEFAULT_SCHEDULE = Schedule(
    id = "1",
    date = "2022-08-15T01:14:10.071Z",
    imageUrl = "image.com",
    subtitle = "Rick and Morty Space League",
    title = "Rick vs Morty"
)
package za.co.codevue.sigmadigital.ui.common

import za.co.codevue.shared.extensions.toPrettyDate
import za.co.codevue.shared.models.domain.Event
import za.co.codevue.shared.models.domain.Schedule

sealed class PagingListModel {
    class Data(
        val id: String,
        val date: String,
        val imageUrl: String,
        val subtitle: String,
        val title: String
    ) : PagingListModel() {
        constructor(event: Event) : this(
            event.id,
            event.date.toPrettyDate(),
            event.imageUrl,
            event.subtitle,
            event.title
        )

        constructor(schedule: Schedule) : this(
            schedule.id,
            schedule.date.toPrettyDate(),
            schedule.imageUrl,
            schedule.subtitle,
            schedule.title
        )
    }

    class Separator(val tag: String) : PagingListModel()
}

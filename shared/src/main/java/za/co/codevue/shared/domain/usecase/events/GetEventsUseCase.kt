package za.co.codevue.shared.domain.usecase.events

import androidx.paging.PagingData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import za.co.codevue.shared.di.IoDispatcher
import za.co.codevue.shared.domain.repository.IEventRepository
import za.co.codevue.shared.domain.usecase.FlowUseCase
import za.co.codevue.shared.models.domain.Event
import javax.inject.Inject

open class GetEventsUseCase @Inject constructor(
    private val repo: IEventRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : FlowUseCase<Unit, PagingData<Event>>(ioDispatcher) {
    override fun execute(parameters: Unit): Flow<PagingData<Event>> {
        return repo.getEvents()
    }
}
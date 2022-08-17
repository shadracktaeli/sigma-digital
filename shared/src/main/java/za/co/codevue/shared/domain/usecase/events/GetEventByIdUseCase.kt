package za.co.codevue.shared.domain.usecase.events

import kotlinx.coroutines.CoroutineDispatcher
import za.co.codevue.shared.di.IoDispatcher
import za.co.codevue.shared.domain.repository.IEventRepository
import za.co.codevue.shared.domain.usecase.UseCase
import za.co.codevue.shared.models.domain.Event
import javax.inject.Inject

open class GetEventByIdUseCase @Inject constructor(
    private val repo: IEventRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : UseCase<String, Event>(ioDispatcher) {
    override suspend fun execute(parameters: String): Event {
        return repo.getEvent(parameters)
    }
}
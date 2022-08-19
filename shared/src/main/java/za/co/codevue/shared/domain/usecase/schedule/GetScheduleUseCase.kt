package za.co.codevue.shared.domain.usecase.schedule

import androidx.paging.PagingData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import za.co.codevue.shared.di.IoDispatcher
import za.co.codevue.shared.domain.repository.IScheduleRepository
import za.co.codevue.shared.domain.usecase.FlowUseCase
import za.co.codevue.shared.models.domain.Schedule
import javax.inject.Inject

open class GetScheduleUseCase @Inject constructor(
    private val repo: IScheduleRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : FlowUseCase<Unit, PagingData<Schedule>>(ioDispatcher) {
    override fun execute(parameters: Unit): Flow<PagingData<Schedule>> {
        return repo.getSchedules()
    }
}
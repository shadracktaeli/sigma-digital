package za.co.codevue.shared.domain.usecase.events

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import mocks.DEFAULT_EVENT
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.kotlin.doAnswer
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import za.co.codevue.shared.domain.repository.IEventRepository
import za.co.codevue.shared.domain.statefuldata.data
import za.co.codevue.shared.domain.statefuldata.message
import za.co.codevue.shared.domain.statefuldata.success
import za.co.codevue.shared.domain.usecase.BaseUseCaseTest
import za.co.codevue.shared.exceptions.ServerException

@RunWith(JUnit4::class)
internal class GetEventByIdUseCaseTest : BaseUseCaseTest() {
    @Test
    fun `should return an event for id`() = runBlocking {
        // given
        val mockEvent = DEFAULT_EVENT
        val mockRepository = mock<IEventRepository> {
            onBlocking { getEvent(mockEvent.id) } doReturn mockEvent
        }
        val usecase = GetEventByIdUseCase(mockRepository, testCoroutineRule.dispatcher)
        // when
        val result = usecase(mockEvent.id)
        // then
        assertThat(result.success).isTrue()
        assertThat(result.data).isEqualTo(mockEvent)
    }

    @Test
    fun `should catch an exception and wrap it as an error state`() = runBlocking {
        // given
        val eventId = "1"
        val error = "Event not found"
        val mockRepository = mock<IEventRepository>()
        whenever(mockRepository.getEvent(eventId)) doAnswer {
            throw ServerException(errorMessage = error)
        }
        val usecase = GetEventByIdUseCase(mockRepository, testCoroutineRule.dispatcher)
        // when
        val result = usecase(eventId)
        // then
        assertThat(result.message).isEqualTo(error)
    }
}
package za.co.codevue.sigmadigital.ui.events.detail

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.runBlocking
import mocks.DEFAULT_EVENT
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.stub
import za.co.codevue.shared.domain.repository.IEventRepository
import za.co.codevue.shared.domain.usecase.events.GetEventByIdUseCase
import za.co.codevue.shared.models.domain.Event
import za.co.codevue.sigmadigital.ui.BaseViewModelTest

@RunWith(JUnit4::class)
class EventDetailViewModelTest : BaseViewModelTest() {
    private val mockRepository = mock<IEventRepository>()

    @Test
    fun `should update ui state to success when an event is returned`() {
        // given
        val eventId = DEFAULT_EVENT.id
        mockRepository.stub {
            onBlocking { getEvent(eventId) } doReturn DEFAULT_EVENT
        }
        val viewModel = createViewModel().apply {
            this.eventId = eventId
        }
        // when
        viewModel.getEvent()
        // then
        runBlocking {
            viewModel.uiState.test {
                assertThat((awaitItem() as EventDetailUiState.Success).event).isEqualTo(
                    DEFAULT_EVENT
                )
                cancelAndConsumeRemainingEvents()
            }
        }
    }

    @Test
    fun `should do nothing when eventId is null`() {
        // given
        mockRepository.stub {
            onBlocking { getEvent(any()) } doReturn DEFAULT_EVENT
        }
        val viewModel = createViewModel()
        // when
        viewModel.getEvent()
        // then
        runBlocking {
            viewModel.uiState.test {
                assertThat(awaitItem()).isInstanceOf(EventDetailUiState.Idle::class.java)
                cancelAndConsumeRemainingEvents()
            }
        }
    }

    @Test
    fun `should update ui state to error on failure`() {
        // given
        val eventId = "1"
        val viewModel = createViewModel(
            getEventByIdUseCase = FailingGetEventByIdUseCase(
                mockRepository,
                testCoroutineRule.dispatcher
            )
        ).apply {
            this.eventId = eventId
        }
        // when
        viewModel.getEvent()
        // then
        runBlocking {
            viewModel.uiState.test {
                awaitItem().also {
                    assertThat(it).isInstanceOf(EventDetailUiState.Error::class.java)
                    assertThat((it as EventDetailUiState.Error).message.isNotBlank()).isTrue()
                }
                cancelAndConsumeRemainingEvents()
            }
        }
    }

    private fun createViewModel(
        getEventByIdUseCase: GetEventByIdUseCase = GetEventByIdUseCase(
            mockRepository,
            testCoroutineRule.dispatcher
        )
    ) = EventDetailViewModel(getEventByIdUseCase)

    private class FailingGetEventByIdUseCase(
        repository: IEventRepository,
        dispatcher: CoroutineDispatcher
    ) : GetEventByIdUseCase(repository, dispatcher) {
        override suspend fun execute(parameters: String): Event {
            throw Exception("Error")
        }
    }
}
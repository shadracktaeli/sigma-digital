package za.co.codevue.shared.domain.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Rule
import util.TestCoroutineRule

internal abstract class BaseUseCaseTest {
    // Executes tasks in the Architecture Components in the same thread
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()
}
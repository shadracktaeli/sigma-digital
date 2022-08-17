package za.co.codevue.sigmadigital.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Rule
import util.TestCoroutineRule

abstract class BaseViewModelTest {
    // Executes tasks in the Architecture Components in the same thread
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()
}
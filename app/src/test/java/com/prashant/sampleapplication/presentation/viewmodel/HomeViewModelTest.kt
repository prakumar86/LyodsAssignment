package com.prashant.sampleapplication.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.prashant.sampleapplication.CoroutineRuleTest
import com.prashant.sampleapplication.domain.models.BaseResponse
import com.prashant.sampleapplication.domain.models.ResourceInfo
import com.prashant.sampleapplication.presentation.viewstate.UIState
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class HomeViewModelTest {

    // Set the main coroutines dispatcher for unit testing.
    @get:Rule
    var mainCoroutineRule = CoroutineRuleTest()

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()


    private val mockResourceList: List<ResourceInfo> = mockk()

    private val viewStateObserver: FlowCollector<UIState<List<ResourceInfo>>> = mockk()

    private val mockException: Exception = mockk()

    private val mockUseCase: ResourceListUseCase = mockk()

    private lateinit var viewModel: HomeViewModel

    private val fakeSuccessFlow = flow {
        emit(BaseResponse.OnSuccess(mockResourceList))
    }

    private val fakeFailureFlow = flow {
        emit(BaseResponse.OnFailure(mockException))
    }


    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        every { mockException.message } returns "Exception!!"
        viewModel = HomeViewModel(mockUseCase)
    }


    @Test
    fun `WHEN getAllListData called THEN succes should called in sequence`() {
        runBlockingTest {
            coEvery { mockUseCase.getResourceList() } returns fakeSuccessFlow
            launch {  viewModel.getResources().collect { viewStateObserver } }
            viewModel.fetchResourcesFromApi()


            verify {
                with(viewStateObserver) {
                    runBlockingTest {
                        emit(UIState.Loading(true))
                        emit(UIState.Success(mockResourceList))
                        emit(UIState.Loading(false))
                    }
                }
            }
        }

    }

    @Test
    fun `WHEN network failure called THEN failure should called`() {
        runBlockingTest {
            coEvery { mockUseCase.getResourceList() } returns fakeFailureFlow
            launch { viewModel.getResources().collect { viewStateObserver } }
            viewModel.fetchResourcesFromApi()

            verify {
                with(viewStateObserver) {
                    runBlockingTest {
                        emit(UIState.Loading(true))
                        emit(UIState.Failure(mockException))
                        emit(UIState.Loading(false))
                    }
                }
            }
        }
    }
}
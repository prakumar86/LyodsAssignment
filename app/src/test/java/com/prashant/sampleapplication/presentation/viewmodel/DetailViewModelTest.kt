package com.prashant.sampleapplication.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.prashant.sampleapplication.CoroutineRuleTest
import com.prashant.sampleapplication.domain.models.BaseResponse
import com.prashant.sampleapplication.domain.models.ResourceDetailInfo
import com.prashant.sampleapplication.domain.usecase.detail.ResourceDetailUseCase
import com.prashant.sampleapplication.presentation.viewstate.UIState
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class DetailViewModelTest {

    // Set the main coroutines dispatcher for unit testing.
    @get:Rule
    var mainCoroutineRule = CoroutineRuleTest()

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()


    private val mockUserDetail: ResourceDetailInfo = mockk()

    private val viewStateObserver: Observer<UIState<ResourceDetailInfo>> = mockk()

    private val mockException: Exception = mockk()

    private val mockUseCase: ResourceDetailUseCase = mockk()

    private lateinit var viewModel: DetailViewModel

    private val fakeSuccessFlow = flow {
        emit(BaseResponse.OnSuccess(mockUserDetail))
    }

    private val fakeFailureFlow = flow {
        emit(BaseResponse.OnFailure(mockException))
    }


    @Before
    fun setUp(){
        MockKAnnotations.init(this)
        every { mockException.message } returns "Exception!!"
        viewModel = DetailViewModel(mockUseCase)
    }


    @Test
    fun `WHEN getUserDetail called THEN succes should called in sequence`() {
        runBlockingTest {
            val user: ResourceDetailInfo = mockk()
            coEvery { mockUseCase.invoke(1) } returns fakeSuccessFlow
            launch { viewModel.getResourcesDetail().collect { viewStateObserver } }
            viewModel.getResourceDetail(1)

            verifyOrder {
                with(viewStateObserver) {
                    onChanged(UIState.Loading(true))
                    onChanged(UIState.Success(user))
                    onChanged(UIState.Loading(false))
                }
            }
        }

    }

    @Test
    fun `WHEN network failure called THEN failure should called`(){

        runBlockingTest {
            coEvery { mockUseCase.invoke(1) } returns fakeFailureFlow
            launch { viewModel.getResourcesDetail().collect { viewStateObserver } }
            viewModel.getResourceDetail(1)

            verifyOrder {
                viewStateObserver.onChanged(UIState.Loading(true))
                viewStateObserver.onChanged(UIState.Failure(mockException))
                viewStateObserver.onChanged(UIState.Loading(false))
            }
        }
    }
}
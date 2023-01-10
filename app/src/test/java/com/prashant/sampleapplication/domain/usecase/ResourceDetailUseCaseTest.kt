package com.prashant.sampleapplication.domain.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.prashant.sampleapplication.CoroutineRuleTest
import com.prashant.sampleapplication.domain.models.BaseResponse
import com.prashant.sampleapplication.domain.models.ResourceDetailInfo
import com.prashant.sampleapplication.domain.repository.detail.ResourceDetailRepository
import com.prashant.sampleapplication.domain.usecase.detail.ResourceDetailUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class ResourceDetailUseCaseTest {
    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = CoroutineRuleTest()

    @Mock
    private lateinit var repository: ResourceDetailRepository

    private lateinit var useCase: ResourceDetailUseCase


    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testFetchDataSuccess() = runBlocking {
        val userDetailModel = ResourceDetailInfo(
            "john",
            "#fffffff",
            2022
        )
        val flow = flow {
            emit(BaseResponse.OnSuccess(userDetailModel))
        }
        Mockito.`when`(repository.getResourceDetail(1)).thenReturn(flow)
        useCase = ResourceDetailUseCase(repository)
        val response = useCase.invoke(1)
        Assert.assertTrue(response is BaseResponse.OnSuccess<*>)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testFetchDataFail() = runBlocking {
        val flow = flow {
            emit(BaseResponse.OnFailure(Throwable("Error")))
        }
        Mockito.`when`(repository.getResourceDetail(1)).thenReturn(flow)
        useCase = ResourceDetailUseCase(repository)
        val response = useCase.invoke(1).first()
        Assert.assertTrue( response is BaseResponse.OnFailure)
    }


}
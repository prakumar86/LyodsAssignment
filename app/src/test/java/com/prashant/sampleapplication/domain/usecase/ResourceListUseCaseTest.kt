package com.prashant.sampleapplication.domain.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.prashant.sampleapplication.CoroutineRuleTest
import com.prashant.sampleapplication.domain.models.BaseResponse
import com.prashant.sampleapplication.domain.models.ResourceInfo
import com.prashant.sampleapplication.domain.repository.home.ResourceListRepository
import com.prashant.sampleapplication.domain.usecase.home.ResourceListUseCaseImpl
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
class ResourceListUseCaseTest {
    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = CoroutineRuleTest()

    @Mock
    private lateinit var repository: ResourceListRepository

    private lateinit var useCase: ResourceListUseCaseImpl


    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testFetchDataSuccess() = runBlocking {
        val flow = flow {
            emit(BaseResponse.OnSuccess(emptyList<ResourceInfo>()))
        }
        Mockito.`when`(repository.getResourceList()).thenReturn(flow)
        useCase = ResourceListUseCaseImpl(repository)
        val response = useCase.getResourceList().first()
        Assert.assertTrue(response is BaseResponse.OnSuccess<*>)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testFetchDataFail() = runBlocking {
        val flow = flow {
            emit(BaseResponse.OnFailure(Throwable("Error")))
        }
        Mockito.`when`(repository.getResourceList()).thenReturn(flow)
        useCase = ResourceListUseCaseImpl(repository)
        val response = useCase.getResourceList().first()
        Assert.assertTrue(response is BaseResponse.OnFailure)
    }
}
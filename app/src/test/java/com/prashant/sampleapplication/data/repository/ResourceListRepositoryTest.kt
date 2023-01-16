package com.prashant.sampleapplication.data.repository

import com.prashant.sampleapplication.CoroutineRuleTest
import com.prashant.sampleapplication.data.mapper.ResourceListMapper
import com.prashant.sampleapplication.data.repository.home.ResourceListRepositoryImpl
import com.prashant.sampleapplication.data.response.ResourceListResponse
import com.prashant.sampleapplication.domain.repository.home.ResourceListRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import junit.framework.TestCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response

@ExperimentalCoroutinesApi
class ResourceListRepositoryTest {
    private lateinit var dataRepository: ResourceListRepository

    @MockK
    private lateinit var dataService: IApiService

    @MockK
    private lateinit var listAPIResponseMapper: ResourceListMapper

    @get:Rule
    val mainCoroutineRule = CoroutineRuleTest()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(Dispatchers.IO)
        dataRepository = ResourceListRepositoryImpl(dataService, listAPIResponseMapper)
    }

    @Test
    fun testAPIResourcesListSuccess() {
        runBlockingTest {
            val response = mockk<Response<ResourceListResponse>>()
            coEvery { response.isSuccessful } returns true
            val usersListResponse = mockk<ResourceListResponse>()
            coEvery { response.body() } returns usersListResponse
            coEvery { listAPIResponseMapper.mapResources(usersListResponse) } returns listOf(
                mockk()
            )
            coEvery { dataService.getResourceList() } returns response.body()!!
            TestCase.assertEquals(1, dataRepository.getResourceList().first())
        }
    }

    @Test
    fun testAPIResourcesListFail() {
        runBlockingTest {
            val response = mockk<Response<ResourceListResponse>>()
            coEvery { response.isSuccessful } returns false
            coEvery { response.message() } returns "error"
            coEvery { dataService.getResourceList() } returns response.body()!!
            TestCase.assertEquals("error", dataRepository.getResourceList().first())
        }
    }
}
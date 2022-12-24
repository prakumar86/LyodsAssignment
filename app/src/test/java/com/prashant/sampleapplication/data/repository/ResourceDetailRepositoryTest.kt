package com.prashant.sampleapplication.data.repository

import com.prashant.sampleapplication.CoroutineRuleTest
import com.prashant.sampleapplication.data.mapper.ResourceDetailMapper
import com.prashant.sampleapplication.data.response.ResourceDetailResponse
import com.prashant.sampleapplication.domain.models.ResourceDetailInfo
import com.prashant.sampleapplication.domain.repository.detail.ResourceDetailRepository
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test
import retrofit2.Response

@ExperimentalCoroutinesApi
class ResourceDetailRepositoryTest {
    private lateinit var dataRepository: ResourceDetailRepository

    @MockK
    private lateinit var dataService: IApiService

    @MockK
    private lateinit var detailAPIResponseMapper: ResourceDetailMapper

    @get:Rule
    val mainCoroutineRule = CoroutineRuleTest()

    @Test
    fun testAPIGetUserByIdSuccess() {
        runBlockingTest {
            val user = mockk<ResourceDetailResponse>()
            val userInfo = mockk<ResourceDetailInfo>()
            val response = mockk<Response<ResourceDetailResponse>>()
            coEvery { response.isSuccessful } returns true
            coEvery { response.body() } returns user
            coEvery { detailAPIResponseMapper.mapResource(user) } returns userInfo
            coEvery { dataService.getResourceDetail(1) } returns response.body()!!
            TestCase.assertEquals(userInfo, dataRepository.getResourceDetail(1).first())
        }
    }

    @Test
    fun testAPIGetUserByIdFail() {
        runBlockingTest {
            val response = mockk<Response<ResourceDetailResponse>>()
            coEvery { response.isSuccessful } returns false
            coEvery { response.message() } returns "error"
            coEvery { dataService.getResourceDetail(1) } returns response.body()!!
            TestCase.assertEquals("error", dataRepository.getResourceDetail(1).first())
        }
    }
}
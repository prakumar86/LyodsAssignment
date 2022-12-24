package com.prashant.sampleapplication.data.service

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.gson.GsonBuilder
import com.prashant.sampleapplication.CoroutineRuleTest
import com.prashant.sampleapplication.data.repository.IApiService
import com.prashant.sampleapplication.data.response.ResourceDetailResponse
import com.prashant.sampleapplication.presentation.viewmodel.MockFileReader
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockWebServer
import org.junit.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@ExperimentalCoroutinesApi
class ApiServiceTest {

    lateinit var mockWebServer: MockWebServer
    @get:Rule
    var mainCoroutineRule = CoroutineRuleTest()

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var webClient: IApiService

    @Before
    fun initService(){
        mockWebServer = MockWebServer()
        mockWebServer.start()
        val gson = GsonBuilder()
            .setLenient()
            .create()
        webClient = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(IApiService::class.java)
    }

    @Test
    fun testUserListFromServer() {
        runBlocking {
            val json =  MockFileReader().getResponseFromJson("/resource.json")
            val gson = GsonBuilder()
                .setLenient()
                .create()
            val resResponse = json.let { gson.fromJson(it,ResourceDetailResponse::class.java) }
            Assert.assertEquals(resResponse?.data, "john")
        }
    }

    @After
    fun tearDown(){
        mockWebServer.shutdown()
    }
}
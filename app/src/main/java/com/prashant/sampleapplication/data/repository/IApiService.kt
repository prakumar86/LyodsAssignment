package com.prashant.sampleapplication.data.repository

import com.prashant.sampleapplication.data.response.ResourceDetailResponse
import com.prashant.sampleapplication.data.response.ResourceListResponse
import retrofit2.http.GET
import retrofit2.http.Path

/*
* Interface to list all the methoeds of the api call having all params and type of call
* */
interface IApiService {
    @GET("api/{resource}")
    suspend fun getResourceList(): ResourceListResponse

    @GET("api/{resource}/{id}")
    suspend fun getResourceDetail(@Path("id") id: Int): ResourceDetailResponse

}
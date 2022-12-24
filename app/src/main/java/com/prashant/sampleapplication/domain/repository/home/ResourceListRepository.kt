package com.prashant.sampleapplication.domain.repository.home

import com.prashant.sampleapplication.domain.models.ResourceInfo
import com.prashant.sampleapplication.domain.models.BaseResponse
import kotlinx.coroutines.flow.Flow

/*
* contract for resource list (home) repository
* */
interface ResourceListRepository {
    fun getResourceList(): Flow<BaseResponse<List<ResourceInfo>>>
}

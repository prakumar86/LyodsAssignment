package com.prashant.sampleapplication.domain.usecase.home

import com.prashant.sampleapplication.domain.models.ResourceInfo
import com.prashant.sampleapplication.domain.models.BaseResponse
import kotlinx.coroutines.flow.Flow

/*
* contract for resource list (home) usecase
* */
interface ResourceListUseCase {
   fun getResourceList(): Flow<BaseResponse<List<ResourceInfo>>>
}
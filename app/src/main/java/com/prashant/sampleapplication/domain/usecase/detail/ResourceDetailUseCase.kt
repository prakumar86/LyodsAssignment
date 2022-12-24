package com.prashant.sampleapplication.domain.usecase.detail

import com.prashant.sampleapplication.domain.models.BaseResponse
import com.prashant.sampleapplication.domain.models.ResourceDetailInfo
import kotlinx.coroutines.flow.Flow

/*
* contract for resource detail use case
* */
interface ResourceDetailUseCase {
    fun getResourceDetail(id: Int): Flow<BaseResponse<ResourceDetailInfo>>
}
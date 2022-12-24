package com.prashant.sampleapplication.domain.repository.detail

import com.prashant.sampleapplication.domain.models.BaseResponse
import com.prashant.sampleapplication.domain.models.ResourceDetailInfo
import kotlinx.coroutines.flow.Flow

/*
* contract for resource detail repository
* */
interface ResourceDetailRepository {
    fun getResourceDetail(id: Int) : Flow<BaseResponse<ResourceDetailInfo>>
}
package com.prashant.sampleapplication.data.repository.detail

import com.prashant.sampleapplication.data.repository.IApiService
import com.prashant.sampleapplication.data.response.ResourceDetailResponse
import com.prashant.sampleapplication.domain.mapper.IMapperService
import com.prashant.sampleapplication.domain.models.BaseResponse
import com.prashant.sampleapplication.domain.models.ResourceDetailInfo
import com.prashant.sampleapplication.domain.repository.detail.ResourceDetailRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/*
* Implementation class for resource detail repository contract
* */
class ResourceDetailRepositoryImpl @Inject constructor(
    private val apiService: IApiService,
    private val mapper: IMapperService<ResourceDetailResponse, ResourceDetailInfo>, private val dispatcher: CoroutineDispatcher
) : ResourceDetailRepository {
    override fun getResourceDetail(id: Int) : Flow<BaseResponse<ResourceDetailInfo>> {
        return flow {
            try {
                val responseData = apiService.getResourceDetail(id)
                val userData = mapper.map(responseData)
                emit(BaseResponse.OnSuccess(userData))
            } catch (e: Exception) {
                emit(BaseResponse.OnFailure(e))
            }
        }.flowOn(dispatcher)
    }
}
package com.prashant.sampleapplication.data.repository.home

import com.prashant.sampleapplication.domain.mapper.ResourceListMapper
import com.prashant.sampleapplication.data.repository.IApiService
import com.prashant.sampleapplication.domain.models.BaseResponse
import com.prashant.sampleapplication.domain.models.ResourceInfo
import com.prashant.sampleapplication.domain.repository.home.ResourceListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
/*
* Implementation class for resource list repository contract
* */
class ResourceListRepositoryImpl @Inject constructor(
    private val apiService: IApiService,
    private val mapper: ResourceListMapper
) : ResourceListRepository {
    override fun getResourceList(): Flow<BaseResponse<List<ResourceInfo>>> {
        return flow {
            try {
                val responseData = apiService.getResourceList()
                val resourceData = mapper.mapResources(responseData)
                emit(BaseResponse.OnSuccess(resourceData))
            } catch (e: Exception) {
                emit(BaseResponse.OnFailure(e))
            }
        }
    }

}
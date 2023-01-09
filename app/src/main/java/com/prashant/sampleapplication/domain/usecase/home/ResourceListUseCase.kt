package com.prashant.sampleapplication.domain.usecase.home

import com.prashant.sampleapplication.domain.models.ResourceInfo
import com.prashant.sampleapplication.domain.models.BaseResponse
import com.prashant.sampleapplication.domain.repository.home.ResourceListRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/*
* Implementation class for resource list usecase contract
* */
class ResourceListUseCase @Inject constructor(
    private val getResourceListRepository: ResourceListRepository) {
    operator fun invoke(): Flow<BaseResponse<List<ResourceInfo>>> {
        return getResourceListRepository.getResourceList()
    }

}
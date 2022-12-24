package com.prashant.sampleapplication.domain.usecase.home

import com.prashant.sampleapplication.domain.models.ResourceInfo
import com.prashant.sampleapplication.domain.models.BaseResponse
import com.prashant.sampleapplication.domain.repository.home.ResourceListRepository
import com.prashant.sampleapplication.domain.usecase.home.ResourceListUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/*
* Implementation class for resource list usecase contract
* */
class ResourceListUseCaseImpl @Inject constructor(
    private val getResourceListRepository: ResourceListRepository
) : ResourceListUseCase {
    override fun getResourceList(): Flow<BaseResponse<List<ResourceInfo>>> {
        return getResourceListRepository.getResourceList()
    }

}
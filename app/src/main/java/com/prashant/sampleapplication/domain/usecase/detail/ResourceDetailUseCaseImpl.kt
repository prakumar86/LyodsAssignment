package com.prashant.sampleapplication.domain.usecase.detail

import com.prashant.sampleapplication.domain.models.BaseResponse
import com.prashant.sampleapplication.domain.models.ResourceDetailInfo
import com.prashant.sampleapplication.domain.repository.detail.ResourceDetailRepository
import com.prashant.sampleapplication.domain.usecase.detail.ResourceDetailUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
/*
* Implementation class for resource detail usecase contract
* */
class ResourceDetailUseCaseImpl @Inject constructor(
    private val getResourceDetailRepository: ResourceDetailRepository
) :ResourceDetailUseCase {
    override fun getResourceDetail(id: Int): Flow<BaseResponse<ResourceDetailInfo>> {
       return getResourceDetailRepository.getResourceDetail(id)
    }
}
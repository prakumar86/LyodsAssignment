package com.prashant.sampleapplication.data.mapper

import com.prashant.sampleapplication.data.response.ResourceDetailResponse
import com.prashant.sampleapplication.domain.mapper.IMapperService
import com.prashant.sampleapplication.domain.models.ResourceDetailInfo

/*
* Class to map the resource detail from api end response to data module model
* */
class ResourceDetailMapper : IMapperService<ResourceDetailResponse, ResourceDetailInfo> {
    override fun map(responseData: ResourceDetailResponse): ResourceDetailInfo {
        return with(responseData.data) {
            ResourceDetailInfo(
                name = name,
                color = color,
                year = year
            )
        }
    }
}
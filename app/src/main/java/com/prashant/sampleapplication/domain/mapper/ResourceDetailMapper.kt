package com.prashant.sampleapplication.domain.mapper

import com.prashant.sampleapplication.data.response.ResourceDetailResponse
import com.prashant.sampleapplication.domain.models.ResourceDetailInfo
/*
* Class to map the resource detail from api end response to data module model
* */
class ResourceDetailMapper {
    fun mapResource(responseData: ResourceDetailResponse): ResourceDetailInfo {
        return with(responseData.data) {
            ResourceDetailInfo(
                name = name,
                color = color,
                year = year
            )
        }
    }
}
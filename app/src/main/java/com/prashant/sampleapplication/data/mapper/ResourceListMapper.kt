package com.prashant.sampleapplication.data.mapper

import com.prashant.sampleapplication.data.response.ResourceListResponse
import com.prashant.sampleapplication.domain.mapper.IMapperService
import com.prashant.sampleapplication.domain.models.ResourceInfo
/*
* Class to map the resource list items from api end response to data module model
* */
class ResourceListMapper: IMapperService<ResourceListResponse, List<ResourceInfo>> {
    override fun map(responseData: ResourceListResponse): List<ResourceInfo> {
        return responseData.data.map { resourceResponse -> with(resourceResponse) {
            ResourceInfo(
                id= id,
                name=name,
                color=color,
                year=year)
        } }
    }
}

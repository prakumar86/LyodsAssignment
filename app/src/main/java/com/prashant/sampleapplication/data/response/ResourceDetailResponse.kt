package com.prashant.sampleapplication.data.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/*
* data class for resource detail base response from api
* */
data class ResourceDetailResponse(
    @SerializedName("data")
    @Expose
    val data: ResourceDetail,
)

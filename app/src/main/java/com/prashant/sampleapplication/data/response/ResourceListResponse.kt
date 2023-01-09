package com.prashant.sampleapplication.data.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/*
* data class for resource list base respone from api
* */
data class ResourceListResponse(
    @SerializedName("page")
    @Expose
    val page: Int,
    @SerializedName("per_page")
    @Expose
    val per_page: Int,
    @SerializedName("total")
    @Expose
    val total: Int?,
    @SerializedName("total_pages")
    @Expose
    val total_pages: Int,
    @SerializedName("data")
    @Expose
    val data: ArrayList<ResourceResponse>,

)

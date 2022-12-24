package com.prashant.sampleapplication.data.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/*
* data class for resource list object from api
* */
data class ResourceResponse(
    @SerializedName("id")
    @Expose
    val id: Int? = null,
    @SerializedName("name")
    @Expose
    val name: String? = null,
    @SerializedName("year")
    @Expose
    val year: Int? = null,
    @SerializedName("color")
    @Expose
    val color: String? = null,
    @SerializedName("pantone_value")
    @Expose
    val pantone_value: String? = null,
)

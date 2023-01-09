package com.prashant.sampleapplication.data.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/*
* data class for resource detail from api
* */
data class ResourceDetail (
    @SerializedName("id")
    @Expose
    val id: Int,
    @SerializedName("name")
    @Expose
    val name: String,
    @SerializedName("year")
    @Expose
    val year: Int,
    @SerializedName("color")
    @Expose
    val color: String,
    @SerializedName("pantone_value")
    @Expose
    val pantone_value: String,
        )
package com.prashant.sampleapplication.domain.models
/*
* class which is the base response to all responses it is a wrapper for all of the responses
* */
sealed class BaseResponse<out T : Any> {
    data class OnSuccess<out T : Any>(val data: T) : BaseResponse<T>()
    data class OnFailure(val throwable: Throwable) : BaseResponse<Nothing>()
}
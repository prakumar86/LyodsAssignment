package com.prashant.sampleapplication.presentation.viewstate

/*
* class to manage the state of view while we have to call the api
* */
sealed class UIState<out T : Any> {

    // loading state of view
    data class Loading(val isLoading: Boolean) : UIState<Nothing>()
    //Success state of view
    data class Success<out T : Any>(val output: T) : UIState<T>()
    //Failure state of view
    data class Failure(val throwable: Throwable) : UIState<Nothing>()

}
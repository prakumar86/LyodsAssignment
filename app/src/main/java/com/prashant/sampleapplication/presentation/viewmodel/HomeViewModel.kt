package com.prashant.sampleapplication.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prashant.sampleapplication.domain.models.ResourceInfo
import com.prashant.sampleapplication.domain.models.BaseResponse
import com.prashant.sampleapplication.domain.usecase.home.ResourceListUseCase
import com.prashant.sampleapplication.presentation.viewstate.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

/*
* view model class for the home screen which is used to communicate between view and use case
* */
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getResourceListUseCase: ResourceListUseCase
) : ViewModel() {
    private val resourceListFlow =
        MutableStateFlow<ViewState<List<ResourceInfo>>>(ViewState.Loading(true))

    fun getResources(): StateFlow<ViewState<List<ResourceInfo>>> = resourceListFlow
    fun fetchResourcesFromApi() {
        viewModelScope.launch {
            getResourceListUseCase.getResourceList().collect {
                when (it) {
                    is BaseResponse.OnSuccess -> {
                        it.data.let { resources ->
                            resourceListFlow.value = ViewState.Success(resources)
                        }
                    }
                    is BaseResponse.OnFailure -> {
                           resourceListFlow.value=ViewState.Failure(it.throwable)
                    }
                }
            }
        }
    }

}
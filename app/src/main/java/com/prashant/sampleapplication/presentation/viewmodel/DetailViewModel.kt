package com.prashant.sampleapplication.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prashant.sampleapplication.domain.models.BaseResponse
import com.prashant.sampleapplication.domain.models.ResourceDetailInfo
import com.prashant.sampleapplication.domain.models.ResourceInfo
import com.prashant.sampleapplication.domain.usecase.detail.ResourceDetailUseCase
import com.prashant.sampleapplication.presentation.viewstate.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

/*
* view model class for the detail screen which is used to communicate between view and use case
* */
@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getResourceDetailUseCase: ResourceDetailUseCase
) : ViewModel() {

    private val resourceDetailFlow =
        MutableStateFlow<ViewState<ResourceDetailInfo>>(ViewState.Loading(true))

    fun getResourcesDetail(): StateFlow<ViewState<ResourceDetailInfo>> = resourceDetailFlow

    fun getResourceDetail(id: Int) {
        viewModelScope.launch {
            getResourceDetailUseCase.getResourceDetail(id).collect {
                when (it) {
                    is BaseResponse.OnSuccess -> {
                            it.data.let { resource ->
                                resourceDetailFlow.value= ViewState.Success(resource)
                            }
                    }
                    is BaseResponse.OnFailure ->{
                        resourceDetailFlow.value= ViewState.Failure(it.throwable)
                    }
                }
            }
        }

    }
}
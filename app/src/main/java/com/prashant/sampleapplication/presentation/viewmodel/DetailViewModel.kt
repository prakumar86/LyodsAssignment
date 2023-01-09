package com.prashant.sampleapplication.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prashant.sampleapplication.domain.models.BaseResponse
import com.prashant.sampleapplication.domain.models.ResourceDetailInfo
import com.prashant.sampleapplication.domain.usecase.detail.ResourceDetailUseCase
import com.prashant.sampleapplication.presentation.viewstate.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

/*
* view model class for the detail screen which is used to communicate between view and use case
* */
@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getResourceDetailUseCase: ResourceDetailUseCase
) : ViewModel() {

    private val resourceDetailFlow =
        MutableStateFlow<UIState<ResourceDetailInfo>>(UIState.Loading(true))

    fun getResourcesDetail(): StateFlow<UIState<ResourceDetailInfo>> = resourceDetailFlow

    fun getResourceDetail(id: Int) {
        viewModelScope.launch {
            getResourceDetailUseCase(id).collect {
                when (it) {
                    is BaseResponse.OnSuccess -> {
                            it.data.let { resource ->
                                resourceDetailFlow.value= UIState.Success(resource)
                            }
                    }
                    is BaseResponse.OnFailure ->{
                        resourceDetailFlow.value= UIState.Failure(it.throwable)
                    }
                }
            }
        }

    }
}
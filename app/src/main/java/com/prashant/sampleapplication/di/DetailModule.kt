package com.prashant.sampleapplication.di

import com.prashant.sampleapplication.data.mapper.ResourceDetailMapper
import com.prashant.sampleapplication.data.repository.IApiService
import com.prashant.sampleapplication.data.repository.detail.ResourceDetailRepositoryImpl
import com.prashant.sampleapplication.domain.usecase.detail.ResourceDetailUseCaseImpl
import com.prashant.sampleapplication.domain.repository.detail.ResourceDetailRepository
import com.prashant.sampleapplication.domain.usecase.detail.ResourceDetailUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

/*
* module to provide all the objects require to detail screen
* */
@Module
@InstallIn(ViewModelComponent::class)
class DetailModule {
    @Provides
    fun provideResourceDetailUseCase(apiservice: IApiService): ResourceDetailUseCase =
        ResourceDetailUseCaseImpl(provideResourceDetailRepository(apiservice))

    @Provides
    fun provideResourceDetailRepository(apiservice: IApiService): ResourceDetailRepository =
        ResourceDetailRepositoryImpl(apiservice, provideResourceDetailMapper())

    private fun provideResourceDetailMapper(): ResourceDetailMapper = ResourceDetailMapper()
}
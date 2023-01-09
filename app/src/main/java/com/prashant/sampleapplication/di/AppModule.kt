package com.prashant.sampleapplication.di

import com.prashant.sampleapplication.domain.mapper.ResourceDetailMapper
import com.prashant.sampleapplication.data.repository.IApiService
import com.prashant.sampleapplication.data.repository.detail.ResourceDetailRepositoryImpl
import com.prashant.sampleapplication.data.repository.home.ResourceListRepositoryImpl
import com.prashant.sampleapplication.domain.mapper.ResourceListMapper
import com.prashant.sampleapplication.domain.repository.detail.ResourceDetailRepository
import com.prashant.sampleapplication.domain.repository.home.ResourceListRepository
import com.prashant.sampleapplication.domain.usecase.detail.ResourceDetailUseCase
import com.prashant.sampleapplication.domain.usecase.home.ResourceListUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

/*
* module to provide all the objects require to detail screen
* */
@Module
@InstallIn(ViewModelComponent::class)
class AppModule {
    @Provides
    fun provideResourceListUseCase(apiservice: IApiService): ResourceListUseCase =
        ResourceListUseCase(provideResourceListRepository(apiservice))

    @Provides
    fun provideResourceListRepository(apiservice: IApiService): ResourceListRepository =
        ResourceListRepositoryImpl(apiservice, provideResourceListMapper())

    private fun provideResourceListMapper(): ResourceListMapper = ResourceListMapper()

    @Provides
    fun provideResourceDetailUseCase(apiservice: IApiService): ResourceDetailUseCase =
        ResourceDetailUseCase(provideResourceDetailRepository(apiservice))

    @Provides
    fun provideResourceDetailRepository(apiservice: IApiService): ResourceDetailRepository =
        ResourceDetailRepositoryImpl(apiservice, provideResourceDetailMapper())

    private fun provideResourceDetailMapper(): ResourceDetailMapper = ResourceDetailMapper()
}
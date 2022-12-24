package com.prashant.sampleapplication.di

import com.prashant.sampleapplication.data.mapper.ResourceListMapper
import com.prashant.sampleapplication.data.repository.IApiService
import com.prashant.sampleapplication.data.repository.home.ResourceListRepositoryImpl
import com.prashant.sampleapplication.domain.usecase.home.ResourceListUseCase
import com.prashant.sampleapplication.domain.usecase.home.ResourceListUseCaseImpl
import com.prashant.sampleapplication.domain.repository.home.ResourceListRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

/*
* module to provide all the objects require to home screen
* */
@Module
@InstallIn(ViewModelComponent::class)
class HomeModule {

    @Provides
    fun provideResourceListUseCase(apiservice: IApiService): ResourceListUseCase =
        ResourceListUseCaseImpl(provideResourceListRepository(apiservice))

    @Provides
    fun provideResourceListRepository(apiservice: IApiService): ResourceListRepository =
        ResourceListRepositoryImpl(apiservice, provideResourceListMapper())

    private fun provideResourceListMapper(): ResourceListMapper = ResourceListMapper()
}
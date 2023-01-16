package com.prashant.sampleapplication.di

import com.prashant.sampleapplication.data.mapper.ResourceDetailMapper
import com.prashant.sampleapplication.data.repository.IApiService
import com.prashant.sampleapplication.data.repository.detail.ResourceDetailRepositoryImpl
import com.prashant.sampleapplication.data.repository.home.ResourceListRepositoryImpl
import com.prashant.sampleapplication.data.mapper.ResourceListMapper
import com.prashant.sampleapplication.data.response.ResourceDetailResponse
import com.prashant.sampleapplication.data.response.ResourceListResponse
import com.prashant.sampleapplication.domain.mapper.IMapperService
import com.prashant.sampleapplication.domain.models.ResourceDetailInfo
import com.prashant.sampleapplication.domain.models.ResourceInfo
import com.prashant.sampleapplication.domain.repository.detail.ResourceDetailRepository
import com.prashant.sampleapplication.domain.repository.home.ResourceListRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.coroutines.CoroutineDispatcher

/*
* module to provide all the objects require to detail screen
* */
@Module
@InstallIn(ViewModelComponent::class)
class AppModule {

    @Provides
    fun provideResourceListRepository(
        apiservice: IApiService,
        ioDispatcher: CoroutineDispatcher
    ): ResourceListRepository =
        ResourceListRepositoryImpl(apiservice, provideResourceListMapper(), ioDispatcher)

    private fun provideResourceListMapper(): IMapperService<ResourceListResponse, List<ResourceInfo>> =
        ResourceListMapper()

    @Provides
    fun provideResourceDetailRepository(
        apiservice: IApiService,
        ioDispatcher: CoroutineDispatcher
    ): ResourceDetailRepository =
        ResourceDetailRepositoryImpl(apiservice, provideResourceDetailMapper(), ioDispatcher)

    private fun provideResourceDetailMapper(): IMapperService<ResourceDetailResponse, ResourceDetailInfo> =
        ResourceDetailMapper()
}
package com.prashant.sampleapplication.di

import com.google.gson.GsonBuilder
import com.prashant.sampleapplication.BuildConfig
import com.prashant.sampleapplication.data.repository.IApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/*
* module to provide all the objects require for networking to make api call
* */
@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Provides
    fun getRetroService(): Retrofit {
        val gson = GsonBuilder()
            .setLenient()
            .create()
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(5, TimeUnit.MINUTES)
            .readTimeout(30, TimeUnit.SECONDS).build()
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL_DOMAIN)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()
        return retrofit
    }

    @Provides
    @Singleton
    fun getService(retrofit: Retrofit): IApiService {
        return retrofit.create(IApiService::class.java)
    }

}
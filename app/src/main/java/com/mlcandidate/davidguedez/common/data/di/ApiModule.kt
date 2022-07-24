package com.mlcandidate.davidguedez.common.data.di


import com.mlcandidate.davidguedez.common.data.api.ApiConstants
import com.mlcandidate.davidguedez.common.data.api.APIProductService
import com.mlcandidate.davidguedez.common.data.api.interceptors.NetworkStatusInterceptor
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
    
    @Provides
    @Singleton
    fun provideApi(builder: Retrofit.Builder): APIProductService {
        return builder
            .build()
            .create(APIProductService::class.java)
    }

    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl(ApiConstants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
    }

    @Provides
    fun provideOkHttpClient(
        networkStatusInterceptor: NetworkStatusInterceptor,
        ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(networkStatusInterceptor)
            .build()
    }

}
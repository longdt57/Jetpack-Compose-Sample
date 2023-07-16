package com.example.jetpackcompose.di.network

import com.example.jetpackcompose.data.network.AppService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object ServiceModule {

    @Singleton
    @Provides
    fun provideService(@RetrofitModule.AppRetrofit retrofit: Retrofit): AppService {
        return retrofit.create(AppService::class.java)
    }
}

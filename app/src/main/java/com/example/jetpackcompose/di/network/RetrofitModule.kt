package com.example.jetpackcompose.di.network

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object RetrofitModule {

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class AppRetrofit

    @Singleton
    @Provides
    @AppRetrofit
    fun provideAppRetrofit(
        networkEnvironment: NetworkEnvironment,
        @OkHttpModule.AppIdInterceptorOkHttpClient okHttpClient: OkHttpClient,
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(networkEnvironment.baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}

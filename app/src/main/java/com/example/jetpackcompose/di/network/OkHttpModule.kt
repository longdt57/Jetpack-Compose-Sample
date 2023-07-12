package com.example.jetpackcompose.di.network

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

@Module
@InstallIn(SingletonComponent::class)
internal object OkHttpModule {

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class AppIdInterceptorOkHttpClient

    @Singleton
    @Provides
    @AppIdInterceptorOkHttpClient
    fun provideOkHttpClientWithInterceptor(
        networkEnvironment: NetworkEnvironment
    ): OkHttpClient {
        return OkHttpClient.Builder().apply {
            addLogging(networkEnvironment)
        }.build()
    }

    private fun OkHttpClient.Builder.addLogging(config: NetworkEnvironment): OkHttpClient.Builder {
        if (config.isDebug) {
            addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
        }
        return this
    }
}

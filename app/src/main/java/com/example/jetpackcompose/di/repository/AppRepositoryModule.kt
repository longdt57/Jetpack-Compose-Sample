package com.example.jetpackcompose.di.repository

import com.example.jetpackcompose.data.repository.IProductCardRepository
import com.example.jetpackcompose.data.repository.IProductRepository
import com.example.jetpackcompose.data.repository.ProductCardRepository
import com.example.jetpackcompose.data.repository.ProductRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppRepositoryModule {

    @Singleton
    @Provides
    fun provideProductRepository(repository: ProductRepository): IProductRepository = repository

    @Singleton
    @Provides
    fun provideProductCardRepository(repository: ProductCardRepository): IProductCardRepository = repository

}
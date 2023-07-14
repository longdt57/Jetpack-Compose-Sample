package com.example.jetpackcompose.di.repository

import android.content.Context
import androidx.room.Room
import com.example.jetpackcompose.data.database.AppDatabase
import com.example.jetpackcompose.data.repository.IProductRepository
import com.example.jetpackcompose.data.repository.ProductRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java, "database-compose-sample"
        ).build()
    }
}
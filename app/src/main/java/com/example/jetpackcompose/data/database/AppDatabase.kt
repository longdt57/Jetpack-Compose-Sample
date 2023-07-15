package com.example.jetpackcompose.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.jetpackcompose.data.model.CardItem
import com.example.jetpackcompose.data.model.ProductItem

@Database(entities = [ProductItem::class, CardItem::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun productCardDao(): ProductCardDao
}
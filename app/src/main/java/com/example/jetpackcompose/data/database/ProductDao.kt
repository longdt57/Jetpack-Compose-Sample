package com.example.jetpackcompose.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.jetpackcompose.data.model.ProductItem
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    @Query("SELECT * FROM ProductItem")
    fun getAll(): Flow<List<ProductItem>>

    @Query("SELECT * FROM ProductItem WHERE name IN (:ids)")
    fun getItemByIds(ids: List<String>): List<ProductItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(products: List<ProductItem>)

    @Query("DELETE FROM ProductItem")
    fun deleteAll()
}
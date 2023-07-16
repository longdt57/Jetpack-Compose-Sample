package com.example.jetpackcompose.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.jetpackcompose.data.model.CartItem

@Dao
interface CartDao {
    @Query("SELECT * FROM CartItem")
    fun getAll(): List<CartItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: CartItem)

    @Delete
    fun delete(item: CartItem)

    @Query("DELETE FROM CartItem")
    fun deleteAll()
}

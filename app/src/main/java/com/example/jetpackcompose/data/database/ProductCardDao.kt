package com.example.jetpackcompose.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.jetpackcompose.data.model.CardAddedItem

@Dao
interface ProductCardDao {
    @Query("SELECT * FROM CardAddedItem")
    fun getAll(): List<CardAddedItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(cardItem: CardAddedItem)

    @Delete
    fun delete(item: CardAddedItem)

    @Query("DELETE FROM CardAddedItem")
    fun deleteAll()
}
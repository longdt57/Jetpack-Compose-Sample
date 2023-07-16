package com.example.jetpackcompose.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.jetpackcompose.data.model.CardItem

@Dao
interface ProductCardDao {
    @Query("SELECT * FROM CardItem")
    fun getAll(): List<CardItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(cardItem: CardItem)

    @Delete
    fun delete(item: CardItem)

    @Query("DELETE FROM CardItem")
    fun deleteAll()
}

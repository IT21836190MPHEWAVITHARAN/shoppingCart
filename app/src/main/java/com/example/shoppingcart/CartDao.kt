package com.example.shoppingcart

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CartDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: CartItems)

    @Delete
    suspend fun delete(item: CartItems)

    @Query("SELECT * FROM cart_items")
    fun getAllCartItems(): LiveData<List<CartItems>>
}

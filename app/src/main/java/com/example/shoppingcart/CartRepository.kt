package com.example.shoppingcart

class CartRepository(private val db: CartDatabase) {

    suspend fun insert(item: CartItems) = db.getCartDao().insert(item)
    suspend fun delete(item: CartItems) = db.getCartDao().delete(item)

    fun allCartItems() = db.getCartDao().getAllCartItems()

}
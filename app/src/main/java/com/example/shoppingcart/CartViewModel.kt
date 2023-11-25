package com.example.shoppingcart

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CartViewModel(private val repository: CartRepository) : ViewModel() {

    fun insert(item: CartItems) = GlobalScope.launch {
        repository.insert(item)
    }

    fun delete(item: CartItems) = GlobalScope.launch {
        repository.delete(item)
    }

    fun allCartItems() = repository.allCartItems()
}
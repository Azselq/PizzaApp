package com.example.pizzaapp.room.repository

import com.example.pizzaapp.room.CartModel

interface CartRepository {
    val allCart: List<CartModel>
    suspend fun addToCart(cart: CartModel)
    suspend fun deleteFromCart(cart: CartModel)
    suspend fun deleteAllDromCart()
}
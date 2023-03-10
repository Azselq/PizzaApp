package com.example.pizzaapp.room.repository

import com.example.pizzaapp.room.CartModel
import java.util.*

interface CartRepository {
    val allCart: List<CartModel>
    suspend fun addToCart(cart: CartModel)
    suspend fun deleteFromCart(cart: CartModel)
    suspend fun deleteAllDromCart()

    suspend fun deleteFood(foodId: Int): Boolean
}
package com.example.pizzaapp.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import ir.rev.foodMaker.models.BaseFood
import java.util.*

@Dao
interface CartDAO {

    @Query("select * from cart_table")
    fun getAllCart(): List<CartModel>

    @Insert
    suspend fun insertInCart(cartModel: CartModel)

    @Delete
    suspend fun removeFromCart(cartModel: CartModel)

    @Query("delete from cart_table")
    suspend fun removeALlFromCart()

    @Query("SELECT * FROM cart_table WHERE id = :foodId")
    fun getFood(foodId: Int): CartModel
}
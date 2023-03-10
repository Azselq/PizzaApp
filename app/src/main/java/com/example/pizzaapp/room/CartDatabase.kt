package com.example.pizzaapp.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CartModel::class], version = 4)
abstract class CartDatabase : RoomDatabase() {
    abstract val getCartDAO: CartDAO
}
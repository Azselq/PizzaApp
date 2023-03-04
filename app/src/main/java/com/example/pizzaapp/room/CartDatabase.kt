package com.example.pizzaapp.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CartModel::class],version = 2)
abstract class CartDatabase:RoomDatabase() {
    abstract val getCartDAO:CartDAO

    companion object{
        private var database:CartDatabase?=null
    }
}
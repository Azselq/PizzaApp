package com.example.pizzaapp.room

import android.content.Context
import androidx.annotation.WorkerThread
import androidx.room.Room
import com.example.pizzaapp.room.repository.CartRealization
import com.example.pizzaapp.room.repository.CartRepository

object CartPlagin {
    private var cartRepositoryInstance: CartRepository? =null
    internal lateinit var database: CartDatabase


    fun getCartRepository():CartRepository{
        return synchronized(this){
            cartRepositoryInstance?: CartRealization().also {
                cartRepositoryInstance = it
            }
        }
    }

    @WorkerThread
    fun initCartDatabase(context: Context){
        database= Room.databaseBuilder(
                context,CartDatabase::class.java,"db").fallbackToDestructiveMigration().build()
        database.getCartDAO.getAllCart()
    }
}
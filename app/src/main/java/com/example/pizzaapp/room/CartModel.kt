package com.example.pizzaapp.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_table")
open class CartModel(

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    @ColumnInfo
    var title: String,

    @ColumnInfo
    var cost: Double,

    @ColumnInfo
    var imageUrl :String,
)
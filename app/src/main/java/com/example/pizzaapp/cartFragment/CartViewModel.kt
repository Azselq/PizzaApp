package com.example.pizzaapp.cartFragment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pizzaapp.mainFragment.CartDishes
import com.example.pizzaapp.mainFragment.DishesForCart
import com.example.pizzaapp.room.CartModel
import com.example.pizzaapp.room.CartPlugin
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.util.UUID

class CartViewModel : ViewModel() {
    private val cartRepository = CartPlugin.getCartRepository()
    private val _cartDishesLiveData = MutableLiveData<List<DishesForCart>>()
    val cartDishesLiveData: LiveData<List<DishesForCart>> = _cartDishesLiveData

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _cartDishesLiveData.postValue(cartRepository.allCart.createViewModels())
            Log.d("123", "Cart $_cartDishesLiveData")
        }

    }

    private fun List<CartModel>.createViewModels(): List<DishesForCart> {
        return map {
            Log.d("123", "kekw ${it.title}")
            CartDishes(
                id = it.id,
                title = it.title,
                cost = it.cost,
                imageUrl = it.imageUrl
            )
        }.onEach {
            it.onClick.set {
                deleteCart(it.id)
            }
        }
    }

    override fun onCleared() {
        _cartDishesLiveData.value?.forEach { it.release() }
        _cartDishesLiveData.value = emptyList()
        super.onCleared()
    }

    fun deleteAllFromCart1() {
        viewModelScope.launch(Dispatchers.IO) {
            cartRepository.deleteAllDromCart()
        }
    }

    fun deleteCart(id: Int) {
        val isDelete = viewModelScope.async(Dispatchers.IO) {
            cartRepository.deleteFood(id)
        }

    }
}
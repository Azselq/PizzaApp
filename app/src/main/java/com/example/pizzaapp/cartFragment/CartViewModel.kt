package com.example.pizzaapp.cartFragment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pizzaapp.mainFragment.AdditionalDishes
import com.example.pizzaapp.mainFragment.BaseDishes
import com.example.pizzaapp.mainFragment.CartDishes
import com.example.pizzaapp.mainFragment.DishesForCart
import com.example.pizzaapp.room.CartModel
import com.example.pizzaapp.room.CartPlagin
import io.reactivex.rxjava3.disposables.Disposable
import ir.rev.foodMaker.models.BaseFood
import ir.rev.foodMaker.models.FoodDetails
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CartViewModel : ViewModel() {
    private val cartRepository = CartPlagin.getCartRepository()
    private val _cartDishesLiveData = MutableLiveData<List<DishesForCart>>()
    val cartDishesLiveData: LiveData<List<DishesForCart>> = _cartDishesLiveData
    init{
        viewModelScope.launch(Dispatchers.IO) {
            _cartDishesLiveData.postValue(cartRepository.allCart.createViewModels())
            Log.d("123","Cart ${_cartDishesLiveData}") }

    }
    private fun List<CartModel>.createViewModels(): List<DishesForCart> {
        return map {
            Log.d("123", "kekw ${it.title}")
            CartDishes(
                id = it.id,
                title = it.title,
                cost = it.cost
            )
        }


    }
    fun deleteAllFromCart1(){
        viewModelScope.launch(Dispatchers.IO) {
            cartRepository.deleteAllDromCart()
        }
    }
}
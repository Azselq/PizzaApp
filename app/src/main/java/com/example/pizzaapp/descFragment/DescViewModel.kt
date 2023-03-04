package com.example.pizzaapp.descFragment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pizzaapp.databinding.FragmentDescBinding
import com.example.pizzaapp.mainFragment.*
import com.example.pizzaapp.room.CartDatabase
import com.example.pizzaapp.room.CartModel
import com.example.pizzaapp.room.CartPlagin
import com.example.pizzaapp.room.repository.CartRealization
import com.example.pizzaapp.room.repository.CartRepository
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.Disposable
import ir.rev.foodMaker.FoodPlugin
import ir.rev.foodMaker.models.BaseFood
import ir.rev.foodMaker.models.FoodDetails
import ir.rev.twoWayActionsBus.TwoWayActionViewModelWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.subscribe
import kotlinx.coroutines.launch
import java.util.*


class DescViewModel(val id: UUID) : ViewModel() {
    private val foodRepository = FoodPlugin.getFoodListRepository()
    private val cartRepository = CartPlagin.getCartRepository()
    private var foodSubscribeDisposeble = Disposable.disposed()

    private val _additionalDishesListLiveData = MutableLiveData<FoodDetails>()
    val additionalDishesListLiveData: LiveData<FoodDetails> = _additionalDishesListLiveData

    private val _additionalFoodListLiveData = MutableLiveData<List<BaseDishes>>()
    val additionalFoodListLiveData: LiveData<List<BaseDishes>> = _additionalFoodListLiveData


    init {
        foodSubscribeDisposeble = foodRepository.getFoodDetails(id).subscribe{ it, error ->
            if (error == null){
                _additionalDishesListLiveData.postValue(it)
                _additionalFoodListLiveData.postValue(it.additionalFood.createViewModels())
                Log.d("checkResult", "$it: ")

            } else {
                Log.d("checkResult", "$error: ")
            }
        }
    }

    private val actionWrapper = TwoWayActionViewModelWrapper(::handleAction)

    val action
        get() = actionWrapper.action

    private fun List<BaseFood.AdditionalFood>.createViewModels(): List<BaseDishes> {
        return map {
            Log.d("123", "kekw ${it.title}")
            AdditionalDishes(
                id = it.id,
                title = it.title,
                subTitle = it.subTitle,
                group = it.group,
                imageUrl = it.imageUrl,
                price = it.price,
                isAvailability = it.isAvailability
            )
        }.onEach {
            it.onClick.set {
                Log.d("123", "$it")
                addAdditionalFood(it)
            }
        }
    }

    private fun handleAction(receivedAction: DishesListModelAction) {
        when (receivedAction) {
            is IDDishes -> {
//                idDishes1 = receivedAction.id
//                Log.d("123","idDIshes ${idDishes1}")
            }
            else -> {

            }
        }
    }
    private fun addAdditionalFood(baseDishes: BaseDishes){
        action.post(AddAdditionalFood(baseDishes))
    }
    fun insertInCart(cartModel: CartModel)=viewModelScope.launch (Dispatchers.IO){
        cartRepository.addToCart(cartModel)
    }
}
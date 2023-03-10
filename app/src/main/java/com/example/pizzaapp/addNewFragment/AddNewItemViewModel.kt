package com.example.pizzaapp.addNewFragment

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import ir.rev.foodMaker.FoodPlugin
import ir.rev.foodMaker.models.BaseFood
import ir.rev.twoWayActionsBus.TwoWayActionViewModelWrapper
import java.util.UUID
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddNewItemViewModel : ViewModel() {

    private val foodRepository = FoodPlugin.getFoodListRepository()
    private val actionWrapper = TwoWayActionViewModelWrapper(::handleAction)

    val action
        get() = actionWrapper.action

    private fun handleAction(receivedAction: AddNewItemModelAction) {
        viewModelScope.launch(Dispatchers.IO) {
            when (receivedAction) {
                is NewItem -> {
                    Log.d("123", receivedAction.group)
                    foodRepository.addFood(
                        BaseFood.Food(
                            id = UUID.randomUUID(),
                            title = receivedAction.title,
                            subTitle = " ",
                            group = receivedAction.group,
                            imageUrl = "https://handcraftguide.com/sites/default/files/styles/original___water/public/4kawaiifoodimageshandcraftguide.com__0.jpg?itok=0S1snRq6",
                            price = receivedAction.cost,
                            isAvailability = true
                        )
                    )
                }
                else -> Unit
            }
        }
    }
}


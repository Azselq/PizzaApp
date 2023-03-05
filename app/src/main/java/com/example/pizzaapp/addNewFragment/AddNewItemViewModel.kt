package com.example.pizzaapp.addNewFragment

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pizzaapp.mainFragment.DishesListModelAction
import com.example.pizzaapp.mainFragment.NewItem
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

    private fun handleAction(receivedAction: DishesListModelAction) {
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
                            imageUrl = " ",
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


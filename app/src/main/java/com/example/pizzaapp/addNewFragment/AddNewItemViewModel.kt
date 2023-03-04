package com.example.pizzaapp.addNewFragment

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pizzaapp.mainFragment.*
import ir.rev.foodMaker.FoodPlugin
import ir.rev.foodMaker.models.BaseFood
import ir.rev.twoWayActionsBus.TwoWayActionViewModelWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class AddNewItemViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    private val foodRepository = FoodPlugin.getFoodListRepository()
    private val actionWrapper = TwoWayActionViewModelWrapper(::handleAction)

    val action
        get() = actionWrapper.action
    private fun handleAction(receivedAction: DishesListModelAction) {
        viewModelScope.launch(Dispatchers.IO) {
            when (receivedAction) {
                is NewItem -> {
                }
                else -> {

                }
            }
        }
    }
    }


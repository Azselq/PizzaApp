package com.example.pizzaapp.mainFragment

import com.example.pizzaapp.R
import ir.rev.foodMaker.models.BaseFood
import ir.rev.vmadapter.ViewModelAdapter

class DishesListAdapter : ViewModelAdapter(Mode.VIEW_MODEL_MERGE) {

    init {
        cell(R.layout.item_beer, itemChecker = Beer)
        cell(R.layout.item_pizza, itemChecker = Pizza)
        cell(R.layout.item_sushi, itemChecker = Sushi)
        cell(R.layout.item_additional_food, itemChecker = AdditionalDishes)
        cell(R.layout.item_cart, itemChecker = CartDishes)
    }
}

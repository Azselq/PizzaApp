package com.example.pizzaapp.descFragment

import com.example.pizzaapp.mainFragment.BaseDishes
import com.example.pizzaapp.mainFragment.DishesListModelAction
import ir.rev.twoWayActionsBus.TwoWayAction

sealed class DescModelAction : TwoWayAction()
class AddAdditionalFood(val baseDishes: BaseDishes) : DescModelAction()
object OpenLastFragment :  DescModelAction()
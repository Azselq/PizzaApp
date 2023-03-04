package com.example.pizzaapp.mainFragment

import ir.rev.twoWayActionsBus.TwoWayAction
import java.util.*

sealed class DishesListModelAction : TwoWayAction()


class OpenDescFragment(val baseDishes: BaseDishes): DishesListModelAction()
class IDDishes(val id: UUID): DishesListModelAction()
class AddAdditionalFood(val baseDishes: BaseDishes ): DishesListModelAction()

class NewItem(val title:String, val cost: Double, val group: String): DishesListModelAction()
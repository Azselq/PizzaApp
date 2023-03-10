package com.example.pizzaapp.mainFragment

import ir.rev.twoWayActionsBus.TwoWayAction
import java.util.UUID

// Первов 05.03.2023 Для каждого экрана нужны свои actions, что б не слушать лишние ивенты там где это не нужно
sealed class DishesListModelAction : TwoWayAction()

class OpenDescFragment(val baseDishes: BaseDishes) : DishesListModelAction()
//object OpenLastFragment : DishesListModelAction()


// TODO: Утащи в другие классы
class IDDishes(val id: UUID) : DishesListModelAction()
//class AddAdditionalFood(val baseDishes: BaseDishes) : DishesListModelAction()

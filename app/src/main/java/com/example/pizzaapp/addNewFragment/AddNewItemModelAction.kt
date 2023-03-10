package com.example.pizzaapp.addNewFragment

import ir.rev.twoWayActionsBus.TwoWayAction

sealed class AddNewItemModelAction: TwoWayAction()

class NewItem(val title: String, val cost: Double, val group: String) : AddNewItemModelAction()

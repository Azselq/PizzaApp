package com.example.pizzaapp.descFragment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pizzaapp.databinding.FragmentDescBinding
import com.example.pizzaapp.mainFragment.*
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.Disposable
import ir.rev.foodMaker.FoodPlugin
import ir.rev.foodMaker.models.BaseFood
import ir.rev.foodMaker.models.FoodDetails
import ir.rev.twoWayActionsBus.TwoWayActionViewModelWrapper
import kotlinx.coroutines.flow.subscribe
import java.util.*


class DescViewModel : ViewModel() {
    private val foodRepository = FoodPlugin.getFoodListRepository()
    var idDishes1: UUID = UUID.randomUUID()
    private var foodSubscribeDisposeble = Disposable.disposed()

    private val _additionalDishesListLiveData = MutableLiveData<Single<FoodDetails>>()
    val additionalDishesListLiveData: LiveData<Single<FoodDetails>> = _additionalDishesListLiveData
    init{
        foodSubscribeDisposeble = foodRepository.getFoodDetails(idDishes1).subscribe({
            Log.d("123","$it")
        },{Log.d("123","ViewModel -> error")})
        //Log.d("123","foodDetails ${foodRepository.getFoodDetails(idDishes1)}")
         //_additionalDishesListLiveData.postValue(foodRepository.getFoodDetails(idDishes1))
        //_additionalDishesListLiveData.value?.subscribe({it.additionalFood.createViewModels()},{Log.d("123","VM error")})

    }

    private val actionWrapper = TwoWayActionViewModelWrapper(::handleAction)

    val action
        get() = actionWrapper.action

    private fun List<BaseFood.AdditionalFood>.createViewModels(): List<BaseDishes> {
        return map {
            Log.d("123","kekw ${it.title}")
            AdditionalDishes(id = it.id, title = it.title, subTitle = it.subTitle,group = it.group, imageUrl = it.imageUrl, price = it.price, isAvailability = it.isAvailability)
            }
        }

    private fun handleAction(receivedAction: DishesListModelAction){
        when(receivedAction){
            is IDDishes-> {
                idDishes1 = receivedAction.id
                Log.d("123","idDIshes ${idDishes1}")
            }
            else->{

            }
        }
    }
}
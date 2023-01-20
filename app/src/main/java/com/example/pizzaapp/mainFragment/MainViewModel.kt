package com.example.pizzaapp.mainFragment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.disposables.Disposable
import ir.rev.foodMaker.FoodPlugin
import ir.rev.foodMaker.models.BaseFood
import ir.rev.foodMaker.models.FoodFilter
import ir.rev.foodMaker.repository.FoodListRepository
import ir.rev.twoWayActionsBus.TwoWayActionViewModelWrapper
import java.util.*

class MainViewModel : ViewModel() {
    private val _dishesListLiveData = MutableLiveData<List<BaseDishes>>()
    val dishesListLiveData: LiveData<List<BaseDishes>> = _dishesListLiveData
    private val foodRepository = FoodPlugin.getFoodListRepository()
    private var foodSubscribeDisposeble = Disposable.disposed()

    private val actionWrapper = TwoWayActionViewModelWrapper(::handleAction)



    init{
        foodSubscribeDisposeble = foodRepository.getFoodListObservable().subscribe{
           if(it.second == null){
                _dishesListLiveData.postValue(it.first.createViewModels())
                Log.d("123","liveData ${it.first}")
            }else{
                Log.d("123","${it.second}")
            }
            //Обработать ошибку
            //Добавить мапер, еды, поместить в лайф дату
        }
    foodRepository.subscribeFoodList(foodFilter = FoodFilter(0,10))
    }
    val action
        get() = actionWrapper.action

    fun getUUID(): UUID {
        return UUID.randomUUID()
    }
    private fun List<BaseFood.Food>.createViewModels(): List<BaseDishes> {
        return map {
            Log.d("123","${it.group}")
            when (it.group) {
                //суши добалвяется в пиццу для проверки
                "суши" -> Pizza(id = it.id, title = it.title, subTitle = it.subTitle,group = it.group, imageUrl = it.imageUrl, price = it.price, isAvailability = it.isAvailability)
                "пиво" -> Beer(id = it.id, title = it.title, subTitle = it.subTitle,group = it.group, imageUrl = it.imageUrl, price = it.price, isAvailability = it.isAvailability)
                else  -> Sushi(id = it.id, title = it.title, subTitle = it.subTitle,group = it.group, imageUrl = it.imageUrl, price = it.price, isAvailability = it.isAvailability)
            }
        }
    }

    private fun handleAction(receivedAction: DishesListModelAction){
        when(receivedAction){

            else->{

            }
        }
    }
/*
    fun refreshByScroll() {
        subscribeToFoodList()
    }



 */
    override fun onCleared() {
        foodSubscribeDisposeble.dispose()
        super.onCleared()
    }
}
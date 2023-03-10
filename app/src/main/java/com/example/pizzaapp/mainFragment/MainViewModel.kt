package com.example.pizzaapp.mainFragment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pizzaapp.utils.checkDelayIsOff
import io.reactivex.rxjava3.disposables.Disposable
import ir.rev.foodMaker.FoodPlugin
import ir.rev.foodMaker.models.BaseFood
import ir.rev.foodMaker.models.FoodFilter
import ir.rev.twoWayActionsBus.TwoWayActionViewModelWrapper

class MainViewModel : ViewModel() {

    private val _dishesListLiveData = MutableLiveData<List<BaseDishes>>()
    val dishesListLiveData: LiveData<List<BaseDishes>> = _dishesListLiveData

    private val foodRepository = FoodPlugin.getFoodListRepository()
    private var foodSubscribeDisposeble = Disposable.disposed()

    private val actionWrapper = TwoWayActionViewModelWrapper(::handleAction)

    val action
        get() = actionWrapper.action

    // что бы избежать множественого вызова функций при пагинации или скроле используетс следующая проперти
    private var lastCallSubscribeTime = -1L

    init {
        foodSubscribeDisposeble = foodRepository.getFoodListObservable().subscribe {
            if (it.second == null) {
                // Починил пагинацию теперь старые значения добавляются к новым
                _dishesListLiveData.postValue(it.first.createViewModels())

                Log.d("123", "liveData ${it.first}")
            } else {
                Log.d("123", "${it.second}")
            }
            //Обработать ошибку
            //Добавить мапер, еды, поместить в лайф дату
        }
        subscribeToFoodList(0)
    }

    private fun List<BaseFood.Food>.createViewModels(): List<BaseDishes> {
        return map {
            Log.d("123", "${it.group}")
            when (it.group) {
                "пицца" -> Pizza(
                    id = it.id,
                    title = it.title,
                    subTitle = it.subTitle,
                    group = it.group,
                    imageUrl = it.imageUrl,
                    price = it.price,
                    isAvailability = it.isAvailability
                )
                "пиво" -> Beer(
                    id = it.id,
                    title = it.title,
                    subTitle = it.subTitle,
                    group = it.group,
                    imageUrl = it.imageUrl,
                    price = it.price,
                    isAvailability = it.isAvailability
                )
                else -> Sushi(
                    id = it.id,
                    title = it.title,
                    subTitle = it.subTitle,
                    group = it.group,
                    imageUrl = it.imageUrl,
                    price = it.price,
                    isAvailability = it.isAvailability
                )
            }
        }.onEach {
            it.onClick.set {
                Log.d("123", "$it")
                openNextFragment(it)
            }
        }
    }

    private fun handleAction(receivedAction: DishesListModelAction) {
        when (receivedAction) {
            else -> {

            }
        }
    }

    private fun openNextFragment(baseDishes: BaseDishes) {
        Log.d("123", "fun openNextFragment $baseDishes")
        action.post(OpenDescFragment(baseDishes))
    }

    fun refreshByScroll() {
        subscribeToFoodList(dishesListLiveData.value?.size ?: 0)
    }

    private fun subscribeToFoodList(position: Int) {
        // и тут пагинацию подправил, теперь запрос идет от последнего айтема и предоставляет коректный размер
        trySubscribe { foodRepository.subscribeFoodList(foodFilter = FoodFilter(445, position + SUBSCRIBE_FOOD_COUNT)) }
    }

    override fun onCleared() {
        foodSubscribeDisposeble.dispose()
        _dishesListLiveData.value?.forEach { it.release() }
        _dishesListLiveData.value = emptyList()
        super.onCleared()
    }

    /**
     * Функция что бы избежать избыточных запросов
     */
    private inline fun trySubscribe(block: () -> Unit) {
        if (lastCallSubscribeTime.checkDelayIsOff()) {
            lastCallSubscribeTime = System.currentTimeMillis()
            block()
        }
    }

    companion object {
        private const val SUBSCRIBE_FOOD_COUNT = 10
    }
}
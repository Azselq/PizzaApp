package com.example.pizzaapp.mainFragment

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableField
import com.bumptech.glide.Glide
import com.example.pizzaapp.R
import ir.rev.vmadapter.ItemChecker
import java.util.*


sealed class BaseDishes(
    val id: UUID,
    val title: String,
    val subTitle: String,
    val group: String,
    val imageUrl: String,
    val price: Double,
    val isAvailability: Boolean
    ) {
    abstract val titleText: ObservableField<String>
    abstract val onClick: ObservableField<(() -> Unit)?>

    fun release() {
        onClick.set(null)
    }
}

class Pizza(id: UUID, title: String, subTitle: String, group: String, imageUrl: String, price: Double, isAvailability: Boolean):
    BaseDishes(id, title, subTitle, group, imageUrl, price, isAvailability) {
    override val titleText: ObservableField<String> = ObservableField("Пицца-$title")
    override val onClick: ObservableField<(() -> Unit)?> = ObservableField()
    val costPizza = "$price"
    val descPizza = "$subTitle"

    companion object : ItemChecker.ForViewModelMerge<Pizza>() {

        override fun areItemsTheSame(left: Pizza, right: Pizza) =
            left.id == right.id

        override fun merge(left: Pizza, right: Pizza) {
            if (left === right) return
            left.titleText.set(right.titleText.get())
        }
    }
}

class Beer(id: UUID, title: String, subTitle: String, group: String, imageUrl: String, price: Double, isAvailability: Boolean):
    BaseDishes(id, title, subTitle, group, imageUrl, price, isAvailability) {
    override val titleText: ObservableField<String> = ObservableField("Пиво-$title")
    override val onClick: ObservableField<(() -> Unit)?> = ObservableField()
    val beerDesc = "$subTitle"
    val beerCost = "$price"
    companion object : ItemChecker.ForViewModelMerge<Beer>() {

        override fun areItemsTheSame(left: Beer, right: Beer) =
            left.id == right.id

        override fun merge(left: Beer, right: Beer) {
            if (left === right) return
            left.titleText.set(right.titleText.get())
        }
    }
}

class Sushi(id: UUID, title: String, subTitle: String, group: String, imageUrl: String, price: Double, isAvailability: Boolean):
    BaseDishes(id, title, subTitle, group, imageUrl, price, isAvailability) {
    override val titleText: ObservableField<String> = ObservableField("Суши-$title ")
    override val onClick: ObservableField<(() -> Unit)?> = ObservableField()
    val sushiCost = "$price"
    val sushiCount = "0"
    companion object : ItemChecker.ForViewModelMerge<Sushi>() {
        override fun areItemsTheSame(left: Sushi, right: Sushi) =
            left.id == right.id
        override fun merge(left: Sushi, right: Sushi) {
            if (left === right) return
            left.titleText.set(right.titleText.get())
        }
    }
}
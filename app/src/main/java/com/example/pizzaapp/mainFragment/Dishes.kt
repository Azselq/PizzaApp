package com.example.pizzaapp.mainFragment

import android.os.Parcelable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableField
import com.bumptech.glide.Glide
import com.example.pizzaapp.R
import ir.rev.vmadapter.ItemChecker
import java.util.*
import kotlinx.parcelize.Parcelize


@Parcelize
sealed class BaseDishes(
    open val id: UUID,
    open val title: String,
    open val subTitle: String,
    open val group: String,
    open val imageUrl: String,
    open val price: Double,
    open val isAvailability: Boolean
) : Parcelable {
    abstract val titleText: ObservableField<String>
    abstract val onClick: ObservableField<(() -> Unit)?>

    fun release() {
        onClick.set(null)
    }
}

@Parcelize
class Pizza(
    override val id: UUID,
    override val title: String,
    override val subTitle: String,
    override val group: String,
    override val imageUrl: String,
    override val price: Double,
    override val isAvailability: Boolean
) :
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

@Parcelize
class Beer(
    override val id: UUID,
    override val title: String,
    override val subTitle: String,
    override val group: String,
    override val imageUrl: String,
    override val price: Double,
    override val isAvailability: Boolean
) :
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

@Parcelize
class Sushi(
    override val id: UUID,
    override val title: String,
    override val subTitle: String,
    override val group: String,
    override val imageUrl: String,
    override val price: Double,
    override val isAvailability: Boolean
) :
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

class AdditionalDishes(
    override val id: UUID,
    override val title: String,
    override val subTitle: String,
    override val group: String,
    override val imageUrl: String,
    override val price: Double,
    override val isAvailability: Boolean
) :
    BaseDishes(id, title, subTitle, group, imageUrl, price, isAvailability) {
    override val titleText: ObservableField<String> = ObservableField("$title")
    override val onClick: ObservableField<(() -> Unit)?> = ObservableField()
    val costAdditionalFood = "$price"
    val descAdditionalFood = "$subTitle"

    companion object : ItemChecker.ForViewModelMerge<Pizza>() {

        override fun areItemsTheSame(left: Pizza, right: Pizza) =
            left.id == right.id

        override fun merge(left: Pizza, right: Pizza) {
            if (left === right) return
            left.titleText.set(right.titleText.get())
        }
    }
}
package com.example.pizzaapp.utils

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.pizzaapp.R

@BindingAdapter("onClick")
fun View.setActionOnClick(action: (() -> Unit)?) {
        setOnClickListener { action?.invoke() }
}


@BindingAdapter("onClick")
fun ImageView.setImageFromUrl(url:String) {
        Glide.with(this).load(url).into(this)
}
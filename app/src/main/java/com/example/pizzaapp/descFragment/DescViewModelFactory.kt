package com.example.pizzaapp.descFragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.util.UUID

/**
 * Предастовляет фабрику для создания viewModel
 *
 * @author au.pervov
 */
@Suppress("UNCHECKED_CAST")
class DescViewModelFactory(
    private val id: UUID
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        DescViewModel(
            id = id
        ) as T

}
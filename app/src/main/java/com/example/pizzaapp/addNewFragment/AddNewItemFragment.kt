package com.example.pizzaapp.addNewFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.pizzaapp.databinding.FragmentAddNewItemBinding
import com.example.pizzaapp.mainFragment.NewItem

class AddNewItemFragment : Fragment() {

    private lateinit var binding: FragmentAddNewItemBinding

    private lateinit var viewModel: AddNewItemViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddNewItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AddNewItemViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            imButtonAdd.setOnClickListener {
                val title = etTitle.text.toString()
                val cost = etCost.text.toString().toDouble()
                val group = etDesc.text.toString()
                viewModel.action.post(NewItem(title, cost, group))
            }
        }

    }

}
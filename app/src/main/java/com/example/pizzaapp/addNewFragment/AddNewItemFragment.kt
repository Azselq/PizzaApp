package com.example.pizzaapp.addNewFragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pizzaapp.databinding.FragmentAddNewItemBinding
import com.example.pizzaapp.mainFragment.NewItem

class AddNewItemFragment : Fragment() {
    lateinit var binding: FragmentAddNewItemBinding

    companion object {
        fun newInstance() = AddNewItemFragment()
    }

    private lateinit var viewModel: AddNewItemViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddNewItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AddNewItemViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.imButtonAdd.setOnClickListener {
            var title = binding.etTitle.text.toString()
            var cost = binding.etCost.text.toString().toDouble()
            var group = binding.etDesc.text.toString()
            viewModel.action.post(NewItem(title, cost, group))
        }
    }

}
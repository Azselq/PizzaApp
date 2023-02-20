package com.example.pizzaapp.descFragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.pizzaapp.R
import com.example.pizzaapp.databinding.FragmentDescBinding
import com.example.pizzaapp.mainFragment.BaseDishes
import com.example.pizzaapp.mainFragment.IDDishes
import com.example.pizzaapp.mainFragment.MainFragmentDirections
import com.example.pizzaapp.mainFragment.OpenDescFragment
//import com.example.pizzaapp.mainFragment.OpenNextFragment
import java.util.*

class DescFragment : Fragment() {
    lateinit var binding: FragmentDescBinding
    private val args: DescFragmentArgs by navArgs()
    private val currentItem: BaseDishes
        get() = args.dishes

    companion object {
        fun newInstance() = DescFragment()
    }

    private lateinit var viewModel: DescViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDescBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(DescViewModel::class.java)
       //binding.fragDescTitle.text = currentItem.title.toString()
        //binding.fragDescDesc.text = currentItem.subTitle.toString()
        viewModel.additionalDishesListLiveData.observe(viewLifecycleOwner) {
            it.subscribe({
                binding.fragDescTitle.text = it.title
                Log.d("321", "title ${it.title}")
                binding.fragDescDesc.text = it.aboutFood.description
            }, {
                Log.d("123", "error")
            })
        }

            Glide.with(binding.fragDescImage).load(currentItem.imageUrl).into(binding.fragDescImage)
            viewModel.action.post(IDDishes(currentItem.id))

            viewModel.action.handler = { event ->
                when (event) {
                    else -> {
                        /* должно быть пусто*/
                    }
                }
            }
        }

    }
package com.example.pizzaapp.mainFragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.example.pizzaapp.R
import com.example.pizzaapp.databinding.FragmentMainBinding
import com.example.pizzaapp.utils.ScrollListener
import ir.rev.foodMaker.FoodPlugin
import ir.rev.foodMaker.models.BaseFood
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainFragment : Fragment() {
    private var adapter: DishesListAdapter? = null
    lateinit var binding: FragmentMainBinding
    private lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = DishesListAdapter()
        binding.listDishes.apply {
            adapter = this@MainFragment.adapter
            addOnScrollListener(
                ScrollListener(
                    context = context,
                    loadNextPage = {
                        //viewModel.refreshByScroll()
                    },
                ).apply {
                    setOnTouchListener(this)
                }
            )
        }
        viewModel.dishesListLiveData.observe(viewLifecycleOwner){
            adapter?.reload(it)
            Log.d("123","$it")
        }
        viewModel.action.handler = { event ->
            when (event) {
                is OpenDescFragment -> findNavController().navigate(MainFragmentDirections.actionMainFragmentToDescFragment(event.baseDishes))
                else -> {
                    /* должно быть пусто*/
                }
            }
        }

    }
}

package com.example.pizzaapp.mainFragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.pizzaapp.databinding.FragmentMainBinding
import com.example.pizzaapp.utils.ScrollListener

class MainFragment : Fragment() {

    private var adapter: DishesListAdapter? = null

    private lateinit var binding: FragmentMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
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
                        viewModel.refreshByScroll()
                    },
                ).apply {
                    setOnTouchListener(this)
                }
            )
        }

        binding.imCart.setOnClickListener {
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToCartFragment())
        }

        viewModel.dishesListLiveData.observe(viewLifecycleOwner) {
            adapter?.reload(it)
            Log.d("123", "Main Fragment $it")
        }

        binding.floatBTAddNewItem.setOnClickListener {
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToAddNewItemFragment2())
        }

        viewModel.action.handler = { event ->
            when (event) {
                is OpenDescFragment -> findNavController().navigate(
                    MainFragmentDirections.actionMainFragmentToDescFragment(
                        event.baseDishes
                    )
                )
                else -> {}
            }
        }

    }
}

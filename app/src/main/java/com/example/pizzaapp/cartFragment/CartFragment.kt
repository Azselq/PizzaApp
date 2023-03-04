package com.example.pizzaapp.cartFragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.viewModelScope
import com.example.pizzaapp.R
import com.example.pizzaapp.databinding.FragmentCartBinding
import com.example.pizzaapp.databinding.FragmentDescBinding
import com.example.pizzaapp.mainFragment.DishesListAdapter
import com.example.pizzaapp.utils.ScrollListener

class CartFragment : Fragment() {
    private var adapter: DishesListAdapter? = null
    lateinit var binding:FragmentCartBinding
    companion object {
        fun newInstance() = CartFragment()
    }

    private lateinit var viewModel: CartViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CartViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = DishesListAdapter()
        binding.rcCart.apply {
            adapter = this@CartFragment.adapter
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

        viewModel.cartDishesLiveData.observe(viewLifecycleOwner){
            adapter?.reload(it)
        }

        binding.deleteAllButtom.setOnClickListener {
            viewModel.deleteAllFromCart1()
            adapter = DishesListAdapter()
            binding.rcCart.apply {
                adapter = this@CartFragment.adapter
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
        }
    }
}
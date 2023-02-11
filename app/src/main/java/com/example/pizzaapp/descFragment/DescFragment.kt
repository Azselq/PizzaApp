package com.example.pizzaapp.descFragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.pizzaapp.R
import com.example.pizzaapp.databinding.FragmentDescBinding
import com.example.pizzaapp.mainFragment.BaseDishes
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
        return inflater.inflate(R.layout.fragment_desc, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DescViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.testTV.text = args.dishes.toString()
    }

}
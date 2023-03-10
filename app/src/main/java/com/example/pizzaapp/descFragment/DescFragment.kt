package com.example.pizzaapp.descFragment

//import com.example.pizzaapp.mainFragment.OpenNextFragment
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.pizzaapp.databinding.FragmentDescBinding
import com.example.pizzaapp.mainFragment.BaseDishes
import com.example.pizzaapp.mainFragment.DishesListAdapter
import com.example.pizzaapp.mainFragment.IDDishes
import com.example.pizzaapp.room.CartModel
import com.example.pizzaapp.utils.ScrollListener

class DescFragment : Fragment() {

    private var adapter: DishesListAdapter? = null
    private lateinit var binding: FragmentDescBinding
    private val args: DescFragmentArgs by navArgs()
    private val currentItem: BaseDishes
        get() = args.dishes

    private lateinit var viewModel: DescViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, DescViewModelFactory(id = args.dishes.id)).get(
            DescViewModel::class.java
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDescBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.additionalDishesListLiveData.observe(viewLifecycleOwner) {
            binding.fragDescTitle.text = it.title
            Log.d("321", "title ${it.id}")
            binding.fragDescDesc.text = it.aboutFood.description
        }
        adapter = DishesListAdapter()
        binding.rcAdditionalFood.apply {
            adapter = this@DescFragment.adapter
            addOnScrollListener(
                ScrollListener(
                    context = context,
                    loadNextPage = {
                    },
                ).apply {
                    setOnTouchListener(this)
                }
            )
        }
        viewModel.additionalFoodListLiveData.observe(viewLifecycleOwner) {
            adapter?.reload(it)
            Log.d("123", "Desc Fragment $it")
        }
        //binding.fragDescTitle.text = currentItem.title
        Glide.with(binding.fragDescImage).load(currentItem.imageUrl).into(binding.fragDescImage)
        //viewModel.action.post(IDDishes(currentItem.id))

        binding.imBack.setOnClickListener {
            activity?.onBackPressed()
        }

        binding.imAdd.setOnClickListener {
            Toast.makeText(requireContext(),"Добавлено в корзину",Toast.LENGTH_LONG).show()
            viewModel.additionalDishesListLiveData.observe(viewLifecycleOwner) {
                viewModel.insertInCart(CartModel(title = it.title, cost = it.aboutFood.price, imageUrl = it.imageUrl))
            }
        }

        binding.imDelete.setOnClickListener {
            viewModel.deleteDishes()
            Log.d("123", "Тут тоже должно быть было удаление, но...")
        }
        viewModel.action.handler = { event ->
            when (event) {
                is AddAdditionalFood -> viewModel.insertInCart(
                    CartModel(
                        title = event.baseDishes.title,
                        cost = event.baseDishes.price,
                        imageUrl = event.baseDishes.imageUrl
                    )
                )
                is OpenLastFragment -> findNavController().popBackStack()
                else -> {
                    /* должно быть пусто*/
                }
            }
        }
    }

}
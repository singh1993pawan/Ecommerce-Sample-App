package com.freeelective.ecommercedemo.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.freeelective.ecommercedemo.MainActivity
import com.freeelective.ecommercedemo.R
import com.freeelective.ecommercedemo.adapter.ImagePageAdapter
import com.freeelective.ecommercedemo.api.ApiResponse
import com.freeelective.ecommercedemo.api.RetrofitInstance
import com.freeelective.ecommercedemo.databinding.FragmentProductsDetailsBinding
import com.freeelective.ecommercedemo.factory.ProductsDetailViewModelFactory
import com.freeelective.ecommercedemo.helper.UpdateCartValueCallback
import com.freeelective.ecommercedemo.repository.ProductsDetailsRepository
import com.freeelective.ecommercedemo.viewmodel.ProductsDetailsViewModel


class ProductsDetailsFragment : Fragment() {

    private lateinit var _binding: FragmentProductsDetailsBinding
    private val binding get() = _binding
    private lateinit var viewModel: ProductsDetailsViewModel
    private lateinit var repository: ProductsDetailsRepository
    private var id: Int = 0
    private var category:String = ""
    private lateinit var buttonNext: ImageButton
    private lateinit var buttonPrevious: ImageButton
    private lateinit var viewPager: ViewPager
    private lateinit var title: TextView
    private lateinit var price: TextView
    private lateinit var description: TextView
    private lateinit var updateCartValueCallback:MainActivity
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProductsDetailsBinding.inflate(
            inflater, container, false
        )
        requireActivity().findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar).visibility=View.VISIBLE
        updateCartValueCallback= activity as MainActivity
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        id = arguments?.getInt("productId") ?: 0
        category = arguments?.getString("category")?:""
        val apiService = RetrofitInstance.apiService
        val repository = ProductsDetailsRepository(apiService)
        viewModel = ViewModelProvider(this, ProductsDetailViewModelFactory(repository)).get(
            ProductsDetailsViewModel::class.java
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar).title=category.capitalize()
        viewPager = binding.viewPager
        viewModel.fetchData(id)
        buttonNext = binding.btnNext
        buttonPrevious = binding.btnPrevious
        title = binding.title
        price = binding.price
        description = binding.description

        binding.addToCart.setOnClickListener {
            updateCartValueCallback.updateCart()
        }

        viewModel.data.observe(viewLifecycleOwner, Observer {
            when (it) {
                is ApiResponse.Success -> {
                    val data = it.data
                    val adapter = ImagePageAdapter(this, data.images)
                    viewPager.adapter = adapter
                    title.text = data.title
                    price.text = "$${data.price}"
                    description.text = data.description
                }
                is ApiResponse.Error -> {
                    Log.e("Error", it.error)
                }
            }
        })

        buttonNext.setOnClickListener {
            navigateToNext()
        }
        buttonPrevious.setOnClickListener {
            navigateToPrevious()
        }

        viewPager.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                updateArrowVisibility()
            }

            override fun onPageSelected(position: Int) {
            }

            override fun onPageScrollStateChanged(state: Int) {
            }
        })

        updateArrowVisibility()

    }

    private fun navigateToPrevious() {
        if (viewPager.currentItem > 0) {
            viewPager.currentItem -= 1
        }
    }

    private fun updateArrowVisibility() {

        if (viewPager.currentItem == 0) {
            buttonPrevious.visibility = View.GONE
        } else {
            buttonPrevious.visibility = View.VISIBLE
        }

        if (viewPager.currentItem == viewPager.adapter?.count?.minus(1) ?: -1) {
            buttonNext.visibility = View.GONE
        } else {
            buttonNext.visibility = View.VISIBLE
        }

    }

    private fun navigateToNext() {
        if (viewPager.currentItem < viewPager.adapter?.count?.minus(1) ?: -1) {
            viewPager.currentItem += 1
        }
    }
}
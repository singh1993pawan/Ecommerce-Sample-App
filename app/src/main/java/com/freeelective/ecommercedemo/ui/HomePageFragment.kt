package com.freeelective.ecommercedemo.ui

import android.app.ActionBar.LayoutParams
import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.freeelective.ecommercedemo.R
import com.freeelective.ecommercedemo.adapter.ProductAdapter
import com.freeelective.ecommercedemo.api.ApiResponse
import com.freeelective.ecommercedemo.api.RetrofitInstance
import com.freeelective.ecommercedemo.data.model.AllCategoryResponse
import com.freeelective.ecommercedemo.data.model.Product
import com.freeelective.ecommercedemo.databinding.FragmentHomePageBinding
import com.freeelective.ecommercedemo.factory.AllCategoryViewModelFactory
import com.freeelective.ecommercedemo.helper.OnGridClickListner
import com.freeelective.ecommercedemo.repository.HomePageRepository
import com.freeelective.ecommercedemo.viewmodel.HomePageViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomePageFragment : Fragment(),OnGridClickListner {
    private var _binding: FragmentHomePageBinding? = null
    private val binding get() = _binding!!
    @Inject lateinit var viewModel: HomePageViewModel
    @Inject lateinit var respository: HomePageRepository
    private lateinit var adapter:ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //val apiService = RetrofitInstance.apiService

//        respository = HomePageRepository(apiService)
//        viewModel = ViewModelProvider(
//            this,
//            AllCategoryViewModelFactory(respository)
//        ).get(HomePageViewModel::class.java)
        viewModel.fetchData()
        viewModel.fetchProducts()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomePageBinding.inflate(inflater, container, false)
        requireActivity().findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar).visibility=View.VISIBLE

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar).title="All Products"
        val gridView=binding.gridView

        val radioGroup = binding.radioGroup
        radioGroup.orientation = LinearLayout.HORIZONTAL

        radioGroup.setOnCheckedChangeListener { radioGroup, i ->
            val radioButton = view.findViewById<RadioButton>(i)
            viewModel.fetchProductsByCategory(radioButton.text.toString())
        }

        // Create a ColorStateList programmatically
        val colorStateList = ColorStateList(
            arrayOf(
                intArrayOf(android.R.attr.state_checked),
                intArrayOf(-android.R.attr.state_checked)
            ),
            intArrayOf(
                ContextCompat.getColor(context!!, R.color.white),
                ContextCompat.getColor(context!!, R.color.black)
            )
        )

        //Observe products category
        viewModel.data.observe(viewLifecycleOwner, Observer {

            when (it) {
                is ApiResponse.Success -> {
                    val data: AllCategoryResponse = it.data
                    for (item in data) {
                        val radioButton = RadioButton(context)
                        radioButton.text = item.toString()
                        radioButton.id = View.generateViewId()
                        val params =
                            LinearLayout.LayoutParams(
                                LayoutParams.WRAP_CONTENT,
                                LayoutParams.WRAP_CONTENT,
                                1f
                            )
                        radioButton.layoutParams = params
                        radioButton.background =
                            resources.getDrawable(R.drawable.custome_radio_button)
                        radioButton.setTextColor(colorStateList)
                        radioButton.setPaddingRelative(0, 0, 25, 0)
                        val layoutParams = radioButton.layoutParams as ViewGroup.MarginLayoutParams
                        // Set the left margin in pixels
                        val leftMarginInPixels = 20
                        layoutParams.leftMargin = leftMarginInPixels
                        radioGroup.addView(radioButton)
                    }
                }
                is ApiResponse.Error -> {
                    Log.e("Error", it.error)
                }
            }
        })

        //Observe all products
        viewModel.products.observe(viewLifecycleOwner, Observer {

            when(it){
                is ApiResponse.Success->{
                    val data=it.data
                    adapter=ProductAdapter(context!!, data.products as MutableList<Product>,this)
                    gridView.adapter=adapter
                }
                is ApiResponse.Error->{
                    Log.e("Error", it.error)
                }
            }
        })

        //Observe products by category
        viewModel.productsByCategory.observe(viewLifecycleOwner, Observer {

            when(it){
                is ApiResponse.Success->{
                    val data=it.data
                    adapter=ProductAdapter(context!!, mutableListOf(),this)
                    adapter.setData(data.products)
                    gridView.adapter=adapter
                }
                is ApiResponse.Error->{
                    Log.e("Error", it.error)
                }
            }
        })


    }

    override fun onItemClick(id: Int, category: String) {
        val bundle=Bundle()
        bundle.putInt("productId",id)
        bundle.putString("category",category)
        findNavController().navigate(R.id.action_homePageFragment_to_productsDetailsFragment,bundle)
    }
}
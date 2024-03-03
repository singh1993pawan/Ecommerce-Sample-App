package com.freeelective.ecommercedemo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.freeelective.ecommercedemo.api.ApiResponse
import com.freeelective.ecommercedemo.data.model.AllCategoryResponse
import com.freeelective.ecommercedemo.data.model.Products
import com.freeelective.ecommercedemo.repository.HomePageRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomePageViewModel @Inject constructor(private val repository: HomePageRepository) : ViewModel() {

    private val _data = MutableLiveData<ApiResponse<AllCategoryResponse>>()
    val data: LiveData<ApiResponse<AllCategoryResponse>> get() = _data

    private val _products = MutableLiveData<ApiResponse<Products>>()
    val products:LiveData<ApiResponse<Products>> = _products

    private val _productsByCategory = MutableLiveData<ApiResponse<Products>>()
    val productsByCategory:LiveData<ApiResponse<Products>> = _productsByCategory

    fun fetchData() {
        viewModelScope.launch {
            _data.value=repository.fetchData()
        }
    }

    fun fetchProducts() {
        viewModelScope.launch {
            _products.value=repository.fetchProductsData()
        }
    }

    fun fetchProductsByCategory(category: String) {
        viewModelScope.launch {
            _productsByCategory.value=repository.fetchProductsDataByCategory(category)
        }
    }
}
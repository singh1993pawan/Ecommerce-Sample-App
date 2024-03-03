package com.freeelective.ecommercedemo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.freeelective.ecommercedemo.api.ApiResponse
import com.freeelective.ecommercedemo.data.model.AllCategoryResponse
import com.freeelective.ecommercedemo.data.model.Product
import com.freeelective.ecommercedemo.repository.HomePageRepository
import com.freeelective.ecommercedemo.repository.ProductsDetailsRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProductsDetailsViewModel @Inject constructor(private val repository: ProductsDetailsRepository):ViewModel() {
    private val _data = MutableLiveData<ApiResponse<Product>>()
    val data:LiveData<ApiResponse<Product>> get() = _data

    fun fetchData(id:Int){
        viewModelScope.launch {
            _data.value=repository.fetchData(id)
        }
    }
}
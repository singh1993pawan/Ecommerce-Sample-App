package com.freeelective.ecommercedemo.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.freeelective.ecommercedemo.repository.HomePageRepository
import com.freeelective.ecommercedemo.repository.ProductsDetailsRepository
import com.freeelective.ecommercedemo.viewmodel.HomePageViewModel
import com.freeelective.ecommercedemo.viewmodel.ProductsDetailsViewModel

class ProductsDetailViewModelFactory(private val dataRepository: ProductsDetailsRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductsDetailsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProductsDetailsViewModel(dataRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
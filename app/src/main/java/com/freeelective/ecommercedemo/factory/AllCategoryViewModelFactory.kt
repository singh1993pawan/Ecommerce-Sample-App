package com.freeelective.ecommercedemo.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.freeelective.ecommercedemo.repository.HomePageRepository
import com.freeelective.ecommercedemo.repository.LoginRepository
import com.freeelective.ecommercedemo.viewmodel.HomePageViewModel
import com.freeelective.ecommercedemo.viewmodel.LoginViewModel

class AllCategoryViewModelFactory(private val dataRepository: HomePageRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomePageViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomePageViewModel(dataRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
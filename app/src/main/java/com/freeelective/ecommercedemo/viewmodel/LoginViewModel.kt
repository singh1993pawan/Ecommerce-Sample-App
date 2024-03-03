package com.freeelective.ecommercedemo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.freeelective.ecommercedemo.api.ApiResponse
import com.freeelective.ecommercedemo.data.model.LoginRequestData
import com.freeelective.ecommercedemo.data.model.LoginResponseData
import com.freeelective.ecommercedemo.repository.LoginRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginViewModel @Inject constructor(private val dataRepository: LoginRepository) : ViewModel() {

    private val _data=MutableLiveData<ApiResponse<LoginResponseData>>()
    val data:LiveData<ApiResponse<LoginResponseData>> = _data

    fun fetchData(loginData: LoginRequestData){
        viewModelScope.launch {
            _data.value=dataRepository.fetchData(loginData)
        }
    }
}
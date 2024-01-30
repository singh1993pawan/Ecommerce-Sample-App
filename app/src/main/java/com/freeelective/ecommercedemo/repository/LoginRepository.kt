package com.freeelective.ecommercedemo.repository

import com.freeelective.ecommercedemo.api.ApiResponse
import com.freeelective.ecommercedemo.api.ApiService
import com.freeelective.ecommercedemo.data.model.LoginRequestData
import com.freeelective.ecommercedemo.data.model.LoginResponseData

class LoginRepository(private val apiService: ApiService) {

    suspend fun fetchData(loginData:LoginRequestData):ApiResponse<LoginResponseData>{
        return try {
            val response=apiService.login(loginData)
            if (response.isSuccessful) {
                ApiResponse.Success(response.body()!!)
            } else {
                ApiResponse.Error("Failed to fetch data: ${response.code()}")
            }
        }catch (e:Exception){
            ApiResponse.Error("Failed to fetch data: ${e.message}")
        }
    }

}
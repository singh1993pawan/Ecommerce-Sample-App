package com.freeelective.ecommercedemo.repository

import com.freeelective.ecommercedemo.api.ApiResponse
import com.freeelective.ecommercedemo.api.ApiService
import com.freeelective.ecommercedemo.data.model.AllCategoryResponse
import com.freeelective.ecommercedemo.data.model.Product

class ProductsDetailsRepository(private val apiService: ApiService) {

    suspend fun fetchData(id:Int): ApiResponse<Product> {
        return try {
            val response = apiService.getProduct(id)
            if (response.isSuccessful){
                ApiResponse.Success(response.body()!!)
            }else{
                ApiResponse.Error("Failed to fetch data: ${response.code()}")
            }

        } catch (e: Exception) {
            ApiResponse.Error("Failed to fetch data: ${e.message}")
        }
    }
}
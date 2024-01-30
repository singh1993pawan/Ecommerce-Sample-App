package com.freeelective.ecommercedemo.repository

import com.freeelective.ecommercedemo.api.ApiResponse
import com.freeelective.ecommercedemo.api.ApiService
import com.freeelective.ecommercedemo.data.model.AllCategoryResponse
import com.freeelective.ecommercedemo.data.model.Products

class HomePageRepository(private val apiService: ApiService) {

    suspend fun fetchData(): ApiResponse<AllCategoryResponse> {
        return try {
            val response = apiService.getAllCategory()
            if (response.isSuccessful){
                ApiResponse.Success(response.body()!!)
            }else{
                ApiResponse.Error("Failed to fetch data: ${response.code()}")
            }

        } catch (e: Exception) {
            ApiResponse.Error("Failed to fetch data: ${e.message}")
        }
    }

    suspend fun fetchProductsData(): ApiResponse<Products> {
        return try {
            val response = apiService.getAllProducts()
            if (response.isSuccessful){
                ApiResponse.Success(response.body()!!)
            }else{
                ApiResponse.Error("Failed to fetch data: ${response.code()}")
            }

        } catch (e: Exception) {
            ApiResponse.Error("Failed to fetch data: ${e.message}")
        }
    }

    suspend fun fetchProductsDataByCategory(category: String): ApiResponse<Products>? {
        return try {
            val response = apiService.getProductsByCategory(category)
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
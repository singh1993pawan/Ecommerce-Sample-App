package com.freeelective.ecommercedemo.api

import com.freeelective.ecommercedemo.data.model.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @POST("auth/login")
    suspend fun login(@Body loginRequestData: LoginRequestData):Response<LoginResponseData>

    @GET("products/categories")
    suspend fun getAllCategory():Response<AllCategoryResponse>

    @GET("products")
    suspend fun getAllProducts():Response<Products>

    @GET("products/category/{category}")
    suspend fun getProductsByCategory(@Path("category") category:String):Response<Products>

    @GET("products/{id}")
    suspend fun getProduct(@Path("id") id:Int):Response<Product>
}
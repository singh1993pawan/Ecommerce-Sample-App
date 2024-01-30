package com.freeelective.ecommercedemo.api

sealed class ApiResponse<out T> {
    data class Success<out T>(val data:T):ApiResponse<T>()
    data class Error(val error:String):ApiResponse<Nothing>()
}

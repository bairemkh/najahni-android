package com.example.najahni.utils

interface ApiResponseHandling {
    fun onSuccess(data: Any)
    fun onError(errorCode: Int, errorMessage: String)
    fun onFailure(errorMessage: String)
}
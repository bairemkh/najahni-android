package com.example.najahni.utils

import android.content.Intent


interface ApiResponseHandling {
    fun onSuccess(data: Any)
    fun onError(errorCode: Int, errorMessage: String)
    fun onFailure(errorMessage: String)
}
interface OnIntentReceived {
    fun onIntent(i: Intent?, resultCode: Int)
}
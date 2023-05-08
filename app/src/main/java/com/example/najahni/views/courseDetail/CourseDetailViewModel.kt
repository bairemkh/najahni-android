package com.example.najahni.views.courseDetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.najahni.services.implementation.EnrollService
import com.example.najahni.utils.ApiResponseHandling

class CourseDetailViewModel : ViewModel() {
    var enrollSucess = MutableLiveData<Boolean>()
    var message = MutableLiveData<String>()


    fun enrollInCourse(token:String,courseId:String){
        EnrollService.enrollInCourse(token,courseId,object : ApiResponseHandling{
            override fun onSuccess(data: Any) {
                message.value = data as String
                enrollSucess.value = true
            }

            override fun onError(errorCode: Int, errorMessage: String) {
                message.value = when (errorCode) {
                    404 -> "Course not found"
                    403 -> "Bad request "
                    else -> "Error"
                }
                enrollSucess.value = false
            }

            override fun onFailure(errorMessage: String) {
                message.value = "Server Error"
                enrollSucess.value = false
            }

        })
    }

}
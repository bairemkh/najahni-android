package com.example.najahni.views.courses

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.najahni.models.Course
import com.example.najahni.models.Enroll
import com.example.najahni.services.implementation.EnrollService

class CoursesViewModel : ViewModel() {
    var success = MutableLiveData<Boolean>()
    var message = MutableLiveData<String>()
    var tokenSaved = MutableLiveData<String>()
    var courses = MutableLiveData<List<Enroll>>()

    fun getMyCourses(token: String,action: (List<Enroll>) -> Unit){
        EnrollService.getAllCourses(token,{code,list -> action(list!!)})
    }
}
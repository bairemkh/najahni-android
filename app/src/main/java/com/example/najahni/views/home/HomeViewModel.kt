package com.example.najahni.views.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.najahni.models.Course
import com.example.najahni.services.implementation.CourseService
import com.example.najahni.utils.ApiResponseHandling

class HomeViewModel : ViewModel() {
    var success = MutableLiveData<Boolean>()
    var message = MutableLiveData<String>()
    var courses = MutableLiveData<List<Course>>()

    fun getAllCourses(action : (List<Course>?) -> Unit)  {
        CourseService.getAllCourses { code, list ->  action(list!!) }
    }
}
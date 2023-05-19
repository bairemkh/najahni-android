package com.example.najahni.utils

interface IService<T> {
    fun create(o: T)
    fun retrieve(id:String): T
    fun update(id:String)
    fun delete(id:String)
}
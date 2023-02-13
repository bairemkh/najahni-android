package com.example.najahni.services.implementation

import android.util.Log
import com.example.najahni.models.User
import com.example.najahni.services.retrofitInterfaces.IUserRetrofit
import com.example.najahni.utils.Consts.retrofit
import com.example.najahni.utils.IService
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object UserService: IService<User> {
    val api: IUserRetrofit = retrofit.create(IUserRetrofit::class.java)

    override fun create(o: User) {
        TODO("Not yet implemented")
    }

    override fun retrieve(id: String): User {
        TODO("Not yet implemented")
    }

    override fun update(id: String) {
        TODO("Not yet implemented")
    }

    override fun delete(id: String) {
        TODO("Not yet implemented")
    }
    fun login(email:String,password:String){
        val response = api.login(email, password).enqueue(object :Callback<ResponseBody>{
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                Log.e("response"," response ${response.code()} ${response.body()!!.string()}")
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("error","${t.message}")
            }

        })
    }


}
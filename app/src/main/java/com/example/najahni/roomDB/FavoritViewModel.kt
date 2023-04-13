package com.example.najahni.roomDB

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.najahni.models.Favorits
import kotlinx.coroutines.launch

class FavoritViewModel(val con: Context) : ViewModel() {
   var readAllData : List<Favorits>? = null


   fun insert(favorits: Favorits){
       FavoritRepository(AppDatabase.getDatabase(con).favoritDao()).addFavorit(favorits)
   }
   fun read(context: Context) {
       viewModelScope.launch {
           readAllData = FavoritRepository(AppDatabase.getDatabase(context).favoritDao()).readAllData
           Log.e("test ========>",readAllData.toString())
       }

   }
}
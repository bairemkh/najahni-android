package com.example.najahni.roomDB

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.najahni.models.Favorits
import kotlinx.coroutines.launch

class FavoritViewModel : ViewModel() {
    var favoritsList: LiveData<List<Favorits>>? = null
    fun insertFavorit(context: Context,favorits: Favorits){
        FavoritRepository.addFavorit(context,favorits)
    }

    fun favoritList(context: Context): LiveData<List<Favorits>>? {
        favoritsList = FavoritRepository.favoritList(context)
        return favoritsList
    }

    fun deleteFavorit(context: Context,favorits: Favorits){
        FavoritRepository.deleteFavorit(context,favorits)
    }
}
package com.example.najahni.roomDB

import com.example.najahni.models.Favorits
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoritRepository(private val favoritDao : FavoritsDao) {

    val readAllData : List<Favorits> = favoritDao.getAll()

    fun addFavorit(favorits: Favorits){
        CoroutineScope(Dispatchers.IO).launch {
            favoritDao.insert(favorits)
        }
    }
}
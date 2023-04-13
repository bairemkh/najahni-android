package com.example.najahni.roomDB

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.najahni.models.Favorits
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoritRepository {

    companion object {
        var appDatabase : AppDatabase? = null
        var listFavorits : LiveData<List<Favorits>>? = null
        fun initializeDB(context:Context) : AppDatabase {
            return AppDatabase.getDatabase(context)
        }

        fun addFavorit(context: Context, favorits: Favorits){
            appDatabase = initializeDB(context)

            CoroutineScope(Dispatchers.IO).launch {
                appDatabase!!.favoritDao().insert(favorits)
            }
        }

        fun favoritList(context: Context) : LiveData<List<Favorits>>? {
            appDatabase = initializeDB(context)
            listFavorits = appDatabase!!.favoritDao().getAll()
            return listFavorits
        }

        fun deleteFavorit(context: Context,favorits: Favorits){
            appDatabase = initializeDB(context)
            CoroutineScope(Dispatchers.IO).launch{
                appDatabase!!.favoritDao().delete(favorits)
            }
        }
    }
}
package com.example.najahni.roomDB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.najahni.models.Cart
import com.example.najahni.models.Favorits

@Database(entities = [Favorits :: class,Cart :: class], version = 1)
abstract class AppDatabase : RoomDatabase(){
    abstract fun favoritDao() : FavoritsDao
    abstract fun cartDao() : CartDao
    companion object{
        @Volatile
        private var INSTANCE : AppDatabase? = null

        fun getDatabase(context: Context) : AppDatabase {
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "Najahni-DB"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
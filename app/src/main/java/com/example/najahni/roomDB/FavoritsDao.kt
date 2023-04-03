package com.example.najahni.roomDB

import androidx.room.*
import com.example.najahni.models.Favorits


@Dao
interface FavoritsDao {

    @Query("SELECT * FROM favorite_course")
    fun getAll():List<Favorits>

    @Query("SELECT * FROM favorite_course WHERE _id LIKE :id LIMIT 1")
    suspend fun findById(id : String): Favorits

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(favorits: Favorits)

    @Delete
    suspend fun delete(favorits: Favorits)


}
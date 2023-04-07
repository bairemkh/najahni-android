package com.example.najahni.views.favorit

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.najahni.R
import com.example.najahni.models.Favorits
import com.example.najahni.roomDB.AppDatabase
import com.example.najahni.views.home.FavoriteAdapter
import kotlinx.coroutines.*


class FavoriteFragment : Fragment() {
    lateinit var appDatabase: AppDatabase
    lateinit var listFavorit: List<Favorits>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_favorit, container, false)
        appDatabase = AppDatabase.getDatabase(requireContext())
        val recycler = view.findViewById<RecyclerView>(R.id.recyclerViewFav)
        recycler.layoutManager = LinearLayoutManager(activity)
        runBlocking {
            listFavorit = appDatabase.favoritDao().getAll()
        }
        if (!listFavorit.isEmpty()) {
            recycler.adapter = FavoriteAdapter(appDatabase.favoritDao().getAll())
        }

        return view
    }
}
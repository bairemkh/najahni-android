package com.example.najahni.views.favorit

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.najahni.R
import com.example.najahni.roomDB.AppDatabase
import com.example.najahni.views.home.FavoriteAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class FavoriteFragment : Fragment() {
    lateinit var appDatabase: AppDatabase
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view : View = inflater.inflate(R.layout.fragment_favorit, container, false)
        appDatabase = AppDatabase.getDatabase(requireContext())
        val recycler = view.findViewById<RecyclerView>(R.id.recyclerViewFav)
        recycler.layoutManager = LinearLayoutManager(activity)
        CoroutineScope(Dispatchers.IO).launch {
            val list = appDatabase.favoritDao().getAll()
            if(!list.isEmpty()){
                recycler.adapter = FavoriteAdapter(appDatabase.favoritDao().getAll())
            }

        }

        return view
    }
}
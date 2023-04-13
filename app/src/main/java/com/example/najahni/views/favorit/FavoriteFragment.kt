package com.example.najahni.views.favorit

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.ViewModelFactoryDsl
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.najahni.R
import com.example.najahni.models.Favorits
import com.example.najahni.roomDB.AppDatabase
import com.example.najahni.roomDB.FavoritRepository
import com.example.najahni.roomDB.FavoritViewModel
import com.example.najahni.views.home.FavoriteAdapter
import kotlinx.coroutines.*


class FavoriteFragment : Fragment() {
    lateinit var appDatabase: AppDatabase
    lateinit var favoritViewModel: FavoritViewModel
    lateinit var listFavorit: List<Favorits>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_favorit, container, false)
        appDatabase = AppDatabase.getDatabase(requireContext())
        val recycler = view.findViewById<RecyclerView>(R.id.recyclerViewFav)
        recycler.layoutManager = LinearLayoutManager(activity)
        favoritViewModel = ViewModelProvider(this)[FavoritViewModel::class.java]

        favoritViewModel.favoritList(requireContext())!!.observe(viewLifecycleOwner, Observer {
            if(it == null){
                Log.e("test","nulll")

            }else{
                val adapter = FavoriteAdapter(it)
                adapter.notifyDataSetChanged()
                recycler.adapter = adapter
                val swipeGesture = object : SwipeGesture(requireContext()){
                    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                       val favoritCourse : Favorits = it.get(viewHolder.adapterPosition)
                        deleteFavorite(requireContext(),favoritCourse)
                        adapter.notifyItemRemoved(viewHolder.adapterPosition)
                    }
                }
                val touchHelper = ItemTouchHelper(swipeGesture)
                touchHelper.attachToRecyclerView(recycler)
            }
        })



        return view
    }

    private fun deleteFavorite(context: Context, favorits: Favorits){
        favoritViewModel.deleteFavorit(context,favorits)
    }
}
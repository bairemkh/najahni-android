package com.example.najahni.views.cart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.najahni.R
import com.example.najahni.models.Cart
import com.example.najahni.roomDB.AppDatabase
import com.example.najahni.views.home.FavoriteAdapter
import kotlinx.coroutines.*


class CartFragment : Fragment() {
    lateinit var appDatabase: AppDatabase
    lateinit var listCart : List<Cart>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_cart, container, false)
        appDatabase = AppDatabase.getDatabase(requireContext())
        val recycler = view.findViewById<RecyclerView>(R.id.recyclerViewCart)
        recycler.layoutManager = LinearLayoutManager(activity)
        CoroutineScope(Dispatchers.IO).launch {
            val list = appDatabase.cartDao().getAll()
            if (!list.isEmpty()) {
                recycler.adapter = CartAdapter(appDatabase.cartDao().getAll())
            }

        }
        return view
    }
}
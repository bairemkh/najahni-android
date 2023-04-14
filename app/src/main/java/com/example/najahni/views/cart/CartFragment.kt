package com.example.najahni.views.cart

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.najahni.R
import com.example.najahni.models.Cart
import com.example.najahni.models.Favorits
import com.example.najahni.roomDB.AppDatabase
import com.example.najahni.roomDB.CartViewModel
import com.example.najahni.views.favorit.SwipeGesture
import com.example.najahni.views.home.FavoriteAdapter
import kotlinx.coroutines.*


class CartFragment : Fragment() {
    lateinit var appDatabase: AppDatabase
    lateinit var cartViewModel: CartViewModel
    lateinit var listCart : List<Cart>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_cart, container, false)
        appDatabase = AppDatabase.getDatabase(requireContext())
        val recycler = view.findViewById<RecyclerView>(R.id.recyclerViewCart)
        recycler.layoutManager = LinearLayoutManager(activity)
        cartViewModel = ViewModelProvider(this)[CartViewModel::class.java]
        var totalPrice : Double = 0.0
        cartViewModel.cartList(requireContext())!!.observe(viewLifecycleOwner, Observer {
            if(it == null) {
                Log.e("Cart","nulll")
            }else{
                val adapter = CartAdapter(it)
                adapter.notifyDataSetChanged()
                recycler.adapter = adapter
                for (i in it){
                    totalPrice = i.price + totalPrice
                }
                view.findViewById<TextView>(R.id.cartTotal).text = totalPrice.toString() + "Dt"
                val swipeGesture = object : SwipeGesture(requireContext()){
                    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                        val cartCourse : Cart = it.get(viewHolder.adapterPosition)
                        deleteFromCart(requireContext(),cartCourse)
                        totalPrice = totalPrice - cartCourse.price
                        adapter.notifyItemRemoved(viewHolder.adapterPosition)
                    }
                }
                val touchHelper = ItemTouchHelper(swipeGesture)
                touchHelper.attachToRecyclerView(recycler)
            }
        })
        return view
    }

    private fun deleteFromCart(context: Context, cart: Cart){
        cartViewModel.deleteCart(context,cart)
    }
}
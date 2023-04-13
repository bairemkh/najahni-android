package com.example.najahni.views.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.najahni.R
import com.example.najahni.models.Favorits
import com.example.najahni.utils.Consts
import com.squareup.picasso.Picasso

class FavoriteAdapter (val courses : List<Favorits>) : RecyclerView.Adapter<FavoriteAdapter.ViewHolder> (){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.favorite_card,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return courses.size
    }

    fun deleteItem(i: Int){
        courses.drop(i)
        notifyDataSetChanged()
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val course = courses[position]
        Picasso.get().load(Consts.BASE_URL1 + course.image).into(holder.courseimg)
        holder.title.text = course.title
    }

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val courseimg: ImageView = itemView.findViewById(R.id.favcardimg)
        val title: TextView = itemView.findViewById(R.id.favcoursename)
    }
}
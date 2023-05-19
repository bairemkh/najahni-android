package com.example.najahni.views.home

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.najahni.R
import com.example.najahni.models.Course
import com.example.najahni.utils.Consts
import com.example.najahni.views.courseDetail.CourseDetail
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class RecommendedCoursesAdapter(val list:List<Course>):RecyclerView.Adapter<RecommendedCoursesAdapter.ViewHolder> () {
    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val courseImg: ImageView = itemView.findViewById(R.id.imageRecommended)
        val trainerImg: CircleImageView = itemView.findViewById(R.id.imageCreatorRecommended)
        val courseTitle: TextView = itemView.findViewById(R.id.courseNameRecommended)
        val trainerName: TextView = itemView.findViewById(R.id.creatorNameRecommended)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recommended_card,parent,false)
        return RecommendedCoursesAdapter.ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.courseTitle.text=list[position].title
        holder.trainerName.text= "${list[position].idOwner.firstname} ${list[position].idOwner.lastname}"
        Picasso.get().load(Consts.BASE_URL1 + list[position].image).into(holder.courseImg)
        Picasso.get().load(Consts.BASE_URL1 + list[position].idOwner.image).into(holder.trainerImg)
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, CourseDetail::class.java)
            intent.putExtra(Consts.SELECTED_COURSE_INTENT, list[position])
            it.context.startActivity(intent)
        }
    }
}
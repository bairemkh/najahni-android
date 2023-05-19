package com.example.najahni.views.addCourse

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.TEXT_ALIGNMENT_CENTER
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.najahni.R
import com.example.najahni.models.Course
import com.example.najahni.models.Section
import com.example.najahni.views.addCourse.adapters.EditSectionListAdapter
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import java.util.*

class AddSections(var course: Course) : Fragment() {
    private lateinit var recyclerView: RecyclerView
    var videoUri: Uri?=null
    lateinit var adapter: EditSectionListAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_sections, container, false)
        recyclerView=view.findViewById(R.id.editSectionRV)
        adapter = EditSectionListAdapter(course.sections,view.context){
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "video/*"
            startActivityForResult(intent, 200)
            Log.e("sent","image sent")
        }
        view.findViewById<ExtendedFloatingActionButton>(R.id.addNewSectionBtn).setOnClickListener {
            val editText = EditText(view.context).apply {
                hint = "Change the name here"
                layoutParams = ViewGroup.MarginLayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                ).apply {
                    val margin = 20
                    setPadding(30, margin, 30, margin)
                }
                background=null
                setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f)
                typeface = ResourcesCompat.getFont(view.context, R.font.montserrat_bold)
            }
            val alertDialogBuilder = AlertDialog.Builder(view.context)
            val title=TextView(view.context).apply {
                layoutParams = ViewGroup.MarginLayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                ).apply {
                    val margin = 30
                    setPadding(margin, margin, margin, margin)
                }
                textAlignment=TEXT_ALIGNMENT_CENTER
                text="Add a new Section"
                setTextColor(resources.getColor(R.color.secondary_color))
                setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f)
                typeface = ResourcesCompat.getFont(context, R.font.montserrat_bold)
            }
            alertDialogBuilder.setCustomTitle(title)
            alertDialogBuilder.setView(editText)

            alertDialogBuilder.setPositiveButton("Confirm") { _, _ ->
                val text = editText.text.toString()
                if(text.isNotEmpty()){
                    adapter.addSection(Section(UUID.randomUUID().toString(),text, mutableListOf()))
                    adapter.notifyItemInserted(adapter.itemCount - 1)
                }
            }
            alertDialogBuilder.show()
        }
        recyclerView.adapter=adapter
        return view
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 200) {
            Log.e("arrived","Arrived at activity")
            adapter.onIntent(data,resultCode)
        }
    }
}
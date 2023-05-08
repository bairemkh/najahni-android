package com.example.najahni.views.addCourse.adapters

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.najahni.R
import com.example.najahni.models.Lesson
import com.example.najahni.models.Section
import com.example.najahni.services.implementation.CourseService
import com.example.najahni.utils.OnIntentReceived
import java.io.*


class EditSectionListAdapter(val list:MutableList<Section>,val context: Context,val onUploadVideo:()->Unit) :RecyclerView.Adapter<EditSectionListAdapter.ViewHolder> (),
    OnIntentReceived {
    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val sectionTitle: TextView = itemView.findViewById(R.id.sectionNameLessonCardEdit)
        val editSection: ImageView = itemView.findViewById(R.id.editSectionBtn)
        val addLesson: ImageView = itemView.findViewById(R.id.addLessonToSectionBtn)
        val deleteSection: ImageView = itemView.findViewById(R.id.deleteSectionBtn)
        val lessonsList: RecyclerView = itemView.findViewById(R.id.lessonsListEditRV)
    }
    var onGettingData:((View, EditLessonListAdapter?, Lesson?, Boolean)->Unit)?=null
    private fun setOnGetData(listener: ((View,EditLessonListAdapter?, Lesson?,Boolean)->Unit)) {
        onGettingData=listener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.sections_card_edit,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.sectionTitle.text=list[position].title
        holder.editSection.setOnClickListener {
            val editText = EditText(holder.itemView.context)
            editText.setText(list[position].title)
            val alertDialogBuilder = AlertDialog.Builder(holder.itemView.context)
            val title=TextView(holder.itemView.context).apply {
                layoutParams = ViewGroup.MarginLayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                ).apply {
                    val margin = 30
                    setPadding(margin, margin, margin, margin)
                }
                textAlignment= View.TEXT_ALIGNMENT_CENTER
                text="Edit the  Section"
                setTextColor(resources.getColor(R.color.secondary_color))
                setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f)
                typeface = ResourcesCompat.getFont(context, R.font.montserrat_bold)
            }
            alertDialogBuilder.setCustomTitle(title)
            alertDialogBuilder.setView(editText)
            alertDialogBuilder.setPositiveButton("Confirm") { _, _ ->
                list[position].title = editText.text.toString()
                notifyItemChanged(position)
            }
            alertDialogBuilder.show()
        }
        holder.deleteSection.setOnClickListener {
            list.removeAt(position)
            notifyItemRemoved(position) }
        val adapter=EditLessonListAdapter(list[position].lessons){lesson->
            val dialogViewDia = LayoutInflater.from(holder.itemView.context).inflate(R.layout.lesson_add_popup, null)
            val alertDialogBuilder = AlertDialog.Builder(holder.itemView.context)
            alertDialogBuilder.setView(dialogViewDia)
            dialogViewDia.findViewById<TextView>(R.id.lessonImportVideoEditPopup).setOnClickListener {
                onUploadVideo()
            }
            alertDialogBuilder.setPositiveButton("Ok") { _, _ ->
                onGettingData?.invoke(dialogViewDia,null,lesson,false)
            }
            alertDialogBuilder.create().show()
        }
        holder.lessonsList.adapter=adapter
        holder.addLesson.setOnClickListener {
            val dialogViewDia = LayoutInflater.from(holder.itemView.context).inflate(R.layout.lesson_add_popup, null)
            val alertDialogBuilder = AlertDialog.Builder(holder.itemView.context)
            alertDialogBuilder.setView(dialogViewDia)
            dialogViewDia.findViewById<TextView>(R.id.lessonImportVideoEditPopup).setOnClickListener {
                onUploadVideo()
            }
            alertDialogBuilder.setPositiveButton("Ok") { _, _ ->
                onGettingData?.invoke(dialogViewDia,adapter,null,true)
            }
            alertDialogBuilder.create().show()

        }

    }
    fun addSection(section:Section){
        list.add(section)
    }

    @Throws(IOException::class)
    fun getFileFromUri(uri: Uri?): File? {
        val contentResolver = context.contentResolver
        val inputStream: InputStream? = contentResolver.openInputStream(uri!!)
        val file = File(context.cacheDir, "temp_lesson_video.mp4")
        val outputStream: OutputStream = FileOutputStream(file)
        val buffer = ByteArray(4 * 1024) // or other buffer size
        var read: Int
        while (inputStream!!.read(buffer).also { read = it } != -1) {
            outputStream.write(buffer, 0, read)
        }
        outputStream.flush()
        return file
    }

    override fun onIntent(i: Intent?, resultCode: Int) {
        Log.e("ping","here")
        //onGettingData?.invoke()
        setOnGetData{ dialogViewDia: View, adapter,l, isAdding ->
            Log.e("gol","listener working !!!")
            if(isAdding){
                val file =getFileFromUri(i!!.data!!)!!
                Log.e("getting data","in the data methode ${file.absolutePath}")
                CourseService.saveLessonVideo(file,dialogViewDia.findViewById<EditText>(R.id.lessonNameEditPopup).text.toString()){
                        lesson, b ->
                    if(b){
                        adapter!!.addLesson(lesson!!)
                        adapter.notifyItemInserted(adapter.itemCount - 1)
                    }
                }
            }else{
                val file =getFileFromUri(i!!.data!!)!!
                Log.e("getting data","in the data methode ${file.absolutePath}")
                val name=dialogViewDia.findViewById<EditText>(R.id.lessonNameEditPopup).apply {
                    setText(l!!.title)
                }
                CourseService.saveLessonVideo(file,name.text.toString()){
                        lesson, b ->
                    if(b){
                        l!!.video=lesson!!.video
                        l.duration= lesson.duration
                        l.title= lesson.title
                    }
                }
            }

        }

    }
}
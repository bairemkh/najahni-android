package com.example.najahni.views.addCourse

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.najahni.R
import com.example.najahni.models.Course
import com.example.najahni.models.enums.Field
import com.example.najahni.models.enums.Level
import com.example.najahni.utils.Consts
import com.squareup.picasso.Picasso


class CreateCourseFragment(var course: Course, var uriImage: MutableLiveData<Uri>) : Fragment() {
    private var onChoosingImage:((Uri)->Unit)?=null
    private fun onChoosingImageListener(listener: (Uri)->Unit) {
        onChoosingImage = listener
    }
    private var boolArray= MutableLiveData(BooleanArray(Field.values().size))
    private var values = MutableLiveData(Field.values().map { f->f.name }.toTypedArray())
    private var selectedFields= MutableLiveData<ArrayList<Field>>(arrayListOf())
    private var fieldsMessage = MutableLiveData<String>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_create_course, container, false)
        view.findViewById<EditText>(R.id.courseTitleEdit).apply {
            setText(course.title.toString())
            doAfterTextChanged{
                course.title=it.toString()

            }
        }
        boolArray.value=Field.values().map { f-> course.fields.contains(f) }.toBooleanArray()
        fieldsMessage.value="${course.fields.size} Fields Selected"
        view.findViewById<EditText>(R.id.courseDescEdit).apply {
            setText(course.description)
            doAfterTextChanged{
                course.description=it.toString()
            }
        }
        view.findViewById<EditText>(R.id.coursePriceEdit).apply {
            setText(course.price.toString())
            doAfterTextChanged{
                if(it!!.isEmpty()){
                    course.isPaid=false
                    course.price=0.0
                }else{
                    course.isPaid=true
                    try {
                       course.price= it.toString().toDouble()
                    }catch (ex:java.lang.Exception){
                        setText("")
                        course.price=0.0
                        course.isPaid=false
                        Toast.makeText(view.context,"You can only put numbers",Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
        view.findViewById<Spinner>(R.id.courseLevelEdit).apply {
            val adapter = ArrayAdapter(view.context,
                android.R.layout.simple_spinner_item, Level.values().map { level-> level.name })
            this.adapter=adapter
            setSelection(Level.values().indexOf(course.level))
            onItemSelectedListener =object : AdapterView.OnItemSelectedListener{
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    Log.e("selected","${Level.values()[position]} is selected")
                    course.level = Level.values()[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    course.level=Level.Beginner
                }

            }
        }
        val image=view.findViewById<ImageView>(R.id.courseImageEdit).apply {
            if(uriImage.value!= Uri.EMPTY)
                setImageURI(uriImage.value)
        }
        if (course.image.isNotEmpty()){
            Picasso.get().load(Consts.BASE_URL1 + course.image).into(image)
        }
        image.setOnClickListener {
                    val intent = Intent(Intent.ACTION_PICK)
                    intent.type = "image/*"
                    startActivityForResult(intent, 200)
                    onChoosingImageListener { newUri ->
                        uriImage.value= newUri
                        image.setImageURI(newUri)
                    }
                }
        fieldsMessage.observe(viewLifecycleOwner, Observer {
            view.findViewById<TextView>(R.id.selectedFieldsAddCourse).text= it
        })
        view.findViewById<LinearLayout>(R.id.openFieldOptionsAddCourse).apply {
            setOnClickListener {
                openChoiceDialog(view.context)
            }
        }
        selectedFields.observe(viewLifecycleOwner,Observer{
            Log.e("selected fields",selectedFields.toString())
        })


        return view
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 200 && resultCode == AppCompatActivity.RESULT_OK) {
            val selectedImageUri = data?.data ?: return
            uriImage.value= selectedImageUri
            onChoosingImage?.invoke(selectedImageUri)
        }
    }
    private fun openChoiceDialog(context:Context) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Select an Option")
        builder.setMultiChoiceItems(values.value,boolArray.value
        ) { _, which, isChecked ->
            if(isChecked){
                course.fields.add(Field.valueOf(values.value!![which]))
                fieldsMessage.value="${course.fields.size} Fields Selected"
            }else{
                course.fields.removeAt(which)
                fieldsMessage.value="${course.fields.size} Fields Selected"
            }
        }
        builder.create().show()
    }

}
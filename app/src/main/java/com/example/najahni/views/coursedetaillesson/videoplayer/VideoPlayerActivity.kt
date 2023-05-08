package com.example.najahni.views.coursedetaillesson.videoplayer

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.MediaController
import android.widget.VideoView
import com.example.najahni.R
import com.example.najahni.models.Course
import com.example.najahni.models.Lesson
import com.example.najahni.utils.Consts

class VideoPlayerActivity : AppCompatActivity() {
    private lateinit var lessonVideo: VideoView
    private  var mediaController: MediaController? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_player)
        val selectedlesson = intent.getSerializableExtra(Consts.SELECTED_LESSON_INTENT) as Lesson

        lessonVideo = findViewById<VideoView>(R.id.videoView)
        if (mediaController == null) {
            mediaController = MediaController(this)
            mediaController!!.setAnchorView(this.lessonVideo)
        }
        lessonVideo.setMediaController(mediaController)
        lessonVideo.setVideoURI(Uri.parse(Consts.BASE_URL1 + selectedlesson.video))

        lessonVideo.requestFocus()
        lessonVideo.start()

    }
}
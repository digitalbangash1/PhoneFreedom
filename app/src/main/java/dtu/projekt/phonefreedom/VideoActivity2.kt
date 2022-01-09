package dtu.projekt.phonefreedom

import android.widget.VideoView

import android.os.Bundle
import android.view.View
import android.widget.MediaController
import androidx.appcompat.app.AppCompatActivity


class VideoActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video2)
        val videoView = findViewById<View>(R.id.VideoView) as VideoView
        videoView.setVideoPath("android.resource://" + packageName + "/" + R.raw.videoclip)
        val mediaController = MediaController(this)
        //link mediaController to videoView
       // mediaController.setAnchorView(videoView)
        //allow mediaController to control our videoView
        //videoView.setMediaController(mediaController)
        videoView.start()
    }
}
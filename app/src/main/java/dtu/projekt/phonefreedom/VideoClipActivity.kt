package dtu.projekt.phonefreedom

import android.R
import android.widget.VideoView
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity



class VideoClipActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(dtu.projekt.phonefreedom.R.layout.activity_video_clip)

        val videoView : VideoView = findViewById(dtu.projekt.phonefreedom.R.id.VideoView)
        videoView.setVideoPath("android.resource://$packageName/" + dtu.projekt.phonefreedom.R.raw.videoclip)
        //val mediaController = MediaController(this)
        //mediaController.setAnchorView(videoView)
        //allow mediaController to control our videoView
        // videoView.setMediaController(mediaController)
        videoView.start()
    }
}

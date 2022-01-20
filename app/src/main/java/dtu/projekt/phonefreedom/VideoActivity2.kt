package dtu.projekt.phonefreedom

import android.widget.VideoView
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import dtu.projekt.phonefreedom.notification_services.PreferencesManager


class VideoActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video2)
        val videoView = findViewById<View>(R.id.VideoView) as VideoView
        videoView.setVideoPath("android.resource://" + packageName + "/" + R.raw.videoclip)

        videoView.start()
    }
}

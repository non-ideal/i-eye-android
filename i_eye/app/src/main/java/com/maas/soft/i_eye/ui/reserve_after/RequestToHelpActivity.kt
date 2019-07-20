package com.maas.soft.i_eye.ui.reserve_after

import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import com.maas.soft.i_eye.R
import com.maas.soft.i_eye.controller.SharedPreferenceController
import kotlinx.android.synthetic.main.activity_request_to_help.*
import java.util.*

class RequestToHelpActivity : AppCompatActivity() {
    private lateinit var tts : TextToSpeech

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_request_to_help)
        changeStatusBarColor()

        tts = TextToSpeech(applicationContext, TextToSpeech.OnInitListener {
            tts.language = Locale.KOREAN
        })
        tts.speak("도움 요청을 완료했습니다.", TextToSpeech.QUEUE_FLUSH, null, this.hashCode().toString())

        tv_request_to_help.setOnClickListener { SharedPreferenceController.setStatus(this, 0) }
    }

    private fun changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.statusBarColor = resources.getColor(R.color.black, theme)
        } else {
            window.statusBarColor = resources.getColor(R.color.black)
        }
    }
}

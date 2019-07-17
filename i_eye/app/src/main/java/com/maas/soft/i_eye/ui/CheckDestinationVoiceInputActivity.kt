package com.maas.soft.i_eye.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.maas.soft.i_eye.R
import kotlinx.android.synthetic.main.activity_check_destination_voice_input.*

class CheckDestinationVoiceInputActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_destination_voice_input)

        tv_dest_check_dest_voice.text = "${intent.getStringExtra("VOICE_DEST")}\n맞습니까?"
        tv_dest_check_dest_voice.contentDescription = "${intent.getStringExtra("VOICE_DEST")} 맞습니까?"
    }
}

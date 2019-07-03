package com.maas.soft.i_eye.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.maas.soft.i_eye.R
import kotlinx.android.synthetic.main.activity_request_to_get_off.*

class RequestToGetOffActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_request_to_get_off)

        Glide.with(this).asGif().load(R.raw.loading).into(iv_loading_request_to_get_off)
    }
}

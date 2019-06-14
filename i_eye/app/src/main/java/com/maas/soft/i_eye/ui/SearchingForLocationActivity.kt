package com.maas.soft.i_eye.ui

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.maas.soft.i_eye.R
import kotlinx.android.synthetic.main.activity_searching_for_location.*

class SearchingForLocationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_searching_for_location)

        Glide.with(this).asGif().load(R.raw.loading).into(iv_loading_searching)

        moveToNextActivity()
    }

    private fun moveToNextActivity() {
        val handler = Handler()
        handler.postDelayed({
            val intent = Intent(applicationContext, CheckCurrentLocationActivity::class.java)
            startActivity(intent)
        }, 1500)
    }
}

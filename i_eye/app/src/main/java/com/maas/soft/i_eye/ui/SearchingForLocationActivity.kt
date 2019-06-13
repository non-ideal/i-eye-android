package com.maas.soft.i_eye.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.maas.soft.i_eye.R
import kotlinx.android.synthetic.main.activity_searching_for_location.*

class SearchingForLocationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_searching_for_location)

        Glide.with(this).asGif().load(R.raw.loading).into(iv_loading_searching)
    }
}

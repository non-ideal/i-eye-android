package com.maas.soft.i_eye.ui

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.maas.soft.i_eye.R
import kotlinx.android.synthetic.main.activity_search_destination.*

class SearchDestinationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_destination)
        setClickListener()
    }

    private fun setClickListener() {
        cl_top_dest_search.setOnClickListener {
            val intent = Intent(applicationContext, CheckDestinationActivity::class.java)
            startActivity(intent)
        }
    }
}
